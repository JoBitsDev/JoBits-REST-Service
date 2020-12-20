/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.printservice.formatters;

import javax.print.Doc;

/**
 *
 * @author Javier
 */
public interface PrintFormatter {
    
    public static final String TICKET_PRINTER = "TICKET_PRINTER", PDF_PRINTER = "PDF_PRINTER";
    
    public Doc formatPrint (String printingOutput);
      
    
}
