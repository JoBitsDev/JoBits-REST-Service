/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.persistence;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.jobits.utils.utils;

/**
 * FirstDream
 *
 * @author Jorge
 *
 */
@Entity
@Table(name = "pago")
@NamedQueries({
    @NamedQuery(name = "Pago.findAll", query = "SELECT p FROM Pago p"),
    @NamedQuery(name = "Pago.findByIdPago", query = "SELECT p FROM Pago p WHERE p.pagoPK.idPago = :idPago"),
    @NamedQuery(name = "Pago.findByFacturaidFactura", query = "SELECT p FROM Pago p WHERE p.pagoPK.facturaidFactura = :facturaidFactura"),
    @NamedQuery(name = "Pago.findByMontoPagado", query = "SELECT p FROM Pago p WHERE p.montoPagado = :montoPagado"),
    @NamedQuery(name = "Pago.findByNoCheque", query = "SELECT p FROM Pago p WHERE p.noCheque = :noCheque"),
    @NamedQuery(name = "Pago.findByNoRecibo", query = "SELECT p FROM Pago p WHERE p.noRecibo = :noRecibo"),
    @NamedQuery(name = "Pago.findByACuenta", query = "SELECT p FROM Pago p WHERE p.aCuenta = :aCuenta")})
public class Pago implements Serializable {

    @Column(name = "es_cobro")
    private Boolean esCobro;

    @JoinColumn(name = "id_cuenta_a_rebajar", referencedColumnName = "id_cuenta")
    @ManyToOne
    private ContabilidadCuenta idCuentaARebajar;

    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PagoPK pagoPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto_pagado")
    private Float montoPagado;
    @Column(name = "no_cheque")
    private String noCheque;
    @Column(name = "no_recibo")
    private String noRecibo;
    @Column(name = "a_cuenta")
    private Boolean aCuenta;
    @JoinColumn(name = "facturaid_factura", referencedColumnName = "id_factura", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Factura factura;

    public Pago() {
    }

    public Pago(PagoPK pagoPK) {
        this.pagoPK = pagoPK;
    }

    public Pago(int idPago, int facturaidFactura) {
        this.pagoPK = new PagoPK(idPago, facturaidFactura);
    }

    public PagoPK getPagoPK() {
        return pagoPK;
    }

    public void setPagoPK(PagoPK pagoPK) {
        this.pagoPK = pagoPK;
    }

    public Float getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(Float montoPagado) {
        this.montoPagado = montoPagado;
    }

    public String getNoCheque() {
        return noCheque;
    }

    public void setNoCheque(String noCheque) {
        this.noCheque = noCheque;
    }

    public String getNoRecibo() {
        return noRecibo;
    }

    public void setNoRecibo(String noRecibo) {
        this.noRecibo = noRecibo;
    }

    public Boolean getACuenta() {
        return aCuenta;
    }

    public void setACuenta(Boolean aCuenta) {
        this.aCuenta = aCuenta;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pagoPK != null ? pagoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pago)) {
            return false;
        }
        Pago other = (Pago) object;
        if ((this.pagoPK == null && other.pagoPK != null) || (this.pagoPK != null && !this.pagoPK.equals(other.pagoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getFactura().toString() + "Pagar: " + utils.setDosLugaresDecimalesFloat(getMontoPagado());
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ContabilidadCuenta getIdCuentaARebajar() {
        return idCuentaARebajar;
    }

    public void setIdCuentaARebajar(ContabilidadCuenta idCuentaARebajar) {
        this.idCuentaARebajar = idCuentaARebajar;
    }

    public Boolean getEsCobro() {
        return esCobro;
    }

    public void setEsCobro(Boolean esCobro) {
        this.esCobro = esCobro;
    }

}
