/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.printservice.formatters;

import com.jobits.pos.persistence.Cocina;
import com.jobits.pos.persistence.IpvRegistro;
import com.jobits.pos.printservice.Ticket;
import com.jobits.utils.R;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.print.Doc;

/**
 *
 * @author Javier
 */
public class IPVPuntoElaboracionFomatter extends AbstractTicketFormatter {

    List<IpvRegistro> registros;

    public IPVPuntoElaboracionFomatter(List<IpvRegistro> registros) {
        this.registros = registros;
    }

    @Override
    public Doc formatPrint(String printingOutput) {
        if (printingOutput.equals(PrintFormatter.TICKET_PRINTER)) {
            return formatIPVPuntoElaboracion();
        }
        throw new IllegalArgumentException("Bad call");
    }

    private Doc formatIPVPuntoElaboracion() {
        Cocina c = registros.get(0).getIpv().getCocina();
        Date fecha = registros.get(0).getIpvRegistroPK().getFecha();
        Collections.sort(registros,
                (o1, o2) -> {
                    return o1.getIpv().getInsumo().getNombre().
                            compareTo(o2.getIpv().getInsumo().getNombre());
                });

        Ticket t = new Ticket();

        addHeader(t);

        t.addLineSeperator();
        t.newLine();
        t.setText(IPV_HEADER);
        t.newLine();
        t.alignLeft();
        t.setText(IPV_PUNTO_ELAB);
        t.newLine();
        t.alignRight();
        t.setText(c.getNombreCocina());
        t.newLine();
        t.alignLeft();
        t.setText(FECHA + R.DATE_FORMAT.format(fecha));
        t.newLine();
        t.addLineSeperator();
        t.alignCenter();
        t.newLine();
        t.setText(IPV_TABLE_HEADER);
        t.newLine();
        t.addLineSeperator();
        t.newLine();

        for (IpvRegistro x : registros) {
            t.setText(x.getIpv().getInsumo().toString());
            t.newLine();
            t.setText(createTableLineForIPVReg(x));
            t.newLine();
            t.newLine();
        }

        addFinal(t);

        return createDoc(t.finalCommandSet().getBytes());
    }

}
