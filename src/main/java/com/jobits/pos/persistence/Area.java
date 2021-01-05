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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codArea", scope = Area.class)
@Entity
@Table(name = "area")
@NamedQueries({
    @NamedQuery(name = "Area.findAll", query = "SELECT a FROM Area a"),
    @NamedQuery(name = "Area.findByCodArea", query = "SELECT a FROM Area a WHERE a.codArea = :codArea"),
    @NamedQuery(name = "Area.findByCapacidad", query = "SELECT a FROM Area a WHERE a.capacidad = :capacidad"),
    @NamedQuery(name = "Area.findByNombre", query = "SELECT a FROM Area a WHERE a.nombre = :nombre")})
public class Area implements Serializable, Comparable<Area> {

    @JsonIgnore
    @OneToMany(mappedBy = "areacodArea")
    private List<Impresora> impresoraList;

    @JsonIgnore
    @OneToMany(mappedBy = "areacodArea")
    private List<PuestoTrabajo> puestoTrabajoList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_area")
    private String codArea;
    @Column(name = "capacidad")
    private Integer capacidad;
    @Column(name = "porciento_por_servicio")
    private Integer porcientoPorServicio;
    @Column(name = "nombre")
    private String nombre;
    @JsonIgnore
    @ManyToMany()
    @JoinTable(name = "carta_area", joinColumns = {
        @JoinColumn(name = "areacod_area", referencedColumnName = "cod_area")}, inverseJoinColumns = {
        @JoinColumn(name = "cartacod_carta", referencedColumnName = "cod_carta")})
    private List<Carta> cartaList;
    @JsonIgnore
    @OneToMany(mappedBy = "areacodArea", cascade = {CascadeType.ALL})
    private List<Mesa> mesaList;

    public Area() {
    }

    public Area(String codArea) {
        this.codArea = codArea;
    }

    public String getCodArea() {
        return codArea;
    }

    public void setCodArea(String codArea) {
        this.codArea = codArea;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Carta> getCartaList() {
        return cartaList;
    }

    public void setCartaList(List<Carta> cartaList) {
        this.cartaList = cartaList;
    }

    public List<Mesa> getMesaList() {
        return mesaList;
    }

    public void setMesaList(List<Mesa> mesaList) {
        this.mesaList = mesaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codArea != null ? codArea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Area)) {
            return false;
        }
        Area other = (Area) object;
        if ((this.codArea == null && other.codArea != null) || (this.codArea != null && !this.codArea.equals(other.codArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre + " [ " + codArea + " ]";
    }

    public Integer getPorcientoPorServicio() {
        return porcientoPorServicio;
    }

    public void setPorcientoPorServicio(Integer porcientoPorServicio) {
        this.porcientoPorServicio = porcientoPorServicio;
    }

    public List<PuestoTrabajo> getPuestoTrabajoList() {
        return puestoTrabajoList;
    }

    public void setPuestoTrabajoList(List<PuestoTrabajo> puestoTrabajoList) {
        this.puestoTrabajoList = puestoTrabajoList;
    }

    public List<Impresora> getImpresoraList() {
        return impresoraList;
    }

    public void setImpresoraList(List<Impresora> impresoraList) {
        this.impresoraList = impresoraList;
    }

    @Override
    public int compareTo(Area o) {
        return this.nombre.compareTo(o.getNombre());
    }
}
