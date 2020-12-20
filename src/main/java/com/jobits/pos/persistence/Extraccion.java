/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jobits.pos.persistence;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * FirstDream
 * @author Jorge
 * 
 */
@Entity
@Table(name = "extraccion")
@NamedQueries({
    @NamedQuery(name = "Extraccion.findAll", query = "SELECT e FROM Extraccion e"),
    @NamedQuery(name = "Extraccion.findByCodExtraccion", query = "SELECT e FROM Extraccion e WHERE e.codExtraccion = :codExtraccion"),
    @NamedQuery(name = "Extraccion.findByNombre", query = "SELECT e FROM Extraccion e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Extraccion.findByDescripcion", query = "SELECT e FROM Extraccion e WHERE e.descripcion = :descripcion"),
    @NamedQuery(name = "Extraccion.findByMonto", query = "SELECT e FROM Extraccion e WHERE e.monto = :monto"),
    @NamedQuery(name = "Extraccion.findByHora", query = "SELECT e FROM Extraccion e WHERE e.hora = :hora")})
public class Extraccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_extraccion")
    private Integer codExtraccion;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto")
    private Float monto;
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;

    public Extraccion() {
    }

    public Extraccion(Integer codExtraccion) {
        this.codExtraccion = codExtraccion;
    }

    public Extraccion(Integer codExtraccion, String nombre) {
        this.codExtraccion = codExtraccion;
        this.nombre = nombre;
    }

    public Integer getCodExtraccion() {
        return codExtraccion;
    }

    public void setCodExtraccion(Integer codExtraccion) {
        this.codExtraccion = codExtraccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codExtraccion != null ? codExtraccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Extraccion)) {
            return false;
        }
        Extraccion other = (Extraccion) object;
        if ((this.codExtraccion == null && other.codExtraccion != null) || (this.codExtraccion != null && !this.codExtraccion.equals(other.codExtraccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restManager.persistencia.Extraccion[ codExtraccion=" + codExtraccion + " ]";
    }

}
