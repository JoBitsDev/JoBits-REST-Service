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
 *
 * @author Jorge
 *
 */
@JsonRootName(value = "gastoVentaPK")
@Embeddable
public class GastoVentaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "gastocod_gasto")
    private String gastocodGasto;
    @Basic(optional = false)
    @Column(name = "ventaid")
    private Integer ventaid;

    public GastoVentaPK() {
    }

    public GastoVentaPK(String gastocodGasto, Integer ventaid) {
        this.gastocodGasto = gastocodGasto;
        this.ventaid = ventaid;
    }

    public String getGastocodGasto() {
        return gastocodGasto;
    }

    public void setGastocodGasto(String gastocodGasto) {
        this.gastocodGasto = gastocodGasto;
    }

    public int getVentafecha() {
        return ventaid;
    }

    public void setVentafecha(int ventaid) {
        this.ventaid = ventaid;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.gastocodGasto);
        hash = 67 * hash + Objects.hashCode(this.ventaid);
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
        final GastoVentaPK other = (GastoVentaPK) obj;
        if (!Objects.equals(this.gastocodGasto, other.gastocodGasto)) {
            return false;
        }
        if (!Objects.equals(this.ventaid, other.ventaid)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restManager.persistencia.GastoVentaPK[ gastocodGasto=" + gastocodGasto + ", ventaid=" + ventaid + " ]";
    }

}
