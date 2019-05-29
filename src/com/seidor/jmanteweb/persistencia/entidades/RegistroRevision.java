/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AMARTEL
 */
@Entity
@Table(name = "REGISTRO_REVISION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegistroRevision.findAll", query = "SELECT r FROM RegistroRevision r")
    , @NamedQuery(name = "RegistroRevision.findById", query = "SELECT r FROM RegistroRevision r WHERE r.id = :id")
    , @NamedQuery(name = "RegistroRevision.findByFechaCreacion", query = "SELECT r FROM RegistroRevision r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RegistroRevision.findByFechaRevCodigo", query = "SELECT r FROM RegistroRevision r WHERE r.fechaRevCodigo = :fechaRevCodigo")
    , @NamedQuery(name = "RegistroRevision.findByFechaModCodigo", query = "SELECT r FROM RegistroRevision r WHERE r.fechaModCodigo = :fechaModCodigo")
    , @NamedQuery(name = "RegistroRevision.findByRevFuentes", query = "SELECT r FROM RegistroRevision r WHERE r.revFuentes = :revFuentes")
    , @NamedQuery(name = "RegistroRevision.findByRevFuentesObs", query = "SELECT r FROM RegistroRevision r WHERE r.revFuentesObs = :revFuentesObs")
    , @NamedQuery(name = "RegistroRevision.findByRevBackup", query = "SELECT r FROM RegistroRevision r WHERE r.revBackup = :revBackup")
    , @NamedQuery(name = "RegistroRevision.findByRevBackupObs", query = "SELECT r FROM RegistroRevision r WHERE r.revBackupObs = :revBackupObs")
    , @NamedQuery(name = "RegistroRevision.findByRevBackupExt", query = "SELECT r FROM RegistroRevision r WHERE r.revBackupExt = :revBackupExt")
    , @NamedQuery(name = "RegistroRevision.findByRevBackupExtObs", query = "SELECT r FROM RegistroRevision r WHERE r.revBackupExtObs = :revBackupExtObs")
    , @NamedQuery(name = "RegistroRevision.findByRevBd", query = "SELECT r FROM RegistroRevision r WHERE r.revBd = :revBd")
    , @NamedQuery(name = "RegistroRevision.findByRevBdObs", query = "SELECT r FROM RegistroRevision r WHERE r.revBdObs = :revBdObs")
    , @NamedQuery(name = "RegistroRevision.findByRevDisco", query = "SELECT r FROM RegistroRevision r WHERE r.revDisco = :revDisco")
    , @NamedQuery(name = "RegistroRevision.findByRevDiscoObs", query = "SELECT r FROM RegistroRevision r WHERE r.revDiscoObs = :revDiscoObs")
    , @NamedQuery(name = "RegistroRevision.findByRevLogs", query = "SELECT r FROM RegistroRevision r WHERE r.revLogs = :revLogs")
    , @NamedQuery(name = "RegistroRevision.findByRevLogsObs", query = "SELECT r FROM RegistroRevision r WHERE r.revLogsObs = :revLogsObs")
    , @NamedQuery(name = "RegistroRevision.findByFechaRevision", query = "SELECT r FROM RegistroRevision r WHERE r.fechaRevision = :fechaRevision")
    , @NamedQuery(name = "RegistroRevision.findByConIncidencias", query = "SELECT r FROM RegistroRevision r WHERE r.conIncidencias = :conIncidencias")
    , @NamedQuery(name = "RegistroRevision.findByCerrada", query = "SELECT r FROM RegistroRevision r WHERE r.cerrada = :cerrada")
    , @NamedQuery(name = "RegistroRevision.findByVersion", query = "SELECT r FROM RegistroRevision r WHERE r.version = :version")
    , @NamedQuery(name = "RegistroRevision.findByRevBorrado", query = "SELECT r FROM RegistroRevision r WHERE r.revBorrado = :revBorrado")
    , @NamedQuery(name = "RegistroRevision.findByRevBorradoObs", query = "SELECT r FROM RegistroRevision r WHERE r.revBorradoObs = :revBorradoObs")})
public class RegistroRevision implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "FECHA_REV_CODIGO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRevCodigo;
    @Column(name = "FECHA_MOD_CODIGO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModCodigo;
    @Column(name = "REV_FUENTES")
    private Short revFuentes;
    @Column(name = "REV_FUENTES_OBS")
    private String revFuentesObs;
    @Column(name = "REV_BACKUP")
    private Short revBackup;
    @Column(name = "REV_BACKUP_OBS")
    private String revBackupObs;
    @Column(name = "REV_BACKUP_EXT")
    private Short revBackupExt;
    @Column(name = "REV_BACKUP_EXT_OBS")
    private String revBackupExtObs;
    @Column(name = "REV_BD")
    private Short revBd;
    @Column(name = "REV_BD_OBS")
    private String revBdObs;
    @Column(name = "REV_DISCO")
    private Short revDisco;
    @Column(name = "REV_DISCO_OBS")
    private String revDiscoObs;
    @Column(name = "REV_LOGS")
    private Short revLogs;
    @Column(name = "REV_LOGS_OBS")
    private String revLogsObs;
    @Column(name = "FECHA_REVISION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRevision;
    @Column(name = "CON_INCIDENCIAS")
    private Short conIncidencias;
    @Lob
    @Column(name = "CONSIDERACIONES")
    private String consideraciones;
    @Column(name = "CERRADA")
    private Short cerrada;
    @Column(name = "VERSION")
    private BigInteger version;
    @Lob
    @Column(name = "INCIDENCIAS")
    private String incidencias;
    @Column(name = "REV_BORRADO")
    private Short revBorrado;
    @Column(name = "REV_BORRADO_OBS")
    private String revBorradoObs;
    @JoinColumn(name = "CLIENTE", referencedColumnName = "CLIENTE")
    @ManyToOne(optional = false)
    private Clientes cliente;
    @JoinColumn(name = "USUARIO", referencedColumnName = "USUARIO")
    @ManyToOne
    private Usuario usuario;

    public RegistroRevision() {
    }

    public RegistroRevision(BigDecimal id) {
        this.id = id;
    }

    public RegistroRevision(BigDecimal id, Date fechaCreacion) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaRevCodigo() {
        return fechaRevCodigo;
    }

    public void setFechaRevCodigo(Date fechaRevCodigo) {
        this.fechaRevCodigo = fechaRevCodigo;
    }

    public Date getFechaModCodigo() {
        return fechaModCodigo;
    }

    public void setFechaModCodigo(Date fechaModCodigo) {
        this.fechaModCodigo = fechaModCodigo;
    }

    public Short getRevFuentes() {
        return revFuentes;
    }

    public void setRevFuentes(Short revFuentes) {
        this.revFuentes = revFuentes;
    }

    public String getRevFuentesObs() {
        return revFuentesObs;
    }

    public void setRevFuentesObs(String revFuentesObs) {
        this.revFuentesObs = revFuentesObs;
    }

    public Short getRevBackup() {
        return revBackup;
    }

    public void setRevBackup(Short revBackup) {
        this.revBackup = revBackup;
    }

    public String getRevBackupObs() {
        return revBackupObs;
    }

    public void setRevBackupObs(String revBackupObs) {
        this.revBackupObs = revBackupObs;
    }

    public Short getRevBackupExt() {
        return revBackupExt;
    }

    public void setRevBackupExt(Short revBackupExt) {
        this.revBackupExt = revBackupExt;
    }

    public String getRevBackupExtObs() {
        return revBackupExtObs;
    }

    public void setRevBackupExtObs(String revBackupExtObs) {
        this.revBackupExtObs = revBackupExtObs;
    }

    public Short getRevBd() {
        return revBd;
    }

    public void setRevBd(Short revBd) {
        this.revBd = revBd;
    }

    public String getRevBdObs() {
        return revBdObs;
    }

    public void setRevBdObs(String revBdObs) {
        this.revBdObs = revBdObs;
    }

    public Short getRevDisco() {
        return revDisco;
    }

    public void setRevDisco(Short revDisco) {
        this.revDisco = revDisco;
    }

    public String getRevDiscoObs() {
        return revDiscoObs;
    }

    public void setRevDiscoObs(String revDiscoObs) {
        this.revDiscoObs = revDiscoObs;
    }

    public Short getRevLogs() {
        return revLogs;
    }

    public void setRevLogs(Short revLogs) {
        this.revLogs = revLogs;
    }

    public String getRevLogsObs() {
        return revLogsObs;
    }

    public void setRevLogsObs(String revLogsObs) {
        this.revLogsObs = revLogsObs;
    }

    public Date getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(Date fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public Short getConIncidencias() {
        return conIncidencias;
    }

    public void setConIncidencias(Short conIncidencias) {
        this.conIncidencias = conIncidencias;
    }

    public String getConsideraciones() {
        return consideraciones;
    }

    public void setConsideraciones(String consideraciones) {
        this.consideraciones = consideraciones;
    }

    public Short getCerrada() {
        return cerrada;
    }

    public void setCerrada(Short cerrada) {
        this.cerrada = cerrada;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public String getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(String incidencias) {
        this.incidencias = incidencias;
    }

    public Short getRevBorrado() {
        return revBorrado;
    }

    public void setRevBorrado(Short revBorrado) {
        this.revBorrado = revBorrado;
    }

    public String getRevBorradoObs() {
        return revBorradoObs;
    }

    public void setRevBorradoObs(String revBorradoObs) {
        this.revBorradoObs = revBorradoObs;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        if (!(object instanceof RegistroRevision)) {
            return false;
        }
        RegistroRevision other = (RegistroRevision) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.RegistroRevision[ id=" + id + " ]";
    }
    
}
