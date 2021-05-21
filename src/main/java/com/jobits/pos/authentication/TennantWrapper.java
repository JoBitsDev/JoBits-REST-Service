/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.authentication;

import com.jobits.pos.persistence.pasarela.Cuenta;
import com.jobits.pos.persistence.pasarela.Token;
import javax.persistence.EntityManagerFactory;
import org.jobits.db.core.domain.ConexionPropertiesModel;
import org.jobits.db.core.domain.TipoConexion;

/**
 * FirstDream
 *
 * @author Jorge
 *
 */
public class TennantWrapper {

    private Token tennantToken;
    private ConexionPropertiesModel tennantEmf;

    public TennantWrapper(Token tennantToken, ConexionPropertiesModel propertiesModel) {
        this.tennantToken = tennantToken;
        this.tennantEmf = propertiesModel;
    }

    public TennantWrapper(Token tennantToken, Cuenta c) {
        this.tennantToken = tennantToken;
        this.tennantEmf = new ConexionPropertiesModel() {
            @Override
            public String getContrasena() {
                return c.getBaseDatos().getContrasena();
            }

            @Override
            public String getDriver() {
                return c.getBaseDatos().getDriver();
            }

            @Override
            public String getNombreUbicacion() {
                return tennantToken.getToken();
            }

            @Override
            public TipoConexion getTipoUbicacion() {
                return TipoConexion.MASTER;//TODO: fix
            }

            @Override
            public String getUrl() {
                return c.getBaseDatos().getUrl();
            }

            @Override
            public String getUsuario() {
                return c.getBaseDatos().getUsuario();
            }
        };
    }

    public Token getTennantToken() {
        return tennantToken;
    }

    public void setTennantToken(Token tennantToken) {
        this.tennantToken = tennantToken;
    }

    public ConexionPropertiesModel getTennantProperties() {
        return tennantEmf;
    }

    public void setTennantProperties(ConexionPropertiesModel tennantEmf) {
        this.tennantEmf = tennantEmf;
    }

}
