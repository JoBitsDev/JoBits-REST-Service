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

/**
 * FirstDream
 * @author Jorge
 * 
 */
@Embeddable
public class NotificacionEnvioCocinaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cocinacod_cocina")
    private String cocinacodCocina;
    @Basic(optional = false)
    @Column(name = "productov_ordenid")
    private Integer productovOrdenId;

    public NotificacionEnvioCocinaPK() {
    }

    public NotificacionEnvioCocinaPK(String cocinacodCocina, int productovOrdenproductoVentapCod) {
        this.cocinacodCocina = cocinacodCocina;
        this.productovOrdenId = productovOrdenproductoVentapCod;
    }

    public String getCocinacodCocina() {
        return cocinacodCocina;
    }

    public void setCocinacodCocina(String cocinacodCocina) {
        this.cocinacodCocina = cocinacodCocina;
    }

    public int getProductovOrdenId() {
        return productovOrdenId;
    }

    public void setProductovOrdenId(Integer productovOrdenId) {
        this.productovOrdenId = productovOrdenId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cocinacodCocina != null ? cocinacodCocina.hashCode() : 0);
        hash += (productovOrdenId != null ? productovOrdenId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificacionEnvioCocinaPK)) {
            return false;
        }
        NotificacionEnvioCocinaPK other = (NotificacionEnvioCocinaPK) object;
        if ((this.cocinacodCocina == null && other.cocinacodCocina != null) || (this.cocinacodCocina != null && !this.cocinacodCocina.equals(other.cocinacodCocina))) {
            return false;
        }
        if ((this.productovOrdenId == null && other.productovOrdenId != null) || (this.productovOrdenId != null && !this.productovOrdenId.equals(other.productovOrdenId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restManager.persistencia.NotificacionEnvioCocinaPK[ cocinacodCocina=" + cocinacodCocina + ", productovOrdenproductoVentapCod=" + productovOrdenId + ", productovOrdenordencodOrden=" +  " ]";
    }

}
