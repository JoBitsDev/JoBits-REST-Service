/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.printservice.formatters;

import com.jobits.pos.persistence.Almacen;
import com.jobits.pos.persistence.InsumoAlmacen;
import com.jobits.pos.printservice.Ticket;
import com.jobits.utils.utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import javax.print.Doc;

/**
 *
 * @author Javier
 */
public class StockBalanceFormatter extends AbstractTicketFormatter {

    Almacen a;

    public StockBalanceFormatter(Almacen a) {
        this.a = a;
    }

    @Override
    public Doc formatPrint(String printingOutput) {
        if (printingOutput.equals(PrintFormatter.TICKET_PRINTER)) {
            return formatStoBalance();
        }
        throw new IllegalArgumentException("Bad call");
    }

    private Doc formatStoBalance() {

        ArrayList<InsumoAlmacen> ret = new ArrayList<>(a.getInsumoAlmacenList());
        Collections.sort(ret, (InsumoAlmacen o1, InsumoAlmacen o2) -> o1.getInsumo().getNombre().compareTo(o2.getInsumo().getNombre()));
        Ticket t = new Ticket();
        addHeader(t);
        addCustomMetaData(t, STOCK_BALANCE, new Date());

        t.alignCenter();
        t.setText(STOCK_FORMAT);
        t.newLine();
        t.newLine();

        for (InsumoAlmacen in : ret) {
            float diferencia = utils.setDosLugaresDecimalesFloat(in.getCantidad() - in.getInsumo().getStockEstimation());
            if (diferencia < 0) {
                t.alignLeft();
                t.setText(in.getInsumo().toString());
                t.newLine();
                t.alignRight();
                t.setText("" + in.getCantidad());
                t.setText(utils.setDosLugaresDecimalesFloat(in.getCantidad()) + " | " + diferencia);
            }
        }

        t.newLine();
        t.addLineSeperator();

        addFinal(t);
        return createDoc(t.finalCommandSet().getBytes());
    }

}
