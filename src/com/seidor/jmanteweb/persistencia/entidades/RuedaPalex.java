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
@Table(name = "RUEDA_PALEX")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RuedaPalex.findAll", query = "SELECT r FROM RuedaPalex r")
    , @NamedQuery(name = "RuedaPalex.findByUsuario", query = "SELECT r FROM RuedaPalex r WHERE r.usuario = :usuario")
    , @NamedQuery(name = "RuedaPalex.findByTurnonormal", query = "SELECT r FROM RuedaPalex r WHERE r.turnonormal = :turnonormal")
    , @NamedQuery(name = "RuedaPalex.findByTurnoviernes", query = "SELECT r FROM RuedaPalex r WHERE r.turnoviernes = :turnoviernes")
    , @NamedQuery(name = "RuedaPalex.findByTurnofinde", query = "SELECT r FROM RuedaPalex r WHERE r.turnofinde = :turnofinde")
    , @NamedQuery(name = "RuedaPalex.findByTurnofestvig", query = "SELECT r FROM RuedaPalex r WHERE r.turnofestvig = :turnofestvig")
    , @NamedQuery(name = "RuedaPalex.findByTurnosuper", query = "SELECT r FROM RuedaPalex r WHERE r.turnosuper = :turnosuper")
    , @NamedQuery(name = "RuedaPalex.findByVersion", query = "SELECT r FROM RuedaPalex r WHERE r.version = :version")
    , @NamedQuery(name = "RuedaPalex.findByOrdenfestvig", query = "SELECT r FROM RuedaPalex r WHERE r.ordenfestvig = :ordenfestvig")
    , @NamedQuery(name = "RuedaPalex.findByOrdensuper", query = "SELECT r FROM RuedaPalex r WHERE r.ordensuper = :ordensuper")})
public class RuedaPalex implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "USUARIO")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "TURNONORMAL")
    private short turnonormal;
    @Basic(optional = false)
    @Column(name = "TURNOVIERNES")
    private short turnoviernes;
    @Basic(optional = false)
    @Column(name = "TURNOFINDE")
    private short turnofinde;
    @Basic(optional = false)
    @Column(name = "TURNOFESTVIG")
    private short turnofestvig;
    @Basic(optional = false)
    @Column(name = "TURNOSUPER")
    private short turnosuper;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @Column(name = "ORDENFESTVIG")
    private String ordenfestvig;
    @Column(name = "ORDENSUPER")
    private String ordensuper;
    @JoinColumn(name = "USUARIO", referencedColumnName = "USUARIO", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario1;

    public RuedaPalex() {
    }

    public RuedaPalex(String usuario) {
        this.usuario = usuario;
    }

    public RuedaPalex(String usuario, short turnonormal, short turnoviernes, short turnofinde, short turnofestvig, short turnosuper, BigInteger version) {
        this.usuario = usuario;
        this.turnonormal = turnonormal;
        this.turnoviernes = turnoviernes;
        this.turnofinde = turnofinde;
        this.turnofestvig = turnofestvig;
        this.turnosuper = turnosuper;
        this.version = version;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public short getTurnonormal() {
        return turnonormal;
    }

    public void setTurnonormal(short turnonormal) {
        this.turnonormal = turnonormal;
    }

    public short getTurnoviernes() {
        return turnoviernes;
    }

    public void setTurnoviernes(short turnoviernes) {
        this.turnoviernes = turnoviernes;
    }

    public short getTurnofinde() {
        return turnofinde;
    }

    public void setTurnofinde(short turnofinde) {
        this.turnofinde = turnofinde;
    }

    public short getTurnofestvig() {
        return turnofestvig;
    }

    public void setTurnofestvig(short turnofestvig) {
        this.turnofestvig = turnofestvig;
    }

    public short getTurnosuper() {
        return turnosuper;
    }

    public void setTurnosuper(short turnosuper) {
        this.turnosuper = turnosuper;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public String getOrdenfestvig() {
        return ordenfestvig;
    }

    public void setOrdenfestvig(String ordenfestvig) {
        this.ordenfestvig = ordenfestvig;
    }

    public String getOrdensuper() {
        return ordensuper;
    }

    public void setOrdensuper(String ordensuper) {
        this.ordensuper = ordensuper;
    }

    public Usuario getUsuario1() {
        return usuario1;
    }

    public void setUsuario1(Usuario usuario1) {
        this.usuario1 = usuario1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuario != null ? usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RuedaPalex)) {
            return false;
        }
        RuedaPalex other = (RuedaPalex) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.RuedaPalex[ usuario=" + usuario + " ]";
    }
    
}
