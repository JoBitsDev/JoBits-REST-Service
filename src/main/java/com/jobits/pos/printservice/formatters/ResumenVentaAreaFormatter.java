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
public class ResumenVentaAreaFormatter extends AbstractTicketFormatter {

    List<ProductovOrden> resumenVentaPorArea;
    Date fecha;

    public ResumenVentaAreaFormatter(List<ProductovOrden> resumenVentaPorArea, Date fecha) {
        this.resumenVentaPorArea = resumenVentaPorArea;
        this.fecha = fecha;
    }

    @Override
    public Doc formatPrint(String printingOutput) {
        if (printingOutput.equals(PrintFormatter.TICKET_PRINTER)) {
            return formatResumenVentaArea();
        }
        throw new IllegalArgumentException("Bad call");
    }

    private Doc formatResumenVentaArea() {
        Ticket t = new Ticket();

        addHeader(t);

        addCustomMetaData(t, RESUMEN_AREA, fecha);

        float total = addPvOrden(t, resumenVentaPorArea);

        addTotalAndFinal(t, total);

        return createDoc(t.finalCommandSet().getBytes());
    }

}
