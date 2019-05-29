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
@Table(name = "CONEXION_PC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConexionPc.findAll", query = "SELECT c FROM ConexionPc c")
    , @NamedQuery(name = "ConexionPc.findByPc", query = "SELECT c FROM ConexionPc c WHERE c.pc = :pc")
    , @NamedQuery(name = "ConexionPc.findByIp", query = "SELECT c FROM ConexionPc c WHERE c.ip = :ip")
    , @NamedQuery(name = "ConexionPc.findByPuerto", query = "SELECT c FROM ConexionPc c WHERE c.puerto = :puerto")
    , @NamedQuery(name = "ConexionPc.findByPuerto2", query = "SELECT c FROM ConexionPc c WHERE c.puerto2 = :puerto2")
    , @NamedQuery(name = "ConexionPc.findByUsuariow", query = "SELECT c FROM ConexionPc c WHERE c.usuariow = :usuariow")
    , @NamedQuery(name = "ConexionPc.findByPasswordw", query = "SELECT c FROM ConexionPc c WHERE c.passwordw = :passwordw")
    , @NamedQuery(name = "ConexionPc.findByUsuarioc", query = "SELECT c FROM ConexionPc c WHERE c.usuarioc = :usuarioc")
    , @NamedQuery(name = "ConexionPc.findByPasswordc", query = "SELECT c FROM ConexionPc c WHERE c.passwordc = :passwordc")
    , @NamedQuery(name = "ConexionPc.findByObservaciones", query = "SELECT c FROM ConexionPc c WHERE c.observaciones = :observaciones")
    , @NamedQuery(name = "ConexionPc.findByDescripcion", query = "SELECT c FROM ConexionPc c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "ConexionPc.findById", query = "SELECT c FROM ConexionPc c WHERE c.id = :id")
    , @NamedQuery(name = "ConexionPc.findByPathconexion", query = "SELECT c FROM ConexionPc c WHERE c.pathconexion = :pathconexion")
    , @NamedQuery(name = "ConexionPc.findByVersion", query = "SELECT c FROM ConexionPc c WHERE c.version = :version")
    , @NamedQuery(name = "ConexionPc.findByPrograma", query = "SELECT c FROM ConexionPc c WHERE c.programa = :programa")
    , @NamedQuery(name = "ConexionPc.findByPrioridad", query = "SELECT c FROM ConexionPc c WHERE c.prioridad = :prioridad")})
public class ConexionPc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "PC")
    private String pc;
    @Column(name = "IP")
    private String ip;
    @Basic(optional = false)
    @Column(name = "PUERTO")
    private BigInteger puerto;
    @Basic(optional = false)
    @Column(name = "PUERTO2")
    private BigInteger puerto2;
    @Column(name = "USUARIOW")
    private String usuariow;
    @Column(name = "PASSWORDW")
    private String passwordw;
    @Column(name = "USUARIOC")
    private String usuarioc;
    @Column(name = "PASSWORDC")
    private String passwordc;
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "PATHCONEXION")
    private String pathconexion;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @Column(name = "PROGRAMA")
    private String programa;
    @Basic(optional = false)
    @Column(name = "PRIORIDAD")
    private short prioridad;
    @JoinColumn(name = "ID_CONEX_REMOTA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ConexionRemota idConexRemota;

    public ConexionPc() {
    }

    public ConexionPc(BigDecimal id) {
        this.id = id;
    }

    public ConexionPc(BigDecimal id, String pc, BigInteger puerto, BigInteger puerto2, BigInteger version, short prioridad) {
        this.id = id;
        this.pc = pc;
        this.puerto = puerto;
        this.puerto2 = puerto2;
        this.version = version;
        this.prioridad = prioridad;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public BigInteger getPuerto() {
        return puerto;
    }

    public void setPuerto(BigInteger puerto) {
        this.puerto = puerto;
    }

    public BigInteger getPuerto2() {
        return puerto2;
    }

    public void setPuerto2(BigInteger puerto2) {
        this.puerto2 = puerto2;
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

    public String getUsuarioc() {
        return usuarioc;
    }

    public void setUsuarioc(String usuarioc) {
        this.usuarioc = usuarioc;
    }

    public String getPasswordc() {
        return passwordc;
    }

    public void setPasswordc(String passwordc) {
        this.passwordc = passwordc;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getPathconexion() {
        return pathconexion;
    }

    public void setPathconexion(String pathconexion) {
        this.pathconexion = pathconexion;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public short getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(short prioridad) {
        this.prioridad = prioridad;
    }

    public ConexionRemota getIdConexRemota() {
        return idConexRemota;
    }

    public void setIdConexRemota(ConexionRemota idConexRemota) {
        this.idConexRemota = idConexRemota;
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
        if (!(object instanceof ConexionPc)) {
            return false;
        }
        ConexionPc other = (ConexionPc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.ConexionPc[ id=" + id + " ]";
    }
    
}
