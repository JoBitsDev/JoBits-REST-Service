/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.persistence;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codMesa", scope = Mesa.class)
@Entity
@Table(name = "mesa")
@NamedQueries({
    @NamedQuery(name = "Mesa.findAll", query = "SELECT m FROM Mesa m ORDER BY m.codMesa ASC"),
    @NamedQuery(name = "Mesa.findFromArea", query = "SELECT m FROM Mesa m where m.areacodArea.codArea = :areaCod"),
    @NamedQuery(name = "Mesa.findByCodMesa", query = "SELECT m FROM Mesa m WHERE m.codMesa = :codMesa"),
    @NamedQuery(name = "Mesa.findByEstado", query = "SELECT m FROM Mesa m WHERE m.estado = :estado"),
    @NamedQuery(name = "Mesa.findByEstallena", query = "SELECT m FROM Mesa m WHERE m.estallena = :estallena"),
    @NamedQuery(name = "Mesa.findByCapacidadMax", query = "SELECT m FROM Mesa m WHERE m.capacidadMax = :capacidadMax"),
    @NamedQuery(name = "Mesa.findByUbicacion", query = "SELECT m FROM Mesa m WHERE m.ubicacion = :ubicacion")})
public class Mesa implements Serializable, Comparable<Mesa> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_mesa")
    private String codMesa;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    public static final String PROP_ESTADO = "estado";
    @Column(name = "estallena")
    private Boolean estallena;
    @Column(name = "capacidad_max")
    private Integer capacidadMax;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ubicacion")
    private Double ubicacion;
    @JoinColumn(name = "areacod_area", referencedColumnName = "cod_area")
    @ManyToOne
    private Area areacodArea;
    @JsonIgnore
    @OneToMany(mappedBy = "mesacodMesa")
    private List<Orden> ordenList;

    public Mesa() {
    }

    public Mesa(String codMesa) {
        this.codMesa = codMesa;
    }

    public Mesa(String codMesa, String estado) {
        this.codMesa = codMesa;
        this.estado = estado;
    }

    @Override
    public int compareTo(Mesa o) {
        String[] a = codMesa.split("-");
        String[] b = o.getCodMesa().split("-");
        return a[1].compareTo(b[1]);
    }

    public String getCodMesa() {
        return codMesa;
    }

    public void setCodMesa(String codMesa) {
        this.codMesa = codMesa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        String oldEstado = this.estado;
        this.estado = estado;
        propertyChangeSupport.firePropertyChange(PROP_ESTADO, oldEstado, estado);
    }

    public Boolean getEstallena() {
        return estallena;
    }

    public void setEstallena(Boolean estallena) {
        this.estallena = estallena;
    }

    public Integer getCapacidadMax() {
        return capacidadMax;
    }

    public void setCapacidadMax(Integer capacidadMax) {
        this.capacidadMax = capacidadMax;
    }

    public Double getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Double ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Area getAreacodArea() {
        return areacodArea;
    }

    public void setAreacodArea(Area areacodArea) {
        this.areacodArea = areacodArea;
    }

    public List<Orden> getOrdenList() {
        return ordenList;
    }

    public void setOrdenList(List<Orden> ordenList) {
        this.ordenList = ordenList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codMesa != null ? codMesa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mesa)) {
            return false;
        }
        Mesa other = (Mesa) object;
        if ((this.codMesa == null && other.codMesa != null) || (this.codMesa != null && !this.codMesa.equals(other.codMesa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Mesa: " + codMesa + " [ " + capacidadMax + " pax ]";
    }

    public boolean isVacia() {
        return getEstado().equals("vacia");
    }

    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

}
