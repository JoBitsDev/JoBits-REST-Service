/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.printservice.formatters;

import com.jobits.pos.persistence.ProductovOrden;
import com.jobits.pos.printservice.Ticket;
import java.util.Date;
import java.util.List;
import javax.print.Doc;

/**
 *
 * @author Javier
 */
public class CasaFormatter extends AbstractTicketFormatter {

    List<ProductovOrden> resumenVentasCasa;
    Date fecha;

    public CasaFormatter(List<ProductovOrden> resumenVentasCasa, Date fecha) {
        this.resumenVentasCasa = resumenVentasCasa;
        this.fecha = fecha;
    }

    @Override
    public Doc formatPrint(String printingOutput) {
        if (printingOutput.equals(PrintFormatter.TICKET_PRINTER)) {
            return formatResumenCasa();
        }
        throw new IllegalArgumentException("Bad call");
    }

    private Doc formatResumenCasa() {
        Ticket t = new Ticket();
        addHeader(t);

        addCustomMetaData(t, RESUMEN_CONSUMO_CASA, fecha);

        float total = addPvOrden(t, resumenVentasCasa);

        addTotalAndFinal(t, total);

        return createDoc(t.finalCommandSet().getBytes());
    }

}
