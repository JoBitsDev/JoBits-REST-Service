/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.printservice;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FirstDream
 *
 * @author Jorge
 *
 */
public class ComponentPrinter implements Printable{

    private final Component comp;

    public ComponentPrinter(Component comp) {
        this.comp = comp;
    }

    @Override
    public int print(Graphics g, PageFormat format, int page_index)
            throws PrinterException {
        if (page_index > 0) {
            return Printable.NO_SUCH_PAGE;
        }

        // get the bounds of the component
        Dimension dim = comp.getSize();
        double cHeight = dim.getHeight();
        double cWidth = dim.getWidth();

        // get the bounds of the printable area
        double pHeight = format.getImageableHeight();
        double pWidth = format.getImageableWidth();

        double pXStart = format.getImageableX();
        double pYStart = format.getImageableY();

        double xRatio = pWidth / cWidth;
        double yRatio = pHeight / cHeight;

        Graphics2D g2 = (Graphics2D) g;
        g2.translate(pXStart, pYStart);
        g2.scale(xRatio, yRatio);
        comp.paint(g2);

        return Printable.PAGE_EXISTS;
    }

    public static void printComponent(Component comp, String docName, boolean showPrintDialog) {
        PrinterJob pjob = PrinterJob.getPrinterJob();
        PageFormat preformat = pjob.defaultPage();
        preformat.setOrientation(PageFormat.PORTRAIT);
        pjob.setPrintable(new ComponentPrinter(comp), preformat);
        pjob.setJobName(docName);
        if (showPrintDialog) {
            if (pjob.printDialog()) {
                try {
                    pjob.print();
                } catch (PrinterException ex) {
                    Logger.getLogger(ComponentPrinter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            try {
                pjob.print();
            } catch (PrinterException ex) {
                Logger.getLogger(ComponentPrinter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
