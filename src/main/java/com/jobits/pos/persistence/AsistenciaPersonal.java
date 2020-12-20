/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.persistence;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * FirstDream
 *
 * @author Jorge
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "asistenciaPersonalPK" ,scope = AsistenciaPersonal.class)
@Entity
@Table(name = "asistencia_personal")
@NamedQueries({
    @NamedQuery(name = "AsistenciaPersonal.findAll", query = "SELECT a FROM AsistenciaPersonal a"),
    @NamedQuery(name = "AsistenciaPersonal.findByVentaId", query = "SELECT a FROM AsistenciaPersonal a WHERE a.asistenciaPersonalPK.ventaid = :ventaId"),
    @NamedQuery(name = "AsistenciaPersonal.findByPersonalusuario", query = "SELECT a FROM AsistenciaPersonal a WHERE a.asistenciaPersonalPK.personalusuario = :personalusuario"),
    @NamedQuery(name = "AsistenciaPersonal.findByPago", query = "SELECT a FROM AsistenciaPersonal a WHERE a.pago = :pago")})
public class AsistenciaPersonal implements Serializable, Comparable<AsistenciaPersonal> {

    @JoinColumn(name = "ventaid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Venta venta;

    @Column(name = "a_mayores")
    private Float aMayores;
    @Column(name = "propina")
    private Float propina;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AsistenciaPersonalPK asistenciaPersonalPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pago")
    private Float pago;
    @JoinColumn(name = "personalusuario", referencedColumnName = "usuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Personal personal;

    public AsistenciaPersonal() {
    }

    public AsistenciaPersonal(AsistenciaPersonalPK asistenciaPersonalPK) {
        this.asistenciaPersonalPK = asistenciaPersonalPK;
    }

    public AsistenciaPersonal(Integer ventafecha, String personalusuario) {
        this.asistenciaPersonalPK = new AsistenciaPersonalPK(ventafecha, personalusuario);
    }

    public AsistenciaPersonalPK getAsistenciaPersonalPK() {
        return asistenciaPersonalPK;
    }

    public void setAsistenciaPersonalPK(AsistenciaPersonalPK asistenciaPersonalPK) {
        this.asistenciaPersonalPK = asistenciaPersonalPK;
    }

    public Float getPago() {
        return pago;
    }

    public void setPago(Float pago) {
        this.pago = pago;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (asistenciaPersonalPK != null ? asistenciaPersonalPK.hashCode() : 0);
        return hash;
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
        final AsistenciaPersonal other = (AsistenciaPersonal) obj;
        if (!Objects.equals(this.venta, other.venta)) {
            return false;
        }
        if (!Objects.equals(this.asistenciaPersonalPK, other.asistenciaPersonalPK)) {
            return false;
        }
        if (!Objects.equals(this.personal, other.personal)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "restManager.persistencia.AsistenciaPersonal[ asistenciaPersonalPK=" + asistenciaPersonalPK + " ]";
    }

    @Override
    public int compareTo(AsistenciaPersonal o) {
        return o.getAsistenciaPersonalPK().getVentaid().compareTo(getAsistenciaPersonalPK().getVentaid());
    }

    public Float getAMayores() {
        return aMayores;
    }

    public void setAMayores(Float aMayores) {
        this.aMayores = aMayores;
    }

    public Float getPropina() {
        return propina;
    }

    public void setPropina(Float propina) {
        this.propina = propina;
    }
}
