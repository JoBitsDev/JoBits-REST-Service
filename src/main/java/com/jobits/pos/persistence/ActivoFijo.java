/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.persistence;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "activo_fijo")
@NamedQueries({
    @NamedQuery(name = "ActivoFijo.findAll", query = "SELECT a FROM ActivoFijo a"),
    @NamedQuery(name = "ActivoFijo.findByNumeroActivo", query = "SELECT a FROM ActivoFijo a WHERE a.numeroActivo = :numeroActivo"),
    @NamedQuery(name = "ActivoFijo.findByDescripcion", query = "SELECT a FROM ActivoFijo a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "ActivoFijo.findByTipoActivo", query = "SELECT a FROM ActivoFijo a WHERE a.tipoActivo = :tipoActivo"),
    @NamedQuery(name = "ActivoFijo.findByEstadoActivo", query = "SELECT a FROM ActivoFijo a WHERE a.estadoActivo = :estadoActivo"),
    @NamedQuery(name = "ActivoFijo.findByGrupo", query = "SELECT a FROM ActivoFijo a WHERE a.grupo = :grupo"),
    @NamedQuery(name = "ActivoFijo.findByFechaAlta", query = "SELECT a FROM ActivoFijo a WHERE a.fechaAlta = :fechaAlta"),
    @NamedQuery(name = "ActivoFijo.findByValorInicial", query = "SELECT a FROM ActivoFijo a WHERE a.valorInicial = :valorInicial"),
    @NamedQuery(name = "ActivoFijo.findByValorResidual", query = "SELECT a FROM ActivoFijo a WHERE a.valorResidual = :valorResidual"),
    @NamedQuery(name = "ActivoFijo.findByVidaUtil", query = "SELECT a FROM ActivoFijo a WHERE a.vidaUtil = :vidaUtil"),
    @NamedQuery(name = "ActivoFijo.findByDepreciacionAcumulada", query = "SELECT a FROM ActivoFijo a WHERE a.depreciacionAcumulada = :depreciacionAcumulada"),
    @NamedQuery(name = "ActivoFijo.findByUltimaDepreciacion", query = "SELECT a FROM ActivoFijo a WHERE a.ultimaDepreciacion = :ultimaDepreciacion")})
public class ActivoFijo implements Serializable {

    @JoinColumn(name = "ubicacion", referencedColumnName = "id_ubicacion")
    @ManyToOne(optional = false)
    private Ubicacion ubicacion;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numero_activo")
    private Integer numeroActivo;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "tipo_activo")
    private String tipoActivo;
    @Column(name = "estado_activo")
    private String estadoActivo;
    @Column(name = "grupo")
    private String grupo;
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_inicial")
    private Float valorInicial;
    @Column(name = "valor_residual")
    private Float valorResidual;
    @Column(name = "vida_util")
    private Float vidaUtil;
    @Column(name = "depreciacion_acumulada")
    private Float depreciacionAcumulada;
    @Column(name = "ultima_depreciacion")
    @Temporal(TemporalType.DATE)
    private Date ultimaDepreciacion;

    public ActivoFijo() {
    }

    public ActivoFijo(Integer numeroActivo) {
        this.numeroActivo = numeroActivo;
    }

    public Integer getNumeroActivo() {
        return numeroActivo;
    }

    public void setNumeroActivo(Integer numeroActivo) {
        this.numeroActivo = numeroActivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoActivo() {
        return tipoActivo;
    }

    public void setTipoActivo(String tipoActivo) {
        this.tipoActivo = tipoActivo;
    }

    public String getEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(String estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Float getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(Float valorInicial) {
        this.valorInicial = valorInicial;
    }

    public Float getValorResidual() {
        return valorResidual;
    }

    public void setValorResidual(Float valorResidual) {
        this.valorResidual = valorResidual;
    }

    public Float getVidaUtil() {
        return vidaUtil;
    }

    public void setVidaUtil(Float vidaUtil) {
        this.vidaUtil = vidaUtil;
    }

    public Float getDepreciacionAcumulada() {
        return depreciacionAcumulada;
    }

    public void setDepreciacionAcumulada(Float depreciacionAcumulada) {
        this.depreciacionAcumulada = depreciacionAcumulada;
    }

    public Date getUltimaDepreciacion() {
        return ultimaDepreciacion;
    }

    public void setUltimaDepreciacion(Date ultimaDepreciacion) {
        this.ultimaDepreciacion = ultimaDepreciacion;
    }

    public Float getValorActual() {
        return utils.setDosLugaresDecimalesFloat(valorInicial - depreciacionAcumulada);
    }

    public Float getDepreciacion() {
        return utils.setDosLugaresDecimalesFloat((getValorInicial() - getValorResidual()) / getVidaUtil());
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroActivo != null ? numeroActivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActivoFijo)) {
            return false;
        }
        ActivoFijo other = (ActivoFijo) object;
        if ((this.numeroActivo == null && other.numeroActivo != null) || (this.numeroActivo != null && !this.numeroActivo.equals(other.numeroActivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restManager.persistencia.ActivoFijo[ numeroActivo=" + numeroActivo + " ]";
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

}
