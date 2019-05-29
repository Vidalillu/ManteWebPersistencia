/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AMARTEL
 */
@Entity
@Table(name = "TIPO_BD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoBd.findAll", query = "SELECT t FROM TipoBd t")
    , @NamedQuery(name = "TipoBd.findByTipoBd", query = "SELECT t FROM TipoBd t WHERE t.tipoBd = :tipoBd")
    , @NamedQuery(name = "TipoBd.findByVersion", query = "SELECT t FROM TipoBd t WHERE t.version = :version")})
public class TipoBd implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TIPO_BD")
    private String tipoBd;
    @Column(name = "VERSION")
    private BigInteger version;
    @OneToMany(mappedBy = "tipoBd")
    private List<InfoBd> infoBdList;

    public TipoBd() {
    }

    public TipoBd(String tipoBd) {
        this.tipoBd = tipoBd;
    }

    public String getTipoBd() {
        return tipoBd;
    }

    public void setTipoBd(String tipoBd) {
        this.tipoBd = tipoBd;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    @XmlTransient
    public List<InfoBd> getInfoBdList() {
        return infoBdList;
    }

    public void setInfoBdList(List<InfoBd> infoBdList) {
        this.infoBdList = infoBdList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoBd != null ? tipoBd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoBd)) {
            return false;
        }
        TipoBd other = (TipoBd) object;
        if ((this.tipoBd == null && other.tipoBd != null) || (this.tipoBd != null && !this.tipoBd.equals(other.tipoBd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.TipoBd[ tipoBd=" + tipoBd + " ]";
    }
    
}
