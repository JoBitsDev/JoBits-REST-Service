/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.printservice.formatters;

import com.jobits.pos.persistence.Orden;
import com.jobits.pos.printservice.Ticket;
import com.jobits.utils.utils;
import javax.print.Doc;

/**
 *
 * @author Javier
 */
public class OrdenFormatter extends AbstractTicketFormatter {

    Orden orden;
    boolean preview;

    public OrdenFormatter(Orden orden, boolean preview) {
        this.orden = orden;
        this.preview = preview;
    }

    @Override
    public Doc formatPrint(String printingOutput) {

        float total;

        Ticket t = new Ticket();

        addHeader(t);

        if (preview) {
            t.setText(PREVIEW);
            t.newLine();
        }
        if (orden.getDeLaCasa()) {
            t.doubleStrik(true);
            t.setText(DELACASA);
            t.doubleStrik(false);
            t.newLine();
        }

        addMetaData(t, orden, orden.getHoraComenzada());

        total = addPvOrden(t, orden.getProductovOrdenList());

        float subTotalPrint = utils.redondeoPorExcesoFloat(total);
        float sumaPorciento = utils.redondeoPorExcesoFloat((subTotalPrint * orden.getPorciento()) / 100);
        float totalPrint = subTotalPrint;
        t.alignRight();
        if (SHOW_SUBTOTAL) {
            t.setText(SUBTOTAL + subTotalPrint + MONEDA);
            t.newLine();
        }
        if (orden.getPorciento() != 0) {
            t.setText("+ " + orden.getPorciento() + PORCIENTO + sumaPorciento + MONEDA);
            totalPrint = utils.redondeoPorExcesoFloat(subTotalPrint + sumaPorciento);

        }
        t.newLine();
        t.newLine();

        addTotal(t, totalPrint);

        t.newLine();
        t.newLine();
        t.alignCenter();
        t.setText(this.PIE);

        addFinal(t);

        return createDoc(t.finalCommandSet().getBytes());
    }

}
