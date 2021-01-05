/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * FirstDream
 *
 * @author Jorge
 *
 */
@Entity
@Table(name = "productov_orden")
@NamedQueries({
    @NamedQuery(name = "ProductovOrden.findAll", query = "SELECT p FROM ProductovOrden p"),
    @NamedQuery(name = "ProductovOrden.findByCantidad", query = "SELECT p FROM ProductovOrden p WHERE p.cantidad = :cantidad"),
    @NamedQuery(name = "ProductovOrden.findByEnviadosacocina", query = "SELECT p FROM ProductovOrden p WHERE p.enviadosacocina = :enviadosacocina"),
    @NamedQuery(name = "ProductovOrden.findByNumeroComensal", query = "SELECT p FROM ProductovOrden p WHERE p.numeroComensal = :numeroComensal"),
    @NamedQuery(name = "ProductovOrden.findByListoParaRecoger", query = "SELECT p FROM ProductovOrden p WHERE p.listoParaRecoger = :listoParaRecoger")})
public class ProductovOrden implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "precio_vendido")
    private float precioVendido;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombre_producto_vendido")
    private String nombreProductoVendido;
    @OneToMany(mappedBy = "agregadoA", orphanRemoval = true)
    private List<ProductovOrden> agregos;
    @JoinColumn(name = "agregado_a", referencedColumnName = "id")
    @ManyToOne
    private ProductovOrden agregadoA;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "enviadosacocina")
    private Float enviadosacocina;

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private float cantidad;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_producto_orden")
    @SequenceGenerator(name = "id_producto_orden", allocationSize = 1)
    private Integer id;
    @Column(name = "numero_comensal")
    private Integer numeroComensal;
    @Column(name = "listo_para_recoger")
    private Boolean listoParaRecoger;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productovOrden")
    private List<NotificacionEnvioCocina> notificacionEnvioCocinaList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "productovOrden")
    @JsonIgnore //TODO: los json van en otros modelos no en los de base de datos
    private Nota nota;
    @JoinColumn(name = "ordencod_orden", referencedColumnName = "cod_orden")
    @ManyToOne(optional = false)
    private Orden orden;
    @JoinColumn(name = "producto_ventap_cod", referencedColumnName = "p_cod")
    @ManyToOne(optional = false)
    private ProductoVenta productoVenta;

    public ProductovOrden() {
    }

    public ProductovOrden(Integer id) {
        this.id = id;
    }

    public ProductovOrden(Integer id, float cantidad, float precioVendido, String nombreProductoVendido) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioVendido = precioVendido;
        this.nombreProductoVendido = nombreProductoVendido;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecioVendido() {
        return precioVendido;
    }

    public void setPrecioVendido(float precioVendido) {
        this.precioVendido = precioVendido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreProductoVendido() {
        return nombreProductoVendido;
    }

    public void setNombreProductoVendido(String nombreProductoVendido) {
        this.nombreProductoVendido = nombreProductoVendido;
    }

    public Integer getNumeroComensal() {
        return numeroComensal;
    }

    public void setNumeroComensal(Integer numeroComensal) {
        this.numeroComensal = numeroComensal;
    }

    public Boolean getListoParaRecoger() {
        return listoParaRecoger;
    }

    public void setListoParaRecoger(Boolean listoParaRecoger) {
        this.listoParaRecoger = listoParaRecoger;
    }

    public List<NotificacionEnvioCocina> getNotificacionEnvioCocinaList() {
        return notificacionEnvioCocinaList;
    }

    public void setNotificacionEnvioCocinaList(List<NotificacionEnvioCocina> notificacionEnvioCocinaList) {
        this.notificacionEnvioCocinaList = notificacionEnvioCocinaList;
    }

    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public ProductoVenta getProductoVenta() {
        return productoVenta;
    }

    public void setProductoVenta(ProductoVenta productoVenta) {
        this.productoVenta = productoVenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductovOrden)) {
            return false;
        }
        ProductovOrden other = (ProductovOrden) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getOrden() + "_" + getProductoVenta();
    }

    public Float getEnviadosacocina() {
        return enviadosacocina;
    }

    public void setEnviadosacocina(Float enviadosacocina) {
        this.enviadosacocina = enviadosacocina;
    }

    public List<ProductovOrden> getAgregos() {
        return agregos;
    }

    public void setAgregos(List<ProductovOrden> agregos) {
        this.agregos = agregos;
    }

    public ProductovOrden getAgregadoA() {
        return agregadoA;
    }

    public void setAgregadoA(ProductovOrden agregadoA) {
        this.agregadoA = agregadoA;
    }
}
