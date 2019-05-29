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
@Table(name = "PANTALLAINI")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pantallaini.findAll", query = "SELECT p FROM Pantallaini p")
    , @NamedQuery(name = "Pantallaini.findById", query = "SELECT p FROM Pantallaini p WHERE p.id = :id")
    , @NamedQuery(name = "Pantallaini.findByOrden", query = "SELECT p FROM Pantallaini p WHERE p.orden = :orden")
    , @NamedQuery(name = "Pantallaini.findByVersion", query = "SELECT p FROM Pantallaini p WHERE p.version = :version")})
public class Pantallaini implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "ORDEN")
    private BigInteger orden;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @JoinColumn(name = "COMPONENTE", referencedColumnName = "NOMBRE")
    @ManyToOne(optional = false)
    private Componentemenu componente;
    @JoinColumn(name = "USUARIO", referencedColumnName = "USUARIO")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Pantallaini() {
    }

    public Pantallaini(BigDecimal id) {
        this.id = id;
    }

    public Pantallaini(BigDecimal id, BigInteger orden, BigInteger version) {
        this.id = id;
        this.orden = orden;
        this.version = version;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getOrden() {
        return orden;
    }

    public void setOrden(BigInteger orden) {
        this.orden = orden;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public Componentemenu getComponente() {
        return componente;
    }

    public void setComponente(Componentemenu componente) {
        this.componente = componente;
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
        if (!(object instanceof Pantallaini)) {
            return false;
        }
        Pantallaini other = (Pantallaini) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Pantallaini[ id=" + id + " ]";
    }
    
}
