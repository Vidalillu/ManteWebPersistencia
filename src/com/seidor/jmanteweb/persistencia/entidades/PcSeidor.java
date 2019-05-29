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
import javax.persistence.CascadeType;
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
@Table(name = "PC_SEIDOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PcSeidor.findAll", query = "SELECT p FROM PcSeidor p")
    , @NamedQuery(name = "PcSeidor.findByPc", query = "SELECT p FROM PcSeidor p WHERE p.pc = :pc")
    , @NamedQuery(name = "PcSeidor.findByDescripcion", query = "SELECT p FROM PcSeidor p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "PcSeidor.findByIp", query = "SELECT p FROM PcSeidor p WHERE p.ip = :ip")
    , @NamedQuery(name = "PcSeidor.findByUsuariow", query = "SELECT p FROM PcSeidor p WHERE p.usuariow = :usuariow")
    , @NamedQuery(name = "PcSeidor.findByPasswordw", query = "SELECT p FROM PcSeidor p WHERE p.passwordw = :passwordw")
    , @NamedQuery(name = "PcSeidor.findByVersion", query = "SELECT p FROM PcSeidor p WHERE p.version = :version")})
public class PcSeidor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PC")
    private String pc;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "IP")
    private String ip;
    @Column(name = "USUARIOW")
    private String usuariow;
    @Column(name = "PASSWORDW")
    private String passwordw;
    @Column(name = "VERSION")
    private BigInteger version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pc")
    private List<ConexionPcsSeidor> conexionPcsSeidorList;

    public PcSeidor() {
    }

    public PcSeidor(String pc) {
        this.pc = pc;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsuariow() {
        return usuariow;
    }

    public void setUsuariow(String usuariow) {
        this.usuariow = usuariow;
    }

    public String getPasswordw() {
        return passwordw;
    }

    public void setPasswordw(String passwordw) {
        this.passwordw = passwordw;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    @XmlTransient
    public List<ConexionPcsSeidor> getConexionPcsSeidorList() {
        return conexionPcsSeidorList;
    }

    public void setConexionPcsSeidorList(List<ConexionPcsSeidor> conexionPcsSeidorList) {
        this.conexionPcsSeidorList = conexionPcsSeidorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pc != null ? pc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PcSeidor)) {
            return false;
        }
        PcSeidor other = (PcSeidor) object;
        if ((this.pc == null && other.pc != null) || (this.pc != null && !this.pc.equals(other.pc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.PcSeidor[ pc=" + pc + " ]";
    }
    
}
