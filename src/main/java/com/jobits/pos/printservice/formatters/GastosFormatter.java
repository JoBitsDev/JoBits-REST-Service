/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.printservice.formatters;

import com.jobits.pos.persistence.GastoVenta;
import com.jobits.pos.printservice.Ticket;
import com.jobits.utils.R;
import java.util.Collections;
import java.util.List;
import javax.print.Doc;

/**
 *
 * @author Javier
 */
public class GastosFormatter extends AbstractTicketFormatter{
    
    List<GastoVenta> lista;

    public GastosFormatter(List<GastoVenta> lista) {
        this.lista = lista;
    }
    
    @Override
    public Doc formatPrint(String printingOutput) {
        if (printingOutput.equals(PrintFormatter.TICKET_PRINTER)) {
           return formatResumenGastos();
        }
        throw new IllegalArgumentException("Bad call");
    }

    private Doc formatResumenGastos() {
        Collections.sort(lista, (GastoVenta o1, GastoVenta o2) -> o1.getGasto().getNombre().compareTo(o2.getGasto().getNombre()));

        Ticket t = new Ticket();

        addHeader(t);

        t.addLineSeperator();
        t.newLine();
        t.alignCenter();
        t.setText(GASTO_HEADER);
        t.newLine();
        t.alignRight();
        t.setText(FECHA + R.DATE_FORMAT.format(lista.get(0).getVenta().getFecha()));
        t.newLine();
        t.addLineSeperator();
        t.newLine();

        float total = 0;
        for (GastoVenta x : lista) {
            t.alignLeft();
            t.setText(x.getGasto().getNombre());
            if (x.getDescripcion() != null) {
                t.setText(" (" + x.getDescripcion() + ")");
            }
            t.newLine();
            t.alignRight();
            t.setText(x.getImporte().toString() + R.getCoinSuffix());
            t.newLine();
            t.newLine();
            total += x.getImporte();
        }

        addTotalAndFinal(t, total);

        return createDoc(t.finalCommandSet().getBytes());
    }
    
}
