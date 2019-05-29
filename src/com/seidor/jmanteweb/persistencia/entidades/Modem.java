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
@Table(name = "MODEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modem.findAll", query = "SELECT m FROM Modem m")
    , @NamedQuery(name = "Modem.findById", query = "SELECT m FROM Modem m WHERE m.id = :id")
    , @NamedQuery(name = "Modem.findByTelefono", query = "SELECT m FROM Modem m WHERE m.telefono = :telefono")
    , @NamedQuery(name = "Modem.findByUsuario", query = "SELECT m FROM Modem m WHERE m.usuario = :usuario")
    , @NamedQuery(name = "Modem.findByPassword", query = "SELECT m FROM Modem m WHERE m.password = :password")
    , @NamedQuery(name = "Modem.findByCallback", query = "SELECT m FROM Modem m WHERE m.callback = :callback")
    , @NamedQuery(name = "Modem.findByVersion", query = "SELECT m FROM Modem m WHERE m.version = :version")})
public class Modem implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "USUARIO")
    private String usuario;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "CALLBACK")
    private BigInteger callback;
    @Column(name = "VERSION")
    private BigInteger version;
    @JoinColumn(name = "ID_CONEX_REMOTA", referencedColumnName = "ID")
    @ManyToOne
    private ConexionRemota idConexRemota;

    public Modem() {
    }

    public Modem(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public BigInteger getCallback() {
        return callback;
    }

    public void setCallback(BigInteger callback) {
        this.callback = callback;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
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
        if (!(object instanceof Modem)) {
            return false;
        }
        Modem other = (Modem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Modem[ id=" + id + " ]";
    }
    
}
