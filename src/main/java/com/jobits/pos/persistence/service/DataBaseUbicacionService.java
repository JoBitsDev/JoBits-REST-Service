/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.persistence.service;

import com.root101.clean.core.app.usecase.AbstractUseCaseImpl;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jobits.db.core.domain.ConexionPropertiesModel;
import org.jobits.db.core.domain.ConexionPropertiesWrapperModel;
import org.jobits.db.core.usecase.UbicacionConexionService;

/**
 *
 * JoBits
 *
 * @author Jorge
 *
 */
public class DataBaseUbicacionService extends AbstractUseCaseImpl implements UbicacionConexionService {

    private static DataBaseUbicacionService INSTANCE = new DataBaseUbicacionService();

    public static DataBaseUbicacionService getInstance() {
        return INSTANCE;
    }

    private DataBaseUbicacionService() {
    }

    @Override
    public void editConexion(ConexionPropertiesModel cpm, int i) {
        throw new UnsupportedOperationException(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSelectedConexion(ConexionPropertiesModel cpm) {
        ConexionWrapper.INSTANCE.setSelectedUbicacion(cpm);
    }

    @Override
    public ConexionPropertiesWrapperModel getUbicaciones() {
        return ConexionWrapper.INSTANCE;
    }

    private static class ConexionWrapper implements ConexionPropertiesWrapperModel {

        private final Logger LOG = Logger.getLogger(ConexionWrapper.class.getName());

        private ConexionPropertiesModel selectedUbicacion;

        private static ConexionWrapper INSTANCE = new ConexionWrapper();

        private ConexionWrapper() {
        }

        @Override
        public int getSelectedUbicacion() {
            return 0;
        }

        @Override
        public void setSelectedUbicacion(int i) {
            LOG.log(Level.INFO, "setSelectedUbicacion{0}", i);
        }

        public void setSelectedUbicacion(ConexionPropertiesModel selected) {
            this.selectedUbicacion = selected;
        }

        @Override
        public ConexionPropertiesModel getUbicacionActiva() {
            return selectedUbicacion;
        }

        @Override
        public void setUbicacionSeleccionada(ConexionPropertiesModel cpm) {
            LOG.log(Level.INFO, "Ubicacion Seleccionada{0}", cpm.getNombreUbicacion());
        }

        @Override
        public ConexionPropertiesModel[] getUbicaciones() {
            LOG.log(Level.INFO, "getUbicaciones");
            throw new UnsupportedOperationException(); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setUbicaciones(ConexionPropertiesModel[] cpms) {
            LOG.log(Level.INFO, "setUbicaciones{0}", cpms.length);
        }

    }

}
