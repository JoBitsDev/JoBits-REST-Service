/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.printservice.formatters;

import com.jobits.pos.persistence.AsistenciaPersonal;
import com.jobits.pos.printservice.Ticket;
import com.jobits.utils.R;
import java.util.Collections;
import java.util.List;
import javax.print.Doc;

/**
 *
 * @author Javier
 */
public class PersonalTrabajandoFormatter extends AbstractTicketFormatter {

    List<AsistenciaPersonal> lista;

    public PersonalTrabajandoFormatter(List<AsistenciaPersonal> lista) {
        this.lista = lista;
    }

    @Override
    public Doc formatPrint(String printingOutput) {
        if (printingOutput.equals(PrintFormatter.TICKET_PRINTER)) {
            return formatResumenPersonalTrabajando();
        }
        throw new IllegalArgumentException("Bad call");
    }

    private Doc formatResumenPersonalTrabajando() {
        Collections.sort(lista, (AsistenciaPersonal o1, AsistenciaPersonal o2) -> o1.getPersonal().getDatosPersonales().getNombre().compareTo(o2.getPersonal().getDatosPersonales().getNombre()));

        Ticket t = new Ticket();

        addHeader(t);

        t.addLineSeperator();
        t.newLine();
        t.alignCenter();
        t.setText(ASISTENCIA_PERSONAL);
        t.newLine();
        t.alignRight();
        t.setText(FECHA + R.DATE_FORMAT.format(lista.get(0).getVenta().getFecha()));
        t.newLine();
        t.addLineSeperator();
        t.newLine();

        float total = 0;
        for (AsistenciaPersonal x : lista) {
            t.alignLeft();
            t.setText(x.getPersonal().getDatosPersonales().getNombre());
            t.newLine();
            t.newLine();
        }
        addFinal(t);
//        addTotalAndFinal(t, total);

        return createDoc(t.finalCommandSet().getBytes());
    }
}