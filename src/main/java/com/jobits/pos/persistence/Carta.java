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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * FirstDream
 *
 * @author Jorge
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codCarta", scope = Carta.class)
@Entity
@Table(name = "carta")
@NamedQueries({
    @NamedQuery(name = "Carta.findAll", query = "SELECT c FROM Carta c"),
    @NamedQuery(name = "Carta.findByCodCarta", query = "SELECT c FROM Carta c WHERE c.codCarta = :codCarta"),
    @NamedQuery(name = "Carta.findByNombreCarta", query = "SELECT c FROM Carta c WHERE c.nombreCarta = :nombreCarta"),
    @NamedQuery(name = "Carta.findByMonedaPrincipal", query = "SELECT c FROM Carta c WHERE c.monedaPrincipal = :monedaPrincipal")})
public class Carta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "disponible_desde")
    @Temporal(TemporalType.TIME)
    private Date disponibleDesde;
    @Column(name = "disponible_hasta")
    @Temporal(TemporalType.TIME)
    private Date disponibleHasta;
    @JoinTable(name = "carta_area", joinColumns = {
        @JoinColumn(name = "cartacod_carta", referencedColumnName = "cod_carta")}, inverseJoinColumns = {
        @JoinColumn(name = "areacod_area", referencedColumnName = "cod_area")})
    @ManyToMany
    private List<Area> areaList;

    @Id
    @Basic(optional = false)
    @Column(name = "cod_carta")
    private String codCarta;
    @Basic(optional = false)
    @Column(name = "nombre_carta")
    private String nombreCarta;
    @Column(name = "moneda_principal")
    private String monedaPrincipal;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cartacodCarta")
    private List<Seccion> seccionList;

    public Carta() {
    }

    public Carta(String codCarta) {
        this.codCarta = codCarta;
    }

    public Carta(String codCarta, String nombreCarta) {
        this.codCarta = codCarta;
        this.nombreCarta = nombreCarta;
    }

    public String getCodCarta() {
        return codCarta;
    }

    public void setCodCarta(String codCarta) {
        this.codCarta = codCarta;
    }

    public String getNombreCarta() {
        return nombreCarta;
    }

    public void setNombreCarta(String nombreCarta) {
        this.nombreCarta = nombreCarta;
    }

    public String getMonedaPrincipal() {
        return monedaPrincipal;
    }

    public void setMonedaPrincipal(String monedaPrincipal) {
        this.monedaPrincipal = monedaPrincipal;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }

    public List<Seccion> getSeccionList() {
        return seccionList;
    }

    public void setSeccionList(List<Seccion> seccionList) {
        this.seccionList = seccionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCarta != null ? codCarta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Carta)) {
            return false;
        }
        Carta other = (Carta) object;
        if ((this.codCarta == null && other.codCarta != null) || (this.codCarta != null && !this.codCarta.equals(other.codCarta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "(" + getMonedaPrincipal() + ")-" + getNombreCarta() + "[" + getCodCarta() + "]";
    }

    public Date getDisponibleDesde() {
        return disponibleDesde;
    }

    public void setDisponibleDesde(Date disponibleDesde) {
        this.disponibleDesde = disponibleDesde;
    }

    public Date getDisponibleHasta() {
        return disponibleHasta;
    }

    public void setDisponibleHasta(Date disponibleHasta) {
        this.disponibleHasta = disponibleHasta;
    }
}
