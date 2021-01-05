/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.persistence;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * FirstDream
 *
 * @author Jorge
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "nombreSeccion", scope = Seccion.class)
@Entity
@Table(name = "seccion")
@NamedQueries({
    @NamedQuery(name = "Seccion.findAll", query = "SELECT s FROM Seccion s"),
    @NamedQuery(name = "Seccion.findByNombreSeccion", query = "SELECT s FROM Seccion s WHERE s.nombreSeccion = :nombreSeccion"),
    @NamedQuery(name = "Seccion.findByDescripcion", query = "SELECT s FROM Seccion s WHERE s.descripcion = :descripcion")})
public class Seccion implements Serializable, Comparable<Seccion> {

    @JoinTable(name = "seccion_agregada", joinColumns = {
        @JoinColumn(name = "agregada_en", referencedColumnName = "nombre_seccion")}, inverseJoinColumns = {
        @JoinColumn(name = "seccionnombre_seccion", referencedColumnName = "nombre_seccion")})
    @ManyToMany
    private List<Seccion> agregadoEn;
    @ManyToMany(mappedBy = "agregadoEn")
    private List<Seccion> agregos;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nombre_seccion")
    private String nombreSeccion;
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "seccionnombreSeccion", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductoVenta> productoVentaList;
    @JoinColumn(name = "cartacod_carta", referencedColumnName = "cod_carta")
    @ManyToOne
    @JsonIgnore
    private Carta cartacodCarta;

    public Seccion() {
    }

    public Seccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }

    public String getNombreSeccion() {
        return nombreSeccion;
    }

    public void setNombreSeccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<ProductoVenta> getProductoVentaList() {
        return productoVentaList;
    }

    public void setProductoVentaList(List<ProductoVenta> productoVentaList) {
        this.productoVentaList = productoVentaList;
    }

    public Carta getCartacodCarta() {
        return cartacodCarta;
    }

    public void setCartacodCarta(Carta cartacodCarta) {
        this.cartacodCarta = cartacodCarta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombreSeccion != null ? nombreSeccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Seccion)) {
            return false;
        }
        Seccion other = (Seccion) object;
        if ((this.nombreSeccion == null && other.nombreSeccion != null) || (this.nombreSeccion != null && !this.nombreSeccion.equals(other.nombreSeccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreSeccion;
    }

    public List<Seccion> getAgregadoEn() {
        return agregadoEn;
    }

    public void setAgregadoEn(List<Seccion> agregadoEn) {
        this.agregadoEn = agregadoEn;
    }

    public List<Seccion> getAgregos() {
        return agregos;
    }

    public void setAgregos(List<Seccion> agregos) {
        this.agregos = agregos;
    }

    @Override
    public int compareTo(Seccion o) {
        return this.nombreSeccion.compareTo(o.getNombreSeccion());
    }

}
