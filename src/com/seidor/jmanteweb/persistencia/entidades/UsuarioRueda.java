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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AMARTEL
 */
@Entity
@Table(name = "USUARIO_RUEDA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioRueda.findAll", query = "SELECT u FROM UsuarioRueda u")
    , @NamedQuery(name = "UsuarioRueda.findByUsuariomante", query = "SELECT u FROM UsuarioRueda u WHERE u.usuariomante = :usuariomante")
    , @NamedQuery(name = "UsuarioRueda.findByTurno", query = "SELECT u FROM UsuarioRueda u WHERE u.turno = :turno")
    , @NamedQuery(name = "UsuarioRueda.findByTurnocorto", query = "SELECT u FROM UsuarioRueda u WHERE u.turnocorto = :turnocorto")
    , @NamedQuery(name = "UsuarioRueda.findBySoloviernes", query = "SELECT u FROM UsuarioRueda u WHERE u.soloviernes = :soloviernes")
    , @NamedQuery(name = "UsuarioRueda.findByVersion", query = "SELECT u FROM UsuarioRueda u WHERE u.version = :version")
    , @NamedQuery(name = "UsuarioRueda.findByOrden", query = "SELECT u FROM UsuarioRueda u WHERE u.orden = :orden")})
public class UsuarioRueda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "USUARIOMANTE")
    private String usuariomante;
    @Basic(optional = false)
    @Column(name = "TURNO")
    private short turno;
    @Basic(optional = false)
    @Column(name = "TURNOCORTO")
    private short turnocorto;
    @Basic(optional = false)
    @Column(name = "SOLOVIERNES")
    private short soloviernes;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @Basic(optional = false)
    @Column(name = "ORDEN")
    private long orden;
    @JoinColumn(name = "USUARIOMANTE", referencedColumnName = "USUARIO", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "PAREJA", referencedColumnName = "USUARIO")
    @ManyToOne
    private Usuario pareja;

    public UsuarioRueda() {
    }

    public UsuarioRueda(String usuariomante) {
        this.usuariomante = usuariomante;
    }

    public UsuarioRueda(String usuariomante, short turno, short turnocorto, short soloviernes, BigInteger version, long orden) {
        this.usuariomante = usuariomante;
        this.turno = turno;
        this.turnocorto = turnocorto;
        this.soloviernes = soloviernes;
        this.version = version;
        this.orden = orden;
    }

    public String getUsuariomante() {
        return usuariomante;
    }

    public void setUsuariomante(String usuariomante) {
        this.usuariomante = usuariomante;
    }

    public short getTurno() {
        return turno;
    }

    public void setTurno(short turno) {
        this.turno = turno;
    }

    public short getTurnocorto() {
        return turnocorto;
    }

    public void setTurnocorto(short turnocorto) {
        this.turnocorto = turnocorto;
    }

    public short getSoloviernes() {
        return soloviernes;
    }

    public void setSoloviernes(short soloviernes) {
        this.soloviernes = soloviernes;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public long getOrden() {
        return orden;
    }

    public void setOrden(long orden) {
        this.orden = orden;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getPareja() {
        return pareja;
    }

    public void setPareja(Usuario pareja) {
        this.pareja = pareja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuariomante != null ? usuariomante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioRueda)) {
            return false;
        }
        UsuarioRueda other = (UsuarioRueda) object;
        if ((this.usuariomante == null && other.usuariomante != null) || (this.usuariomante != null && !this.usuariomante.equals(other.usuariomante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.UsuarioRueda[ usuariomante=" + usuariomante + " ]";
    }
    
}
