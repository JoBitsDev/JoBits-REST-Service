/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jobits.pos.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * FirstDream
 * @author Jorge
 * 
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class ,property = "insumoElaboradoPK",scope = InsumoElaborado.class)
@Entity
@Table(name = "insumo_elaborado")
@NamedQueries({
    @NamedQuery(name = "InsumoElaborado.findAll", query = "SELECT i FROM InsumoElaborado i"),
    @NamedQuery(name = "InsumoElaborado.findByInsumocodNombre", query = "SELECT i FROM InsumoElaborado i WHERE i.insumoElaboradoPK.insumocodNombre = :insumocodNombre"),
    @NamedQuery(name = "InsumoElaborado.findByInsumocodInsumo", query = "SELECT i FROM InsumoElaborado i WHERE i.insumoElaboradoPK.insumocodInsumo = :insumocodInsumo"),
    @NamedQuery(name = "InsumoElaborado.findByCantidad", query = "SELECT i FROM InsumoElaborado i WHERE i.cantidad = :cantidad"),
    @NamedQuery(name = "InsumoElaborado.findByCosto", query = "SELECT i FROM InsumoElaborado i WHERE i.costo = :costo")})
public class InsumoElaborado implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InsumoElaboradoPK insumoElaboradoPK;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private float cantidad;
    @Basic(optional = false)
    @Column(name = "costo")
    private float costo;
    @JoinColumn(name = "insumocod_nombre", referencedColumnName = "cod_insumo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Insumo insumo;
    @JoinColumn(name = "insumocod_insumo", referencedColumnName = "cod_insumo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Insumo insumo_derivado_nombre;

    public InsumoElaborado() {
    }

    public InsumoElaborado(InsumoElaboradoPK insumoElaboradoPK) {
        this.insumoElaboradoPK = insumoElaboradoPK;
    }

    public InsumoElaborado(InsumoElaboradoPK insumoElaboradoPK, float cantidad, float costo) {
        this.insumoElaboradoPK = insumoElaboradoPK;
        this.cantidad = cantidad;
        this.costo = costo;
    }

    public InsumoElaborado(String insumocodNombre, String insumocodInsumo) {
        this.insumoElaboradoPK = new InsumoElaboradoPK(insumocodNombre, insumocodInsumo);
    }

    public InsumoElaboradoPK getInsumoElaboradoPK() {
        return insumoElaboradoPK;
    }

    public void setInsumoElaboradoPK(InsumoElaboradoPK insumoElaboradoPK) {
        this.insumoElaboradoPK = insumoElaboradoPK;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public Insumo getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
    }

    public Insumo getInsumo_derivado_nombre() {
        return insumo_derivado_nombre;
    }

    public void setInsumo_derivado_nombre(Insumo insumo_derivado_nombre) {
        this.insumo_derivado_nombre = insumo_derivado_nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (insumoElaboradoPK != null ? insumoElaboradoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InsumoElaborado)) {
            return false;
        }
        InsumoElaborado other = (InsumoElaborado) object;
        if ((this.insumoElaboradoPK == null && other.insumoElaboradoPK != null) || (this.insumoElaboradoPK != null && !this.insumoElaboradoPK.equals(other.insumoElaboradoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restManager.persistencia.InsumoElaborado[ insumoElaboradoPK=" + insumoElaboradoPK + " ]";
    }

}
