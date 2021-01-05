/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codInsumo", scope = Insumo.class)
@Entity
@Table(name = "insumo")
@NamedQueries({
    @NamedQuery(name = "Insumo.findAll", query = "SELECT i FROM Insumo i"),
    @NamedQuery(name = "Insumo.findByCodInsumo", query = "SELECT i FROM Insumo i WHERE i.codInsumo = :codInsumo"),
    @NamedQuery(name = "Insumo.findByNombre", query = "SELECT i FROM Insumo i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "Insumo.findByUm", query = "SELECT i FROM Insumo i WHERE i.um = :um"),
    @NamedQuery(name = "Insumo.findByElaborado", query = "SELECT i FROM Insumo i WHERE i.elaborado = :elaborado"),
    @NamedQuery(name = "Insumo.findByCostoPorUnidad", query = "SELECT i FROM Insumo i WHERE i.costoPorUnidad = :costoPorUnidad"),
    @NamedQuery(name = "Insumo.findByStockEstimation", query = "SELECT i FROM Insumo i WHERE i.stockEstimation = :stockEstimation"),
    @NamedQuery(name = "Insumo.findByCantidadCreada", query = "SELECT i FROM Insumo i WHERE i.cantidadCreada = :cantidadCreada")})
public class Insumo implements Serializable, Comparable<Insumo> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_insumo")
    private String codInsumo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "um")
    private String um;
    @Column(name = "elaborado")
    private Boolean elaborado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "costo_por_unidad")
    private Float costoPorUnidad;
    @Column(name = "stock_estimation")
    private Float stockEstimation;
    @Column(name = "cantidad_creada")
    private Float cantidadCreada;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "insumo", orphanRemoval = true)
    private List<ProductoInsumo> productoInsumoList;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "insumocodInsumo")
    private List<Transaccion> transaccionList;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "insumo")
    private List<InsumoAlmacen> insumoAlmacenList;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "insumo")
    private List<Ipv> ipvList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "insumo", orphanRemoval = true)
    private List<InsumoElaborado> insumoDerivadoList;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "insumo")
    private List<TransaccionTransformacion> transaccionTransformacionList;

    public Insumo() {
    }

    public Insumo(String codInsumo) {
        this.codInsumo = codInsumo;
    }

    public Insumo(String codInsumo, String nombre) {
        this.codInsumo = codInsumo;
        this.nombre = nombre;
    }

    public String getCodInsumo() {
        return codInsumo;
    }

    public void setCodInsumo(String codInsumo) {
        this.codInsumo = codInsumo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUm() {
        return um;
    }

    public void setUm(String um) {
        this.um = um;
    }

    public Boolean getElaborado() {
        return elaborado;
    }

    public void setElaborado(Boolean elaborado) {
        this.elaborado = elaborado;
    }

    public Float getCostoPorUnidad() {
        return costoPorUnidad;
    }

    public void setCostoPorUnidad(Float costoPorUnidad) {
        this.costoPorUnidad = costoPorUnidad;
    }

    public Float getStockEstimation() {
        return stockEstimation;
    }

    public void setStockEstimation(Float stockEstimation) {
        this.stockEstimation = stockEstimation;
    }

    public Float getCantidadCreada() {
        return cantidadCreada;
    }

    public void setCantidadCreada(Float cantidadCreada) {
        this.cantidadCreada = cantidadCreada;
    }

    public List<ProductoInsumo> getProductoInsumoList() {
        return productoInsumoList;
    }

    public void setProductoInsumoList(List<ProductoInsumo> productoInsumoList) {
        this.productoInsumoList = productoInsumoList;
    }

    public List<Transaccion> getTransaccionList() {
        return transaccionList;
    }

    public void setTransaccionList(List<Transaccion> transaccionList) {
        this.transaccionList = transaccionList;
    }

    public List<InsumoAlmacen> getInsumoAlmacenList() {
        return insumoAlmacenList;
    }

    public void setInsumoAlmacenList(List<InsumoAlmacen> insumoAlmacenList) {
        this.insumoAlmacenList = insumoAlmacenList;
    }

    public List<Ipv> getIpvList() {
        return ipvList;
    }

    public void setIpvList(List<Ipv> ipvList) {
        this.ipvList = ipvList;
    }

    public List<InsumoElaborado> getInsumoDerivadoList() {
        return insumoDerivadoList;
    }

    public void setInsumoDerivadoList(List<InsumoElaborado> insumoDerivadoList) {
        this.insumoDerivadoList = insumoDerivadoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codInsumo != null ? codInsumo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (object instanceof InsumoElaborado) {
            InsumoElaborado e = (InsumoElaborado) object;
            return e.getInsumo().getCodInsumo().equals(this.codInsumo);
        }
        if (!(object instanceof Insumo)) {
            return false;
        }
        Insumo other = (Insumo) object;
        if ((this.codInsumo == null && other.codInsumo != null) || (this.codInsumo != null && !this.codInsumo.equals(other.codInsumo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre + "(" + codInsumo + ")" + "(" + um + ")";
    }

    public List<TransaccionTransformacion> getTransaccionTransformacionList() {
        return transaccionTransformacionList;
    }

    public void setTransaccionTransformacionList(List<TransaccionTransformacion> transaccionTransformacionList) {
        this.transaccionTransformacionList = transaccionTransformacionList;
    }

    @Override
    public int compareTo(Insumo o) {
        return this.nombre.compareTo(o.getNombre());
    }

}
