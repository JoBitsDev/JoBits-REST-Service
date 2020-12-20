/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jobits.pos.persistence;

import java.io.Serializable;
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
@Entity
@Table(name = "transaccion_transformacion")
@NamedQueries({
    @NamedQuery(name = "TransaccionTransformacion.findAll", query = "SELECT t FROM TransaccionTransformacion t"),
    @NamedQuery(name = "TransaccionTransformacion.findByTransaccionnoTransaccion", query = "SELECT t FROM TransaccionTransformacion t WHERE t.transaccionTransformacionPK.transaccionnoTransaccion = :transaccionnoTransaccion"),
    @NamedQuery(name = "TransaccionTransformacion.findByInsumocodInsumo", query = "SELECT t FROM TransaccionTransformacion t WHERE t.transaccionTransformacionPK.insumocodInsumo = :insumocodInsumo"),
    @NamedQuery(name = "TransaccionTransformacion.findByCantidadUsada", query = "SELECT t FROM TransaccionTransformacion t WHERE t.cantidadUsada = :cantidadUsada"),
    @NamedQuery(name = "TransaccionTransformacion.findByCantidadCreada", query = "SELECT t FROM TransaccionTransformacion t WHERE t.cantidadCreada = :cantidadCreada"),
    @NamedQuery(name = "TransaccionTransformacion.findByDireccionInversa", query = "SELECT t FROM TransaccionTransformacion t WHERE t.direccionInversa = :direccionInversa")})
public class TransaccionTransformacion implements Serializable {

    @Column(name = "costo_unitario")
    private Float costoUnitario;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TransaccionTransformacionPK transaccionTransformacionPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad_usada")
    private Float cantidadUsada;
    @Column(name = "cantidad_creada")
    private Float cantidadCreada;
    @Column(name = "direccion_inversa")
    private Boolean direccionInversa;
    @JoinColumn(name = "insumocod_insumo", referencedColumnName = "cod_insumo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Insumo insumo;
    @JoinColumn(name = "transaccionno_transaccion", referencedColumnName = "no_transaccion", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Transaccion transaccion;

    public TransaccionTransformacion() {
    }

    public TransaccionTransformacion(TransaccionTransformacionPK transaccionTransformacionPK) {
        this.transaccionTransformacionPK = transaccionTransformacionPK;
    }

    public TransaccionTransformacion(int transaccionnoTransaccion, String insumocodInsumo) {
        this.transaccionTransformacionPK = new TransaccionTransformacionPK(transaccionnoTransaccion, insumocodInsumo);
    }

    public TransaccionTransformacionPK getTransaccionTransformacionPK() {
        return transaccionTransformacionPK;
    }

    public void setTransaccionTransformacionPK(TransaccionTransformacionPK transaccionTransformacionPK) {
        this.transaccionTransformacionPK = transaccionTransformacionPK;
    }

    public Float getCantidadUsada() {
        return cantidadUsada;
    }

    public void setCantidadUsada(Float cantidadUsada) {
        this.cantidadUsada = cantidadUsada;
    }

    public Float getCantidadCreada() {
        return cantidadCreada;
    }

    public void setCantidadCreada(Float cantidadCreada) {
        this.cantidadCreada = cantidadCreada;
    }

    public Boolean getDireccionInversa() {
        return direccionInversa;
    }

    public void setDireccionInversa(Boolean direccionInversa) {
        this.direccionInversa = direccionInversa;
    }

    public Insumo getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transaccionTransformacionPK != null ? transaccionTransformacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransaccionTransformacion)) {
            return false;
        }
        TransaccionTransformacion other = (TransaccionTransformacion) object;
        if (!this.insumo.equals(other.insumo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restManager.persistencia.TransaccionTransformacion[ transaccionTransformacionPK=" + transaccionTransformacionPK + " ]";
    }

    public Float getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(Float costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

}
