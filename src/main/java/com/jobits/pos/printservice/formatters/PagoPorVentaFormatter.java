/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.printservice.formatters;

import com.jobits.pos.adapters.repo.autenticacion.PersonalDAO;
import com.jobits.pos.domain.VentaDAO1;
import com.jobits.pos.persistence.Orden;
import com.jobits.pos.persistence.Personal;
import com.jobits.pos.persistence.ProductovOrden;
import com.jobits.pos.persistence.Venta;
import com.jobits.pos.printservice.Ticket;
import com.jobits.utils.R;
import com.jobits.utils.utils;
import java.util.ArrayList;
import java.util.Collections;
import javax.print.Doc;

/**
 *
 * @author Javier
 */
public class PagoPorVentaFormatter extends AbstractTicketFormatter {

    Venta instance;
    String usuario;

    public PagoPorVentaFormatter(Venta instance, String usuario) {
        this.instance = instance;
        this.usuario = usuario;
    }

    @Override
    public Doc formatPrint(String printingOutput) {
        if (printingOutput.equals(PrintFormatter.TICKET_PRINTER)) {
            return formatPagoPorVenta();
        }
        throw new IllegalArgumentException("Bad call");
    }

    private Doc formatPagoPorVenta() {
        Personal p = PersonalDAO.getInstance().find(usuario);
        ArrayList<Orden> lista = new ArrayList<>(instance.getOrdenList());
        Collections.sort(lista);

        Ticket t = new Ticket();

        addHeader(t);

        t.addLineSeperator();
        t.newLine();
        t.alignCenter();
        t.setText(PAGO_POR_VENTA_HEADER);
        t.newLine();
        t.alignRight();
        t.setText(FECHA + R.DATE_FORMAT.format(instance.getFecha()));
        t.newLine();
        t.setText(usuario);
        t.newLine();
        t.addLineSeperator();
        t.newLine();

        float total = 0;
        for (ProductovOrden pv : VentaDAO1.getResumenVentasCamarero(instance, p)) {
            if (pv.getProductoVenta().getPagoPorVenta() != null) {
                if (pv.getProductoVenta().getPagoPorVenta() != 0) {
                    t.alignLeft();
                    t.setText(pv.getCantidad() + " " + pv.getNombreProductoVendido());
                    t.newLine();
                    t.alignRight();
                    if (SHOW_PRICES) {
                        t.setText(utils.setDosLugaresDecimales(pv.getCantidad() * pv.getProductoVenta().getPagoPorVenta()));
                    }
                    t.newLine();
                    total += pv.getCantidad() * pv.getProductoVenta().getPagoPorVenta();
                }

            }
        }

        addTotalAndFinal(t, total);

        return createDoc(t.finalCommandSet().getBytes());

    }

}
