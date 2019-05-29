/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AMARTEL
 */
@Entity
@Table(name = "TIPOS_CLIENTE_OLD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposClienteOld.findAll", query = "SELECT t FROM TiposClienteOld t")
    , @NamedQuery(name = "TiposClienteOld.findByTipocliente", query = "SELECT t FROM TiposClienteOld t WHERE t.tipocliente = :tipocliente")
    , @NamedQuery(name = "TiposClienteOld.findByVersion", query = "SELECT t FROM TiposClienteOld t WHERE t.version = :version")})
public class TiposClienteOld implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TIPOCLIENTE")
    private String tipocliente;
    @Column(name = "VERSION")
    private BigInteger version;

    public TiposClienteOld() {
    }

    public TiposClienteOld(String tipocliente) {
        this.tipocliente = tipocliente;
    }

    public String getTipocliente() {
        return tipocliente;
    }

    public void setTipocliente(String tipocliente) {
        this.tipocliente = tipocliente;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipocliente != null ? tipocliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TiposClienteOld)) {
            return false;
        }
        TiposClienteOld other = (TiposClienteOld) object;
        if ((this.tipocliente == null && other.tipocliente != null) || (this.tipocliente != null && !this.tipocliente.equals(other.tipocliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.TiposClienteOld[ tipocliente=" + tipocliente + " ]";
    }
    
}
