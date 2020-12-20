/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.printservice.formatters;

import com.jobits.pos.logs.RestManagerHandler;
import com.jobits.pos.adapters.repo.impl.ProductovOrdenDAO;
import com.jobits.pos.persistence.Cocina;
import com.jobits.pos.persistence.Orden;
import com.jobits.pos.persistence.ProductovOrden;
import com.jobits.pos.printservice.Impresion;
import com.jobits.pos.printservice.Ticket;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;

/**
 *
 * @author Javier
 */
public class CocinaFormatter extends AbstractTicketFormatter {

    Orden orden;
    Cocina cocina;
    String sync;
    private final String TAB = "****> ";

    public CocinaFormatter(Orden orden, Cocina cocina, String sync) {
        this.orden = orden;
        this.cocina = cocina;
        this.sync = sync;
    }

    @Override
    public Doc formatPrint(String printingOutput) {
        if (printingOutput.equals(PrintFormatter.TICKET_PRINTER)) {
            return formatKitchen();
        }
        throw new IllegalArgumentException("Bad call");
    }

    private Doc formatKitchen() {
        boolean ordenSinPlatos = true;

        Ticket t = new Ticket();

        addHeader(t);

        t.emphasized(true);
        t.setText(COCINA + cocina.getNombreCocina());
        t.emphasized(false);
        t.newLine();

        addMetaData(t, orden, new Date());

        t.alignLeft();

        for (ProductovOrden x : orden.getProductovOrdenList()) {
            log(orden, x);

            if (x.getEnviadosacocina() < x.getCantidad()
                    && x.getProductoVenta().getCocinacodCocina().equals(cocina)) {
                if (x.getNota() != null) {
                    t.alignCenter();
                    t.emphasized(true);
                    t.setText(x.getNota().getDescripcion().replace('%', ' '));
                    t.newLine();
                    t.alignLeft();
                    adjusttextToLine(t, x.getNombreProductoVendido(),
                            "*NOTA* " + (x.getCantidad() - x.getEnviadosacocina()), false);
//                    t.setText("*NOTA* " + (x.getCantidad() - x.getEnviadosacocina()) + " " + x.getNombreProductoVendido());
                } else {
                    adjusttextToLine(t, x.getNombreProductoVendido(),
                            "" + (x.getCantidad() - x.getEnviadosacocina()), false);
//                    t.setText(x.getCantidad() - x.getEnviadosacocina() + " " + x.getNombreProductoVendido());
                }
                t.alignRight();
                t.setText((x.getCantidad() - x.getEnviadosacocina()) * x.getPrecioVendido() + MONEDA);
                t.newLine();
                t.alignLeft();

                if (x.getAgregos() != null) {
                    if (!x.getAgregos().isEmpty()) {
                        for (ProductovOrden a : x.getAgregos()) {
                            adjusttextToLine(t, a.getNombreProductoVendido(),
                                    TAB + (a.getCantidad() - a.getEnviadosacocina()), true);
//                            t.setText(TAB + (a.getCantidad() - a.getEnviadosacocina()) + " " + a.getNombreProductoVendido());
//                            t.newLine();
                            t.alignRight();
                            t.setText((a.getCantidad() - a.getEnviadosacocina()) * a.getPrecioVendido() + MONEDA);
                            t.newLine();
                            t.alignLeft();
                            a.setEnviadosacocina(a.getCantidad());
                            try {
                                ProductovOrdenDAO.getInstance().edit(a);
                            } catch (Exception ex) {
                                Logger.getLogger(Impresion.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }
                }

                ordenSinPlatos = false;
                x.setEnviadosacocina(x.getCantidad());
            }
            try {

                ProductovOrdenDAO.getInstance().edit(x);
            } catch (Exception ex) {
                Logger.getLogger(Impresion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        t.addLineSeperator();
        t.alignCenter();
        t.newLine();
        t.emphasized(true);
        t.setText(sync);
        t.newLine();
        t.feed((byte) 3);
        t.finit();

        if (!ordenSinPlatos) {
            if (cocina.getRecibirNotificacion()) {
                return createDoc(t.finalCommandSet().getBytes());
            }
        } else {
            System.out.println("No existen productos de venta del punto de elaboracion "
                    + cocina.getNombreCocina() + " de la orden " + orden.getCodOrden() + " para imprimir");
            t.resetAll();
        }
        return createDoc(new Ticket().finalCommandSet().getBytes());
    }

    private void adjusttextToLine(Ticket t, String text, String inicio, boolean addTab) {
        String[] part = text.split(" ");
        String[] line = new String[5];
        line[0] = inicio;
        int numLine = 0;
        for (int i = 0; i < part.length; i++) {
            if (line[numLine].length() + part[i].length() < 32) {
                line[numLine] += " " + part[i];
            } else {
                numLine++;
                if (addTab) {
                    line[numLine] = "      " + part[i];
                } else {
                    line[numLine] = part[i];
                }
            }
        }
        for (String string : line) {
            if (string != null) {
                t.setText(string);
                t.newLine();
            } else {
                break;
            }
        }
    }

    private void log(Orden o, ProductovOrden x) {
        RestManagerHandler.Log(LOGGER, RestManagerHandler.Action.IMPRIMIENDO_PRODUCTO,
                Level.FINEST,
                o, x.getProductoVenta(),
                x.getEnviadosacocina() - x.getCantidad());
    }

    private Orden printKitchenForced(Orden o) {
        Ticket t = new Ticket();
        boolean ordenSinPlatos = true;

        addHeader(t);

        addMetaData(t, o, new Date());

        ArrayList<String> entrantes = new ArrayList<>();
        entrantes.add("Entrantes Calientes");
        entrantes.add("Entrantes Frios");

        ArrayList<ProductovOrden> items = new ArrayList<>(o.getProductovOrdenList());
        items.sort((ProductovOrden o1, ProductovOrden o2) -> {
            ArrayList<String> entrantes1 = new ArrayList<>();
            entrantes1.add("Entrantes Calientes");
            entrantes1.add("Entrantes Frios");
            if (entrantes1.contains(o1.getProductoVenta().getSeccionnombreSeccion().getNombreSeccion())) {
                return -1;
            }
            if (entrantes1.contains(o2.getProductoVenta().getSeccionnombreSeccion().getNombreSeccion())) {
                return 1;
            }
            if (o1.getProductoVenta().getSeccionnombreSeccion().getNombreSeccion().matches("Postres")) {
                return 1;
            }
            if (o2.getProductoVenta().getSeccionnombreSeccion().getNombreSeccion().matches("Postres")) {
                return -1;
            }
            return 0;
        });

        t.alignLeft();

        boolean entrante = false;
        boolean postre = false;

        for (ProductovOrden x : items) {
            log(o, x);

            if (x.getCantidad() > x.getEnviadosacocina()) {
                if (!entrantes.contains(x.getProductoVenta().getSeccionnombreSeccion().getNombreSeccion()) && !entrante) {
                    t.addLineSeperator();
                    t.newLine();
                    entrante = true;
                }
                if (x.getProductoVenta().getSeccionnombreSeccion().getNombreSeccion().equals("Postres") && !postre) {
                    t.addLineSeperator();
                    t.newLine();
                    postre = true;
                }
                if (x.getNota() != null) {
                    t.alignCenter();
                    t.emphasized(true);
                    t.setText(x.getNota().getDescripcion().replace('%', ' '));
                    t.newLine();
                    t.alignLeft();
                    t.setText("*NOTA* " + (x.getCantidad() - x.getEnviadosacocina()) + " " + x.getNombreProductoVendido());
                } else {
                    t.setText(x.getCantidad() - x.getEnviadosacocina() + " " + x.getNombreProductoVendido());
                }
                t.newLine();
                t.alignRight();
                t.setText((x.getCantidad() - x.getEnviadosacocina()) * x.getPrecioVendido() + " " + MONEDA);
                t.newLine();
                t.alignLeft();
                x.setEnviadosacocina(x.getCantidad());
                try {
                    ProductovOrdenDAO.getInstance().edit(x);
                } catch (Exception ex) {
                    Logger.getLogger(Impresion.class.getName()).log(Level.SEVERE, null, ex);
                }
                ordenSinPlatos = false;
                x.setEnviadosacocina(x.getCantidad());
            }
        }

        t.addLineSeperator();
        t.alignCenter();
        t.newLine();
        t.feed((byte) 3);
        t.finit();

        if (!ordenSinPlatos) {
            //feedPrinter(t.finalCommandSet().getBytes(), DEFAULT_KITCHEN_PRINTER_LOCATION, TipoImpresion.COCINA);
        }

        return o;
    }
}
