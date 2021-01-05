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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * FirstDream
 * @author Jorge
 * 
 */
@Entity
@Table(name = "proveedor")
@NamedQueries({
    @NamedQuery(name = "Proveedor.findAll", query = "SELECT p FROM Proveedor p"),
    @NamedQuery(name = "Proveedor.findByNoProveedor", query = "SELECT p FROM Proveedor p WHERE p.noProveedor = :noProveedor"),
    @NamedQuery(name = "Proveedor.findByCodigo", query = "SELECT p FROM Proveedor p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Proveedor.findByNombre", query = "SELECT p FROM Proveedor p WHERE p.nombre = :nombre")})
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "no_proveedor")
    private Integer noProveedor;
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proveedor")
    private List<Operacion> operacionList;

    public Proveedor() {
    }

    public Proveedor(Integer noProveedor) {
        this.noProveedor = noProveedor;
    }

    public Proveedor(Integer noProveedor, String nombre) {
        this.noProveedor = noProveedor;
        this.nombre = nombre;
    }

    public Integer getNoProveedor() {
        return noProveedor;
    }

    public void setNoProveedor(Integer noProveedor) {
        this.noProveedor = noProveedor;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Operacion> getOperacionList() {
        return operacionList;
    }

    public void setOperacionList(List<Operacion> operacionList) {
        this.operacionList = operacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noProveedor != null ? noProveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedor)) {
            return false;
        }
        Proveedor other = (Proveedor) object;
        if ((this.noProveedor == null && other.noProveedor != null) || (this.noProveedor != null && !this.noProveedor.equals(other.noProveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restManager.persistencia.Proveedor[ noProveedor=" + noProveedor + " ]";
    }

}
