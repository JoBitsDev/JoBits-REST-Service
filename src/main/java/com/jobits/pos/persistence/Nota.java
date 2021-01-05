/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.persistence;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
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
 *
 * JoBits
 *
 * @author Jorge
 *
 */
@Entity
@Table(name = "nota")
@NamedQueries({
    @NamedQuery(name = "Nota.findAll", query = "SELECT n FROM Nota n"),
    @NamedQuery(name = "Nota.findByDescripcion", query = "SELECT n FROM Nota n WHERE n.descripcion = :descripcion"),
    @NamedQuery(name = "Nota.findByProductovOrdenid", query = "SELECT n FROM Nota n WHERE n.productovOrdenid = :productovOrdenid")})
public class Nota implements Serializable {

    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "productov_ordenid")
    private Integer productovOrdenid;
    @JoinColumn(name = "productov_ordenid", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private ProductovOrden productovOrden;

    public Nota() {
    }

    public Nota(Integer productovOrdenid) {
        this.productovOrdenid = productovOrdenid;
    }


    public Integer getProductovOrdenid() {
        return productovOrdenid;
    }

    public void setProductovOrdenid(Integer productovOrdenid) {
        this.productovOrdenid = productovOrdenid;
    }

    public ProductovOrden getProductovOrden() {
        return productovOrden;
    }

    public void setProductovOrden(ProductovOrden productovOrden) {
        this.productovOrden = productovOrden;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productovOrdenid != null ? productovOrdenid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nota)) {
            return false;
        }
        Nota other = (Nota) object;
        if ((this.productovOrdenid == null && other.productovOrdenid != null) || (this.productovOrdenid != null && !this.productovOrdenid.equals(other.productovOrdenid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jobits.pos.domain.models.Nota[ productovOrdenid=" + productovOrdenid + " ]";
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
