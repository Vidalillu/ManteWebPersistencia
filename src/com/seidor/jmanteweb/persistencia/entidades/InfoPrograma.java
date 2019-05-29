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
@Table(name = "INFO_PROGRAMA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InfoPrograma.findAll", query = "SELECT i FROM InfoPrograma i")
    , @NamedQuery(name = "InfoPrograma.findByCliente", query = "SELECT i FROM InfoPrograma i WHERE i.infoProgramaPK.cliente = :cliente")
    , @NamedQuery(name = "InfoPrograma.findByPrograma", query = "SELECT i FROM InfoPrograma i WHERE i.infoProgramaPK.programa = :programa")
    , @NamedQuery(name = "InfoPrograma.findByVersionprog", query = "SELECT i FROM InfoPrograma i WHERE i.versionprog = :versionprog")
    , @NamedQuery(name = "InfoPrograma.findByStandard", query = "SELECT i FROM InfoPrograma i WHERE i.standard = :standard")
    , @NamedQuery(name = "InfoPrograma.findByObservaciones", query = "SELECT i FROM InfoPrograma i WHERE i.observaciones = :observaciones")
    , @NamedQuery(name = "InfoPrograma.findByVersion", query = "SELECT i FROM InfoPrograma i WHERE i.version = :version")})
public class InfoPrograma implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InfoProgramaPK infoProgramaPK;
    @Column(name = "VERSIONPROG")
    private String versionprog;
    @Basic(optional = false)
    @Column(name = "STANDARD")
    private BigInteger standard;
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Column(name = "VERSION")
    private BigInteger version;
    @JoinColumn(name = "CLIENTE", referencedColumnName = "CLIENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Clientes clientes;
    @JoinColumn(name = "PROGRAMA", referencedColumnName = "PROGRAMA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Programas programas;

    public InfoPrograma() {
    }

    public InfoPrograma(InfoProgramaPK infoProgramaPK) {
        this.infoProgramaPK = infoProgramaPK;
    }

    public InfoPrograma(InfoProgramaPK infoProgramaPK, BigInteger standard) {
        this.infoProgramaPK = infoProgramaPK;
        this.standard = standard;
    }

    public InfoPrograma(String cliente, String programa) {
        this.infoProgramaPK = new InfoProgramaPK(cliente, programa);
    }

    public InfoProgramaPK getInfoProgramaPK() {
        return infoProgramaPK;
    }

    public void setInfoProgramaPK(InfoProgramaPK infoProgramaPK) {
        this.infoProgramaPK = infoProgramaPK;
    }

    public String getVersionprog() {
        return versionprog;
    }

    public void setVersionprog(String versionprog) {
        this.versionprog = versionprog;
    }

    public BigInteger getStandard() {
        return standard;
    }

    public void setStandard(BigInteger standard) {
        this.standard = standard;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
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
        hash += (infoProgramaPK != null ? infoProgramaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InfoPrograma)) {
            return false;
        }
        InfoPrograma other = (InfoPrograma) object;
        if ((this.infoProgramaPK == null && other.infoProgramaPK != null) || (this.infoProgramaPK != null && !this.infoProgramaPK.equals(other.infoProgramaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.InfoPrograma[ infoProgramaPK=" + infoProgramaPK + " ]";
    }
    
}
