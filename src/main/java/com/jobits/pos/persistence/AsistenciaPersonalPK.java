/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jobits.pos.persistence;

import com.fasterxml.jackson.annotation.JsonRootName;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * FirstDream
 * @author Jorge
 * 
 */
@JsonRootName(value = "asistenciaPersonalPK")
@Embeddable
public class AsistenciaPersonalPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ventaid")
    private Integer ventaid;
    @Basic(optional = false)
    @Column(name = "personalusuario")
    private String personalusuario;

    public AsistenciaPersonalPK() {
    }

    public AsistenciaPersonalPK(int ventafecha, String personalusuario) {
        this.ventaid = ventafecha;
        this.personalusuario = personalusuario;
    }

    public Integer getVentaid() {
        return ventaid;
    }

    public void setVentafecha(int ventafecha) {
        this.ventaid = ventafecha;
    }

    public String getPersonalusuario() {
        return personalusuario;
    }

    public void setPersonalusuario(String personalusuario) {
        this.personalusuario = personalusuario;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.ventaid;
        hash = 97 * hash + Objects.hashCode(this.personalusuario);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AsistenciaPersonalPK other = (AsistenciaPersonalPK) obj;
        if (this.ventaid != other.ventaid) {
            return false;
        }
        if (!Objects.equals(this.personalusuario, other.personalusuario)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "restManager.persistencia.AsistenciaPersonalPK[ ventafecha=" + ventaid + ", personalusuario=" + personalusuario + " ]";
    }

}
