/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jobits.pos.persistence.pasarela;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * FirstDream
 * @author Jorge
 * 
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Token.findAll", query = "SELECT t FROM Token t"),
    @NamedQuery(name = "Token.findByCuentaid", query = "SELECT t FROM Token t WHERE t.cuentaid = :cuentaid"),
    @NamedQuery(name = "Token.findByToken", query = "SELECT t FROM Token t WHERE t.token = :token"),
    @NamedQuery(name = "Token.findByExpira", query = "SELECT t FROM Token t WHERE t.expira = :expira")})
public class Token implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer cuentaid;
    @Size(max = 255)
    private String token;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expira;
    @JoinColumn(name = "cuentaid", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Cuenta cuenta;

    public Token() {
    }

    public Token(Integer cuentaid) {
        this.cuentaid = cuentaid;
    }

    public Integer getCuentaid() {
        return cuentaid;
    }

    public void setCuentaid(Integer cuentaid) {
        this.cuentaid = cuentaid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpira() {
        return expira;
    }

    public void setExpira(Date expira) {
        this.expira = expira;
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
        if (!(object instanceof Token)) {
            return false;
        }
        Token other = (Token) object;
        if ((this.cuentaid == null && other.cuentaid != null) || (this.cuentaid != null && !this.cuentaid.equals(other.cuentaid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jobits.pos.persistence.pasarela.Token[ cuentaid=" + cuentaid + " ]";
    }

}
