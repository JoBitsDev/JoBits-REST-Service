/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.printservice.formatters;

import com.jobits.pos.persistence.Transaccion;
import com.jobits.pos.printservice.Ticket;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.print.Doc;

/**
 *
 * @author Javier
 */
public class ComprobanteTransaccionFormatter extends AbstractTicketFormatter{
    
    List<Transaccion> selectedsObjects;

    public ComprobanteTransaccionFormatter(List<Transaccion> selectedsObjects) {
        this.selectedsObjects = selectedsObjects;
    }

    @Override
    public Doc formatPrint(String printingOutput) {
        if (printingOutput.equals(PrintFormatter.TICKET_PRINTER)) {
           return formatComprobanteTransaccion();
        }
        throw new IllegalArgumentException("Bad call");
    }

    private Doc formatComprobanteTransaccion() {
         Collections.sort(selectedsObjects, (o1, o2) -> {
            return o1.getInsumocodInsumo().getNombre().compareTo(o2.getInsumocodInsumo().getNombre());
        });
        Ticket t = new Ticket();
        addHeader(t);
        addCustomMetaData(t, COMPROBANTE_TRANSACCION, new Date());

        t.alignCenter();
        t.alignCenter();
        Transaccion tr = selectedsObjects.get(0);
        String tipoTrans = "Tipo Transaccion: ";
        if (tr.getTransaccionEntrada() != null) {
            tipoTrans += "ENTRADA";
        } else if (tr.getTransaccionMerma() != null) {
            tipoTrans += tr.getTransaccionMerma().getRazon().toUpperCase();
        } else {
            tipoTrans += "SALIDA: " + tr.getTransaccionSalida().getCocinacodCocina();
        }

        t.setText(tipoTrans);
        t.newLine();
        t.newLine();

        for (Transaccion in : selectedsObjects) {
            t.alignLeft();

            t.setText(in.getInsumocodInsumo().toString() + "{" + in.getInsumocodInsumo().getUm() + "}");
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
