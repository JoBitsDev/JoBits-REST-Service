/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jobits.pos.persistence.pasarela;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * FirstDream
 * @author Jorge
 * 
 */
@Entity
@Table(name = "base_datos")
@NamedQueries({
    @NamedQuery(name = "BaseDatos.findAll", query = "SELECT b FROM BaseDatos b"),
    @NamedQuery(name = "BaseDatos.findByCuentaid", query = "SELECT b FROM BaseDatos b WHERE b.cuentaid = :cuentaid"),
    @NamedQuery(name = "BaseDatos.findByUrl", query = "SELECT b FROM BaseDatos b WHERE b.url = :url"),
    @NamedQuery(name = "BaseDatos.findByUsuario", query = "SELECT b FROM BaseDatos b WHERE b.usuario = :usuario"),
    @NamedQuery(name = "BaseDatos.findByContrasena", query = "SELECT b FROM BaseDatos b WHERE b.contrasena = :contrasena"),
    @NamedQuery(name = "BaseDatos.findByDriver", query = "SELECT b FROM BaseDatos b WHERE b.driver = :driver")})
public class BaseDatos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer cuentaid;
    @Size(max = 255)
    private String url;
    @Size(max = 30)
    private String usuario;
    @Size(max = 255)
    private String contrasena;
    @Size(max = 100)
    private String driver;
    @JoinColumn(name = "cuentaid", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Cuenta cuenta;

    public BaseDatos() {
    }

    public BaseDatos(Integer cuentaid) {
        this.cuentaid = cuentaid;
    }

    public Integer getCuentaid() {
        return cuentaid;
    }

    public void setCuentaid(Integer cuentaid) {
        this.cuentaid = cuentaid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cuentaid != null ? cuentaid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BaseDatos)) {
            return false;
        }
        BaseDatos other = (BaseDatos) object;
        if ((this.cuentaid == null && other.cuentaid != null) || (this.cuentaid != null && !this.cuentaid.equals(other.cuentaid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jobits.pos.persistence.pasarela.BaseDatos[ cuentaid=" + cuentaid + " ]";
    }

}
