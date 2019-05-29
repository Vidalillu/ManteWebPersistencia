/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "CONEXION_PCS_SEIDOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConexionPcsSeidor.findAll", query = "SELECT c FROM ConexionPcsSeidor c")
    , @NamedQuery(name = "ConexionPcsSeidor.findByVersion", query = "SELECT c FROM ConexionPcsSeidor c WHERE c.version = :version")
    , @NamedQuery(name = "ConexionPcsSeidor.findById", query = "SELECT c FROM ConexionPcsSeidor c WHERE c.id = :id")})
public class ConexionPcsSeidor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @JoinColumn(name = "ID_CONEXION", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ConexionRemota idConexion;
    @JoinColumn(name = "PC", referencedColumnName = "PC")
    @ManyToOne(optional = false)
    private PcSeidor pc;

    public ConexionPcsSeidor() {
    }

    public ConexionPcsSeidor(BigDecimal id) {
        this.id = id;
    }

    public ConexionPcsSeidor(BigDecimal id, BigInteger version) {
        this.id = id;
        this.version = version;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public ConexionRemota getIdConexion() {
        return idConexion;
    }

    public void setIdConexion(ConexionRemota idConexion) {
        this.idConexion = idConexion;
    }

    public PcSeidor getPc() {
        return pc;
    }

    public void setPc(PcSeidor pc) {
        this.pc = pc;
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
        if (!(object instanceof ConexionPcsSeidor)) {
            return false;
        }
        ConexionPcsSeidor other = (ConexionPcsSeidor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.ConexionPcsSeidor[ id=" + id + " ]";
    }
    
}
