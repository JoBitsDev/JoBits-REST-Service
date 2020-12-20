/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.printservice.formatters;

import com.jobits.pos.persistence.AsistenciaPersonal;
import com.jobits.pos.persistence.Personal;
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
public class ComprobantePagoFormatter extends AbstractTicketFormatter {

    Personal personal;

    public ComprobantePagoFormatter(Personal personal) {
        this.personal = personal;
    }

    @Override
    public Doc formatPrint(String printingOutput) {
        if (printingOutput.equals(PrintFormatter.TICKET_PRINTER)) {
            return formatComprobantePago();
        }
        throw new IllegalArgumentException("Bad call");
    }

    private Doc formatComprobantePago() {
        ArrayList<AsistenciaPersonal> lista = new ArrayList<>(personal.getAsistenciaPersonalList());
        Collections.sort(lista);

        Ticket t = new Ticket();

        addHeader(t);

        t.addLineSeperator();
        t.newLine();
        t.alignCenter();
        t.setText(PAGO_TRABAJADOR);
        t.newLine();
        t.alignRight();
        t.setText(FECHA + R.DATE_FORMAT.format(R.TODAYS_DATE));
        t.newLine();
        t.setText(personal.getDatosPersonales().getNombre());
        t.newLine();
        t.addLineSeperator();
        t.newLine();

        float total = 0, propina = 0, aMayores = 0, salario = 0;
        for (AsistenciaPersonal a : lista) {
            if (a.getVenta().getFecha().compareTo(personal.getUltimodiaPago()) >= 0) {
                if (a.getVenta().getVentaTotal() != null) {
                    t.alignLeft();
                    t.setText(R.DATE_FORMAT.format(a.getAsistenciaPersonalPK().getVentaid()));
                    t.newLine();
                    t.alignRight();
                    t.setText(utils.setDosLugaresDecimales(a.getPago()));
                    t.newLine();
                    if (a.getPropina() != null) {
                        if (a.getPropina() > 0) {
                            t.setText("Propina: " + utils.setDosLugaresDecimales(a.getPropina()));
                        }
                    }
                    propina += a.getPropina();
                    salario += a.getPago();
                    if (a.getAMayores() != null) {
                        aMayores += a.getAMayores();
                    }
                }
            }
        }
        total = aMayores + salario;
        t.addLineSeperator();
        t.alignRight();
        if (propina > 0) {
            t.setText("Total Propina: " + utils.setDosLugaresDecimales(propina));
        }
        t.newLine();
        t.setText("'Total A Mayores: " + utils.setDosLugaresDecimales(aMayores));
        t.newLine();
        t.setText("'Total a Pagar: " + utils.setDosLugaresDecimales(total));
        t.newLine();
        addFinal(t);

        return createDoc(t.finalCommandSet().getBytes());

    }

}
