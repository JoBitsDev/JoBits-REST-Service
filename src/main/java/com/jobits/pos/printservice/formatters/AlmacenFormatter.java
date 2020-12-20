/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.printservice.formatters;


import com.jobits.pos.persistence.Almacen;
import com.jobits.pos.persistence.InsumoAlmacen;
import com.jobits.pos.printservice.Ticket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import javax.print.Doc;

/**
 *
 * @author Javier
 */
public class AlmacenFormatter extends AbstractTicketFormatter {

    Almacen almacen;

    public AlmacenFormatter(Almacen almacen) {
        this.almacen = almacen;
    }

    @Override
    public Doc formatPrint(String printingOutput) {
        if (printingOutput.equals(PrintFormatter.TICKET_PRINTER)) {
            return formatResumenAlmacen();
        }
        throw new IllegalArgumentException("Bad call");
    }

    private Doc formatResumenAlmacen() {
        ArrayList<InsumoAlmacen> ret = new ArrayList<>(almacen.getInsumoAlmacenList());
        Collections.sort(ret, (InsumoAlmacen o1, InsumoAlmacen o2) -> o1.getInsumo().getNombre().compareTo(o2.getInsumo().getNombre()));
        Ticket t = new Ticket();
        addHeader(t);
        addCustomMetaData(t, STOCK_BALANCE, new Date());

        t.alignCenter();
        t.setText(STOCK_FORMAT);
        t.newLine();
        t.newLine();

        for (InsumoAlmacen in : ret) {
            t.alignLeft();
            t.setText(in.getInsumo().toString());
            t.newLine();
            t.alignRight();
            t.setText("" + in.getCantidad());
//            t.setText(String.format("%.2f | %+.2f", in.getCantidadExistente(), in.getCantidadExistente() - in.getStockEstimation()));
            t.newLine();
        }

        t.newLine();
        t.addLineSeperator();

        addFinal(t);

        return createDoc(t.finalCommandSet().getBytes());
    }
}


