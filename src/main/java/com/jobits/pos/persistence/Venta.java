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
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 * FirstDream
 *
 * @author Jorge
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "fecha", scope = Venta.class)
@Entity
@Table(name = "venta")
@NamedQueries({
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v"),
    @NamedQuery(name = "Venta.findByFecha", query = "SELECT v FROM Venta v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "Venta.findByVentaTotal", query = "SELECT v FROM Venta v WHERE v.ventaTotal = :ventaTotal"),
    @NamedQuery(name = "Venta.findByVentagastosEninsumos", query = "SELECT v FROM Venta v WHERE v.ventagastosEninsumos = :ventagastosEninsumos"),
    @NamedQuery(name = "Venta.findByHoraPico", query = "SELECT v FROM Venta v WHERE v.horaPico = :horaPico"),
    @NamedQuery(name = "Venta.findByVentagastosPagotrabajadores", query = "SELECT v FROM Venta v WHERE v.ventagastosPagotrabajadores = :ventagastosPagotrabajadores"),
    @NamedQuery(name = "Venta.findByVentagastosGastos", query = "SELECT v FROM Venta v WHERE v.ventagastosGastos = :ventagastosGastos"),
    @NamedQuery(name = "Venta.findByVentagastosEninsumos", query = "SELECT v FROM Venta v WHERE v.ventagastosEninsumos = :ventagastosEninsumos")})
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venta")
    private List<AsistenciaPersonal> asistenciaPersonalList;

    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venta")
    private List<GastoVenta> gastoVentaList;
    @Column(name = "hora_pico")
    private Integer horaPico;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_venta")
    @SequenceGenerator(name = "id_venta", allocationSize = 1)
    private Integer id;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "diaVenta")
    private List<IpvVentaRegistro> ipvVentaRegistroList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ventaid")
    private List<Orden> ordenList;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "venta_total")
    private Double ventaTotal;
    @Column(name = "ventagastos_eninsumos")
    private Double ventagastosEninsumos;
    @Column(name = "ventagastos_gastos")
    private Float ventagastosGastos;
    @Column(name = "ventagastos_pagotrabajadores")
    private Float ventagastosPagotrabajadores;
    @Column(name = "ventapropina")
    private Float ventapropina;

    public Venta() {
    }

    public Venta(Integer id) {
        this.id = id;
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
        final Venta other = (Venta) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }



    public List<AsistenciaPersonal> getAsistenciaPersonalList() {
        return asistenciaPersonalList;
    }

    public void setAsistenciaPersonalList(List<AsistenciaPersonal> asistenciaPersonalList) {
        this.asistenciaPersonalList = asistenciaPersonalList;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<GastoVenta> getGastoVentaList() {
        return gastoVentaList;
    }

    public void setGastoVentaList(List<GastoVenta> gastoVentaList) {
        this.gastoVentaList = gastoVentaList;
    }

    public Integer getHoraPico() {
        return horaPico;
    }

    public void setHoraPico(Integer horaPico) {
        this.horaPico = horaPico;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<IpvVentaRegistro> getIpvVentaRegistroList() {
        return ipvVentaRegistroList;
    }

    public void setIpvVentaRegistroList(List<IpvVentaRegistro> ipvVentaRegistroList) {
        this.ipvVentaRegistroList = ipvVentaRegistroList;
    }

    public List<Orden> getOrdenList() {
        return ordenList;
    }

    public void setOrdenList(List<Orden> ordenList) {
        this.ordenList = ordenList;
    }

    public Double getVentaTotal() {
        return ventaTotal;
    }

    public void setVentaTotal(Double ventaTotal) {
        this.ventaTotal = ventaTotal;
    }

    public Double getVentagastosEninsumos() {
        return ventagastosEninsumos;
    }

    public void setVentagastosEninsumos(Double ventagastosEninsumos) {
        this.ventagastosEninsumos = ventagastosEninsumos;
    }

    public Float getVentagastosGastos() {
        return ventagastosGastos;
    }

    public void setVentagastosGastos(Float ventagastosGastos) {
        this.ventagastosGastos = ventagastosGastos;
    }

    public Float getVentagastosPagotrabajadores() {
        return ventagastosPagotrabajadores;
    }

    public void setVentagastosPagotrabajadores(Float ventagastosPagotrabajadores) {
        this.ventagastosPagotrabajadores = ventagastosPagotrabajadores;
    }

    public Float getVentapropina() {
        return ventapropina;
    }

    public void setVentapropina(Float ventapropina) {
        this.ventapropina = ventapropina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fecha != null ? fecha.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Venta ID: " + id;
    }

}
