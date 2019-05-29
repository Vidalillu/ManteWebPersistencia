/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AMARTEL
 */
@Entity
@Table(name = "INFO_MODI_PROGRAMA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InfoModiPrograma.findAll", query = "SELECT i FROM InfoModiPrograma i")
    , @NamedQuery(name = "InfoModiPrograma.findByIdmodi", query = "SELECT i FROM InfoModiPrograma i WHERE i.infoModiProgramaPK.idmodi = :idmodi")
    , @NamedQuery(name = "InfoModiPrograma.findByPrograma", query = "SELECT i FROM InfoModiPrograma i WHERE i.infoModiProgramaPK.programa = :programa")
    , @NamedQuery(name = "InfoModiPrograma.findByVersion", query = "SELECT i FROM InfoModiPrograma i WHERE i.version = :version")})
public class InfoModiPrograma implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InfoModiProgramaPK infoModiProgramaPK;
    @Column(name = "VERSION")
    private BigInteger version;
    @JoinColumn(name = "IDMODI", referencedColumnName = "IDMODI", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Modis modis;
    @JoinColumn(name = "PROGRAMA", referencedColumnName = "PROGRAMA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Programas programas;

    public InfoModiPrograma() {
    }

    public InfoModiPrograma(InfoModiProgramaPK infoModiProgramaPK) {
        this.infoModiProgramaPK = infoModiProgramaPK;
    }

    public InfoModiPrograma(BigInteger idmodi, String programa) {
        this.infoModiProgramaPK = new InfoModiProgramaPK(idmodi, programa);
    }

    public InfoModiProgramaPK getInfoModiProgramaPK() {
        return infoModiProgramaPK;
    }

    public void setInfoModiProgramaPK(InfoModiProgramaPK infoModiProgramaPK) {
        this.infoModiProgramaPK = infoModiProgramaPK;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public Modis getModis() {
        return modis;
    }

    public void setModis(Modis modis) {
        this.modis = modis;
    }

    public Programas getProgramas() {
        return programas;
    }

    public void setProgramas(Programas programas) {
        this.programas = programas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (infoModiProgramaPK != null ? infoModiProgramaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InfoModiPrograma)) {
            return false;
        }
        InfoModiPrograma other = (InfoModiPrograma) object;
        if ((this.infoModiProgramaPK == null && other.infoModiProgramaPK != null) || (this.infoModiProgramaPK != null && !this.infoModiProgramaPK.equals(other.infoModiProgramaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.InfoModiPrograma[ infoModiProgramaPK=" + infoModiProgramaPK + " ]";
    }
    
}
