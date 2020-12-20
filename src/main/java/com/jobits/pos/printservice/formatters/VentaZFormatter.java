/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.printservice.formatters;

import com.jobits.pos.domain.VentaDAO1;
import com.jobits.pos.persistence.GastoVenta;
import com.jobits.pos.persistence.Venta;
import com.jobits.pos.printservice.Ticket;
import com.jobits.utils.utils;
import javax.print.Doc;

/**
 *
 * @author Javier
 */
public class VentaZFormatter extends AbstractTicketFormatter {

    Venta ventaToFormat;

    public VentaZFormatter(Venta ventaToFormat) {
        this.ventaToFormat = ventaToFormat;
    }

    @Override
    public Doc formatPrint(String printingOutput) {
        if (printingOutput.equals(PrintFormatter.TICKET_PRINTER)) {
           return formatZ();
        }
        throw new IllegalArgumentException("Bad call");
    }

    private Doc formatZ() {
        if (ventaToFormat == null) {
            throw new NullPointerException();
        }

        Ticket t = new Ticket();

        addHeader(t);

        addCustomMetaData(t, Z, ventaToFormat.getFecha());

        float total = addPvOrden(t, VentaDAO1.getResumenVentas(ventaToFormat));
        addTotal(t, total);

        float porciento = VentaDAO1.getValorTotalPorcientoVenta(ventaToFormat);
        t.newLine();
        t.setText("(+/-) " + "Porciento: " + utils.setDosLugaresDecimalesFloat(porciento) + MONEDA);
        t.newLine();
        float totalExtracciones = 0;
        for (GastoVenta extraccion : ventaToFormat.getGastoVentaList()) {
            totalExtracciones += extraccion.getImporte();
        }

        t.setText("Total extracciones: " + utils.setDosLugaresDecimales(totalExtracciones));
        t.setText("Total Real: " + utils.setDosLugaresDecimales(total + porciento - totalExtracciones));
        addFinal(t);

        return createDoc(t.finalCommandSet().getBytes());

    }
}
