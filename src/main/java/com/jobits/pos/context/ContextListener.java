/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.context;

import com.jobits.pos.persistence.pasarela.Token;
import com.jobits.pos.persistence.repository.DatabaseRepository;
import com.jobits.pos.persistence.service.DataBaseUbicacionService;
import javax.persistence.EntityManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.jobits.db.core.module.DataVersionControlModule;
import org.jobits.db.pool.ConnectionPoolHandler;

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
        EntityManager em = DatabaseRepository.getDefaultConnection();
        Token t = em.find(Token.class, 1);
        em.remove(t);
        com.jobits.pos.core.module.PosCoreModule.init();
        DataVersionControlModule.init();
        org.jobits.db.core.usecase.UbicacionConexionHandler.registerUbicacionConexionService(DataBaseUbicacionService.getInstance());
//        File f = new File(sce.getServletContext().getRealPath("/") + "ubicaciones.json");
//        try {
//            f.createNewFile();
//        } catch (IOException ex) {
//            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

}
