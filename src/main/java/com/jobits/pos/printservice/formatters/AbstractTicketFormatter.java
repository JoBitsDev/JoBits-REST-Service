/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.printservice.formatters;

import com.jobits.pos.persistence.IpvRegistro;
import com.jobits.pos.persistence.Orden;
import com.jobits.pos.persistence.ProductovOrden;
import com.jobits.pos.printservice.Impresion;
import com.jobits.pos.printservice.Ticket;
import com.jobits.utils.R;
import com.jobits.utils.utils;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.SimpleDoc;

/**
 *
 * @author Javier
 */
public abstract class AbstractTicketFormatter implements PrintFormatter {

    protected static final Logger LOGGER = Logger.getLogger(Impresion.class.getSimpleName());

    private boolean monedaCUC;
    protected static boolean SHOW_PRICES = true;
    public static boolean PRINT_IN_CENTRAL_KITCHEN = true;
    public static boolean PRINT_GASTOS_EN_AUTORIZOS = false;
    public static boolean SHOW_HEADER = true;
    public static boolean SHOW_SUBTOTAL = true;
    public static String DEFAULT_KITCHEN_PRINTER_LOCATION = "Cocina";
    public static String DEFAULT_PRINT_LOCATION = null;
    public static boolean PRINT_SECOND_COIN = true;
    public static boolean IMPRIMIR_TICKET_COCINA = true;
    public static int cantidadCopias = 0;
    public static boolean REDONDEO_POR_EXCESO = true;
    public static String CABECERA = "Restaurante";
    public static boolean BUZZER_ON = true;

    /**
     * String referentes a la impresion de ordenes
     */
    public final String COCINA = "Pto Elaboracion: ";

    private final String TAB = "****> ";

    protected final String DELACASA = "(Pedido por la casa)",
            ORDEN = "Orden No: ",
            MESA = "Mesa: ",
            FECHA = "Fecha: ",
            CAMARERO = "Cajero(a): ",
            SUBTOTAL = "SubTotal: ",
            TOTAL = "Total: ",
            CUC = " CUC",
            MN = " MN",
            PREVIEW = "(Cierre parcial de cuenta)",
            PORCIENTO = "% : ",
            Z = "Impresión de Z",
            CANCELACION = "CANCELACION";

    protected String PIE = "Vuelva Pronto",
            MONEDA = R.getCoinSuffix();

    /**
     * Strings referentes a la impresion de resumenes de ventas
     */
    protected final String RESUMEN_VENTAS_CAMAREROS = "Resumen de ventas personal ",
            RESUMEN_VENTAS_COCINA = "Resumen de ventas por area ",
            TOTAL_VENTAS = "Total Vendido: ",
            RESUMEN_CONSUMO_CASA = "Resumen del consumo de la casa ";

    protected final String IPV_TABLE_HEADER = "Ini. |Ent. |Disp.|Cons.|Final.",
            IPV_HEADER = "Resumen de gasto de insumos",
            IPV_PUNTO_ELAB = "Punto de elaboracion";

    protected final String GASTO_HEADER = "Resumen de gastos";
    protected final String ASISTENCIA_PERSONAL = "Asistencia del personal";

    protected final String PAGO_POR_VENTA_HEADER = "Pago por ventas";

    protected final String RESUMEN_AREA = "Resumen de area de venta";
    /**
     * String referentes al almacen
     */
    protected final String STOCK_BALANCE = "Balance de stock en almacen",
            STOCK_FORMAT = "En Almacen | Diferencia ",
            COMPROBANTE_TRANSACCION = "Comprobante de Transaccion";

    /**
     * String referentes a los pagos de trabajadores
     */
    protected final String PAGO_TRABAJADOR = "Comprobante de pago a trabajador";

    public AbstractTicketFormatter() {
    }

    public static boolean SHOW_PRICES() {
        return SHOW_PRICES;
    }

    public static void setSHOW_PRICES(boolean SHOW_PRICES) {
        AbstractTicketFormatter.SHOW_PRICES = SHOW_PRICES;
    }

    protected String createTableLineForIPVReg(IpvRegistro x) {
        //"Ini. |Ent. |Disp.|Cons.|Final."
        String ret = "";
        String separador = "|";
        int Separators = 5;
        int LenghtPerSeparator = (int) ((Ticket.PAPER_LENGHT - Separators) / 5);
        ret += fillSpace(x.getInicio(), LenghtPerSeparator) + separador;
        ret += fillSpace(x.getEntrada(), LenghtPerSeparator) + separador;
        ret += fillSpace(x.getDisponible(), LenghtPerSeparator) + separador;
        ret += fillSpace(x.getConsumo(), LenghtPerSeparator) + separador;
        ret += fillSpace(x.getFinalCalculado(), LenghtPerSeparator);
        return ret;
    }

    protected void addCustomMetaData(Ticket t, String customHeader, Date fecha) {
        t.addLineSeperator();
        t.newLine();
        t.setText(customHeader);
        t.newLine();
        t.alignLeft();
        t.setText(this.FECHA + R.DATE_FORMAT.format(fecha));
        t.newLine();
        t.addLineSeperator();
        t.newLine();
    }

    protected void addMetaData(Ticket t, Orden o, Date date) {
        t.addLineSeperator();
        t.newLine();
        t.alignRight();
        t.setText(this.FECHA + R.DATE_FORMAT.format(o.getVentafecha()) + R.TIME_FORMAT.format(date));
        t.newLine();
        t.setText(this.ORDEN + o.getCodOrden());
        t.newLine();
        if (o.getMesacodMesa() != null) {
            t.setText(this.MESA + o.getMesacodMesa());
            t.newLine();
        }
        t.alignLeft();
        t.setText(this.CAMARERO);
        t.newLine();
        t.alignRight();
        t.setText(o.getPersonalusuario().getDatosPersonales().getNombre());
        t.newLine();
        t.addLineSeperator();
        t.newLine();
    }

    //
    // Private printing format methods
    //
    protected void addTotalAndFinal(Ticket t, float total) {
        addTotal(t, total);
        addFinal(t);
    }

    protected void addFinal(Ticket t) {
        t.feed((byte) 3);
        t.finit();
    }

    protected void addFocusedMessage(Ticket t, String sms) {
        t.addLineSeperator();
        t.newLine();
        t.addLineSeperator();
        t.newLine();
        t.alignCenter();
        t.setText(sms);
        t.newLine();
        t.addLineSeperator();
        t.newLine();
    }

    protected void addHeader(Ticket t) {
        t.resetAll();
        t.initialize();
        t.feedBack((byte) 2);
        t.alignCenter();
        if (SHOW_HEADER) {
            t.setText(CABECERA);
            t.newLine();
            t.setText(R.getRestName());
            t.newLine();
        } else {
            t.setText("BIENVENIDO");
            t.newLine();
            t.newLine();
        }
    }

    protected void addTotal(Ticket t, float total) {
        t.addLineSeperator();
        t.newLine();
        if (this.SHOW_PRICES) {
            t.alignRight();
            t.setText(String.format(this.TOTAL_VENTAS + "%.2f" + this.MONEDA, total));
            t.newLine();
            if (this.PRINT_SECOND_COIN) {
                if (this.monedaCUC) {
                    if (this.REDONDEO_POR_EXCESO) {
                        t.setText(this.TOTAL_VENTAS + utils.redondeoPorExcesoFloat(total * R.getCoinChange()) + this.MN);
                    } else {
                        t.setText(String.format(this.TOTAL_VENTAS + "%.2f" + this.MN, utils.setDosLugaresDecimalesFloat(total * R.getCoinChange())));
                    }
                } else {
                    if (this.REDONDEO_POR_EXCESO) {
                        t.setText(this.TOTAL_VENTAS + utils.redondeoPorExcesoFloat(total / R.getCoinChange()) + this.CUC);
                    } else {
                        t.setText(String.format(this.TOTAL_VENTAS + "%.2f" + this.CUC, utils.setDosLugaresDecimalesFloat(total / R.getCoinChange())));
                    }
                }
            }
        }
    }

    protected String fillSpace(float number, int finalLenght) {
        String ret = "" + utils.setDosLugaresDecimalesFloat(number);
        while (ret.length() < finalLenght) {
            ret += " ";
        }
        return ret;
    }

    protected float addPvOrden(Ticket t, List<ProductovOrden> prods) {
        float total = 0;
        for (ProductovOrden x : prods) {
            t.alignLeft();
//            t.setText(x.getCantidad() + " " + x.getNombreProductoVendido());
            adjusttextToLine(t, x.getNombreProductoVendido(), "" + x.getCantidad(), false);
            t.alignRight();
            if (this.SHOW_PRICES) {
                if (x.getOrden().getDeLaCasa()) {
                    if (this.PRINT_GASTOS_EN_AUTORIZOS) {
                        t.setText(utils.setDosLugaresDecimales(x.getCantidad() * x.getProductoVenta().getGasto()));
                    } else {
                        t.setText(utils.setDosLugaresDecimales(x.getCantidad() * x.getPrecioVendido()));
                    }
                } else {
                    t.setText(utils.setDosLugaresDecimales(x.getCantidad() * x.getPrecioVendido()));
                }
                t.newLine();
            }
            t.alignLeft();
            if (x.getAgregos() != null) {
                if (!x.getAgregos().isEmpty()) {
                    for (ProductovOrden a : x.getAgregos()) {
                        adjusttextToLine(t, a.getNombreProductoVendido(),
                                TAB + (a.getCantidad()), true);
//                            t.setText(TAB + (a.getCantidad() - a.getEnviadosacocina()) + " " + a.getNombreProductoVendido());
//                            t.newLine();
                        t.alignRight();
                        t.setText(utils.setDosLugaresDecimales((a.getCantidad()) * a.getPrecioVendido()));
                        t.newLine();
                        t.alignLeft();
//                        a.setEnviadosacocina(a.getCantidad());
//                        try {
//                            ProductovOrdenDAO.getInstance().edit(a);
//                        } catch (Exception ex) {
//                            Logger.getLogger(Impresion.class.getName()).log(Level.SEVERE, null, ex);
//                        }

                    }
                }
            }
            if (x.getOrden().getDeLaCasa()) {
                if (this.PRINT_GASTOS_EN_AUTORIZOS) {
                    total += x.getCantidad() * x.getProductoVenta().getGasto();
                } else {
                    total += x.getCantidad() * x.getPrecioVendido();
                }
            } else {
                total += x.getCantidad() * x.getPrecioVendido();
            }
        }
        return total;
    }

    protected Doc createDoc(byte[] bytes) {
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        Doc doc = new SimpleDoc(bytes, flavor, null);
        return doc;

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

}
