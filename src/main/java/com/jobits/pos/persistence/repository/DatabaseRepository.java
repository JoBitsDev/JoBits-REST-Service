/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.persistence.repository;

import com.jobits.pos.persistence.pasarela.BaseDatos;
import com.jobits.pos.persistence.pasarela.Cuenta;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FirstDream
 *
 * @author Jorge
 *
 */
public class DatabaseRepository {

    private static final String PU_DEFAULT_NAME = "pasarela_loggeo";
    private static final HashMap<String, String> PU_DEFAULT_PROPERTIES = getDefaultProperties();
    private static final EntityManagerFactory DEFAULT_EMF = Persistence.createEntityManagerFactory(PU_DEFAULT_NAME, PU_DEFAULT_PROPERTIES);
    
    public static EntityManagerFactory getDefaultFactory() {
        return DEFAULT_EMF;
    }

    public static EntityManager getDefaultConnection() {
        return getDefaultFactory().createEntityManager();
    }

    public static EntityManagerFactory getFactoryFrom(Cuenta cuenta) {
        return Persistence.createEntityManagerFactory(PU_DEFAULT_NAME, getPropertiesFrom(cuenta.getBaseDatos()));
    }

    private static HashMap<String, String> getDefaultProperties() {
        HashMap<String, String> ret = new HashMap<>();
        ret.put(PersistenceProperties.URL.getName(), "jdbc:postgresql://localhost:5432/pasarela_loggeo");
        ret.put(PersistenceProperties.USER.getName(), "pasarela_user");
        ret.put(PersistenceProperties.PASSWORD.getName(), "pasarela_user");
        ret.put(PersistenceProperties.DRIVER.getName(), "org.postgresql.Driver");
        return ret;
    }

    private static Map getPropertiesFrom(BaseDatos baseDatos) {
        HashMap<String, String> ret = new HashMap<>();
        ret.put(PersistenceProperties.URL.getName(), baseDatos.getUrl());
        ret.put(PersistenceProperties.USER.getName(), baseDatos.getUsuario());
        ret.put(PersistenceProperties.PASSWORD.getName(), baseDatos.getContrasena());
        ret.put(PersistenceProperties.DRIVER.getName(), baseDatos.getDriver());
        return ret;
    }

}
