/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.persistence;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Entity
@Table(name = "contabilidad_cuenta")
@NamedQueries({
    @NamedQuery(name = "ContabilidadCuenta.findAll", query = "SELECT c FROM ContabilidadCuenta c"),
    @NamedQuery(name = "ContabilidadCuenta.findByIdCuenta", query = "SELECT c FROM ContabilidadCuenta c WHERE c.idCuenta = :idCuenta"),
    @NamedQuery(name = "ContabilidadCuenta.findByNombre", query = "SELECT c FROM ContabilidadCuenta c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ContabilidadCuenta.findByTipoCuenta", query = "SELECT c FROM ContabilidadCuenta c WHERE c.tipoCuenta = :tipoCuenta"),
    @NamedQuery(name = "ContabilidadCuenta.findByNumeroCuenta", query = "SELECT c FROM ContabilidadCuenta c WHERE c.numeroCuenta = :numeroCuenta")})
public class ContabilidadCuenta implements Serializable {

    @Column(name = "cierra")
    private Boolean cierra;

    @OneToMany(mappedBy = "idCuentaARebajar")
    private List<Pago> pagoList;

    @JoinColumn(name = "cuenta_enlazada", referencedColumnName = "id_cuenta")
    @ManyToOne
    private ContabilidadCuenta cuentaEnlazada;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cuenta")
    private Integer idCuenta;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "tipo_cuenta")
    private String tipoCuenta;
    @Column(name = "numero_cuenta")
    private String numeroCuenta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuentaDeudora")
    private List<Factura> facturasDeudoras;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuentaAcreedora")
    private List<Factura> facturasAcreedoras;
    @OneToMany(mappedBy = "idCuentaPadre")
    private List<ContabilidadCuenta> cuentasHijo;
    @JoinColumn(name = "id_cuenta_padre", referencedColumnName = "id_cuenta")
    @ManyToOne
    private ContabilidadCuenta idCuentaPadre;

    public ContabilidadCuenta() {
    }

    public ContabilidadCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public List<Factura> getFacturasDeudoras() {
        return facturasDeudoras;
    }

    public void setFacturasDeudoras(List<Factura> facturasDeudoras) {
        this.facturasDeudoras = facturasDeudoras;
    }

    public List<Factura> getFacturasAcreedoras() {
        return facturasAcreedoras;
    }

    public void setFacturasAcreedoras(List<Factura> facturasAcreedoras) {
        this.facturasAcreedoras = facturasAcreedoras;
    }

    public List<ContabilidadCuenta> getCuentasHijo() {
        return cuentasHijo;
    }

    public void setCuentasHijo(List<ContabilidadCuenta> cuentasHijo) {
        this.cuentasHijo = cuentasHijo;
    }

    public ContabilidadCuenta getIdCuentaPadre() {
        return idCuentaPadre;
    }

    public void setIdCuentaPadre(ContabilidadCuenta idCuentaPadre) {
        this.idCuentaPadre = idCuentaPadre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuenta != null ? idCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContabilidadCuenta)) {
            return false;
        }
        ContabilidadCuenta other = (ContabilidadCuenta) object;
        if ((this.idCuenta == null && other.idCuenta != null) || (this.idCuenta != null && !this.idCuenta.equals(other.idCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return numeroCuenta + " " + nombre;
    }

    public ContabilidadCuenta getCuentaEnlazada() {
        return cuentaEnlazada;
    }

    public void setCuentaEnlazada(ContabilidadCuenta cuentaEnlazada) {
        this.cuentaEnlazada = cuentaEnlazada;
    }

    public enum TipoCuentaContable {
        CREDITO("CREDITO"), DEBITO("DEBITO");

        private final String string;

        private TipoCuentaContable(String string) {
            this.string = string;
        }

        public final String getString() {
            return string;
        }
        
    }

    public List<Pago> getPagoList() {
        return pagoList;
    }

    public void setPagoList(List<Pago> pagoList) {
        this.pagoList = pagoList;
    }

    public Boolean getCierra() {
        return cierra;
    }

    public void setCierra(Boolean cierra) {
        this.cierra = cierra;
    }

}
