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
@Table(name = "TIPO_VPN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoVpn.findAll", query = "SELECT t FROM TipoVpn t")
    , @NamedQuery(name = "TipoVpn.findByTipoVpn", query = "SELECT t FROM TipoVpn t WHERE t.tipoVpn = :tipoVpn")
    , @NamedQuery(name = "TipoVpn.findByVersion", query = "SELECT t FROM TipoVpn t WHERE t.version = :version")})
public class TipoVpn implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TIPO_VPN")
    private String tipoVpn;
    @Column(name = "VERSION")
    private BigInteger version;

    public TipoVpn() {
    }

    public TipoVpn(String tipoVpn) {
        this.tipoVpn = tipoVpn;
    }

    public String getTipoVpn() {
        return tipoVpn;
    }

    public void setTipoVpn(String tipoVpn) {
        this.tipoVpn = tipoVpn;
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
        hash += (tipoVpn != null ? tipoVpn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoVpn)) {
            return false;
        }
        TipoVpn other = (TipoVpn) object;
        if ((this.tipoVpn == null && other.tipoVpn != null) || (this.tipoVpn != null && !this.tipoVpn.equals(other.tipoVpn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.TipoVpn[ tipoVpn=" + tipoVpn + " ]";
    }
    
}
