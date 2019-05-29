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
@Table(name = "VPN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vpn.findAll", query = "SELECT v FROM Vpn v")
    , @NamedQuery(name = "Vpn.findById", query = "SELECT v FROM Vpn v WHERE v.id = :id")
    , @NamedQuery(name = "Vpn.findByVpn", query = "SELECT v FROM Vpn v WHERE v.vpn = :vpn")
    , @NamedQuery(name = "Vpn.findByUsuario", query = "SELECT v FROM Vpn v WHERE v.usuario = :usuario")
    , @NamedQuery(name = "Vpn.findByPassword", query = "SELECT v FROM Vpn v WHERE v.password = :password")
    , @NamedQuery(name = "Vpn.findByVersion", query = "SELECT v FROM Vpn v WHERE v.version = :version")
    , @NamedQuery(name = "Vpn.findByIp", query = "SELECT v FROM Vpn v WHERE v.ip = :ip")
    , @NamedQuery(name = "Vpn.findByEnlace", query = "SELECT v FROM Vpn v WHERE v.enlace = :enlace")
    , @NamedQuery(name = "Vpn.findByWmware", query = "SELECT v FROM Vpn v WHERE v.wmware = :wmware")
    , @NamedQuery(name = "Vpn.findByUsuarioalt", query = "SELECT v FROM Vpn v WHERE v.usuarioalt = :usuarioalt")
    , @NamedQuery(name = "Vpn.findByPasswordalt", query = "SELECT v FROM Vpn v WHERE v.passwordalt = :passwordalt")})
public class Vpn implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "VPN")
    private String vpn;
    @Column(name = "USUARIO")
    private String usuario;
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @Column(name = "IP")
    private String ip;
    @Column(name = "ENLACE")
    private String enlace;
    @Column(name = "WMWARE")
    private String wmware;
    @Column(name = "USUARIOALT")
    private String usuarioalt;
    @Column(name = "PASSWORDALT")
    private String passwordalt;
    @JoinColumn(name = "ID_CONEX_REMOTA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ConexionRemota idConexRemota;

    public Vpn() {
    }

    public Vpn(BigDecimal id) {
        this.id = id;
    }

    public Vpn(BigDecimal id, String vpn, BigInteger version) {
        this.id = id;
        this.vpn = vpn;
        this.version = version;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getVpn() {
        return vpn;
    }

    public void setVpn(String vpn) {
        this.vpn = vpn;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public String getWmware() {
        return wmware;
    }

    public void setWmware(String wmware) {
        this.wmware = wmware;
    }

    public String getUsuarioalt() {
        return usuarioalt;
    }

    public void setUsuarioalt(String usuarioalt) {
        this.usuarioalt = usuarioalt;
    }

    public String getPasswordalt() {
        return passwordalt;
    }

    public void setPasswordalt(String passwordalt) {
        this.passwordalt = passwordalt;
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
        if (!(object instanceof Vpn)) {
            return false;
        }
        Vpn other = (Vpn) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Vpn[ id=" + id + " ]";
    }
    
}
