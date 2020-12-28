/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.persistence;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import javax.validation.constraints.NotNull;

/**
 * FirstDream
 *
 * @author Jorge
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codOrden", scope = Orden.class)
@Entity
@Table(name = "orden")
@NamedQueries({
    @NamedQuery(name = "Orden.findAll", query = "SELECT o FROM Orden o"),
    @NamedQuery(name = "Orden.findByCodOrden", query = "SELECT o FROM Orden o WHERE o.codOrden = :codOrden"),
    @NamedQuery(name = "Orden.findByHoraComenzada", query = "SELECT o FROM Orden o WHERE o.horaComenzada = :horaComenzada"),
    @NamedQuery(name = "Orden.findByHoraTerminada", query = "SELECT o FROM Orden o WHERE o.horaTerminada = :horaTerminada"),
    @NamedQuery(name = "Orden.findByDeLaCasa", query = "SELECT o FROM Orden o WHERE o.deLaCasa = :deLaCasa"),
    @NamedQuery(name = "Orden.findByPorciento", query = "SELECT o FROM Orden o WHERE o.porciento = :porciento"),
    @NamedQuery(name = "Orden.findByGananciaXporciento", query = "SELECT o FROM Orden o WHERE o.gananciaXporciento = :gananciaXporciento"),
    @NamedQuery(name = "Orden.findByOrdenvalorMonetario", query = "SELECT o FROM Orden o WHERE o.ordenvalorMonetario = :ordenvalorMonetario"),
    @NamedQuery(name = "Orden.findByOrdengastoEninsumos", query = "SELECT o FROM Orden o WHERE o.ordengastoEninsumos = :ordengastoEninsumos")})
public class Orden implements Serializable, Comparable<Orden> {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ventafecha")
    @Temporal(TemporalType.DATE)
    private Date ventafecha;
    @JoinColumn(name = "ventaid", referencedColumnName = "id")
    @ManyToOne
    private Venta ventaid;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_orden")
    private String codOrden;
    @Column(name = "hora_comenzada")
    @Temporal(TemporalType.TIME)
    private Date horaComenzada;
    @Column(name = "hora_terminada")
    @Temporal(TemporalType.TIME)
    private Date horaTerminada;
    @Basic(optional = false)
    @Column(name = "de_la_casa")
    private boolean deLaCasa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "porciento")
    private Float porciento;
    @Column(name = "ganancia_xporciento")
    private Float gananciaXporciento;
    @Column(name = "ordenvalor_monetario")
    private Float ordenvalorMonetario;
    @Column(name = "ordengasto_eninsumos")
    private Float ordengastoEninsumos;
    @JoinColumn(name = "clienteid_cliente", referencedColumnName = "id_cliente")
    @ManyToOne
    private Cliente clienteIdCliente;
    @JoinColumn(name = "mesacod_mesa", referencedColumnName = "cod_mesa", nullable = true)
    @ManyToOne(optional = true)
    private Mesa mesacodMesa;
    @JoinColumn(name = "personalusuario", referencedColumnName = "usuario", nullable = true)
    @ManyToOne(optional = true)
    private Personal personalusuario;
    @OneToMany(mappedBy = "orden", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ProductovOrden> productovOrdenList;

    public Orden() {
    }

    public Orden(String codOrden) {
        this.codOrden = codOrden;
    }

    public Orden(String codOrden, boolean deLaCasa) {
        this.codOrden = codOrden;
        this.deLaCasa = deLaCasa;
    }

    public String getCodOrden() {
        return codOrden;
    }

    public void setCodOrden(String codOrden) {
        this.codOrden = codOrden;
    }

    public Date getHoraComenzada() {
        return horaComenzada;
    }

    public void setHoraComenzada(Date horaComenzada) {
        this.horaComenzada = horaComenzada;
    }

    public Date getHoraTerminada() {
        return horaTerminada;
    }

    public void setHoraTerminada(Date horaTerminada) {
        this.horaTerminada = horaTerminada;
    }

    public boolean getDeLaCasa() {
        return deLaCasa;
    }

    public void setDeLaCasa(boolean deLaCasa) {
        this.deLaCasa = deLaCasa;
    }

    public Float getPorciento() {
        return porciento;
    }

    public void setPorciento(Float porciento) {
        this.porciento = porciento;
    }

    public Float getGananciaXporciento() {
        return gananciaXporciento;
    }

    public void setGananciaXporciento(Float gananciaXporciento) {
        this.gananciaXporciento = gananciaXporciento;
    }

    public Float getOrdenvalorMonetario() {
        return ordenvalorMonetario;
    }

    public void setOrdenvalorMonetario(Float ordenvalorMonetario) {
        this.ordenvalorMonetario = ordenvalorMonetario;
    }

    public Float getOrdengastoEninsumos() {
        return ordengastoEninsumos;
    }

    public void setOrdengastoEninsumos(Float ordengastoEninsumos) {
        this.ordengastoEninsumos = ordengastoEninsumos;
    }

    public Cliente getClienteIdCliente() {
        return clienteIdCliente;
    }

    public void setClienteIdCliente(Cliente clienteIdCliente) {
        this.clienteIdCliente = clienteIdCliente;
    }

    public Mesa getMesacodMesa() {
        return mesacodMesa;
    }

    public void setMesacodMesa(Mesa mesacodMesa) {
        this.mesacodMesa = mesacodMesa;
    }

    public Personal getPersonalusuario() {
        return personalusuario;
    }

    public void setPersonalusuario(Personal personalusuario) {
        this.personalusuario = personalusuario;
    }

    public List<ProductovOrden> getProductovOrdenList() {
        List<ProductovOrden> aux = new ArrayList<>();
        for (ProductovOrden productovOrden : productovOrdenList) {
            if (productovOrden.getAgregadoA() == null) {
                aux.add(productovOrden);
                if (productovOrden.getAgregos() != null) {
                    aux.addAll(productovOrden.getAgregos());
                }
            }
        }
        return aux;
    }

    public void setProductovOrdenList(List<ProductovOrden> productovOrdenList) {
        this.productovOrdenList = productovOrdenList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codOrden != null ? codOrden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orden)) {
            return false;
        }
        Orden other = (Orden) object;
        if ((this.codOrden == null && other.codOrden != null) || (this.codOrden != null && !this.codOrden.equals(other.codOrden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return codOrden;
    }

    @Override
    public int compareTo(Orden o) {
        int o2 = Integer.parseInt(o.getCodOrden().split("-")[1]);
        int o1 = Integer.parseInt(getCodOrden().split("-")[1]);

        if (o1 > o2) {
            return 1;
        }
        if (o1 < o2) {
            return -1;
        }
        return 0;
    }

    public Date getVentafecha() {
        return ventafecha;
    }

    public void setVentafecha(Date ventafecha) {
        this.ventafecha = ventafecha;
    }

    public Venta getVentaid() {
        return ventaid;
    }

    public void setVentaid(Venta ventaid) {
        this.ventaid = ventaid;
    }

}
