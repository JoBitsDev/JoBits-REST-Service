/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.context;

import com.jobits.pos.persistence.pasarela.Token;
import com.jobits.pos.persistence.repository.DatabaseRepository;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * JoBits
 *
 * @author Jorge
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Token t = DatabaseRepository.getDefaultConnection().find(Token.class, 1);
        DatabaseRepository.getDefaultConnection().remove(t);
//        File f = new File(sce.getServletContext().getRealPath("/") + "ubicaciones.json");
//        try {
//            f.createNewFile();
//        } catch (IOException ex) {
//            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    }
