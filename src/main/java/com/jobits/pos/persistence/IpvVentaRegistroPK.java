/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jobits.pos.persistence;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * JoBits
 * @author Jorge
 * 
 */
@Embeddable
public class IpvVentaRegistroPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "producto_ventap_cod")
    private String productoVentapCod;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ventaid")
    private int ventaid;

    public IpvVentaRegistroPK() {
    }

    public IpvVentaRegistroPK(String productoVentapCod, int ventaid) {
        this.productoVentapCod = productoVentapCod;
        this.ventaid = ventaid;
    }

    public String getProductoVentapCod() {
        return productoVentapCod;
    }

    public void setProductoVentapCod(String productoVentapCod) {
        this.productoVentapCod = productoVentapCod;
    }

    public int getVentaid() {
        return ventaid;
    }

    public void setVentaid(int ventaid) {
        this.ventaid = ventaid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoVentapCod != null ? productoVentapCod.hashCode() : 0);
        hash += (int) ventaid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IpvVentaRegistroPK)) {
            return false;
        }
        IpvVentaRegistroPK other = (IpvVentaRegistroPK) object;
        if ((this.productoVentapCod == null && other.productoVentapCod != null) || (this.productoVentapCod != null && !this.productoVentapCod.equals(other.productoVentapCod))) {
            return false;
        }
        if (this.ventaid != other.ventaid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jobits.pos.domain.models.IpvVentaRegistroPK[ productoVentapCod=" + productoVentapCod + ", ventaid=" + ventaid + " ]";
    }

}
