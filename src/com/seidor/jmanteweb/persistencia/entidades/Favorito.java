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
@Table(name = "FAVORITO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Favorito.findAll", query = "SELECT f FROM Favorito f")
    , @NamedQuery(name = "Favorito.findByUsuario", query = "SELECT f FROM Favorito f WHERE f.favoritoPK.usuario = :usuario")
    , @NamedQuery(name = "Favorito.findByComponentemenu", query = "SELECT f FROM Favorito f WHERE f.favoritoPK.componentemenu = :componentemenu")
    , @NamedQuery(name = "Favorito.findByOrden", query = "SELECT f FROM Favorito f WHERE f.orden = :orden")
    , @NamedQuery(name = "Favorito.findByVersion", query = "SELECT f FROM Favorito f WHERE f.version = :version")})
public class Favorito implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FavoritoPK favoritoPK;
    @Basic(optional = false)
    @Column(name = "ORDEN")
    private BigInteger orden;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @JoinColumn(name = "COMPONENTEMENU", referencedColumnName = "NOMBRE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Componentemenu componentemenu1;
    @JoinColumn(name = "USUARIO", referencedColumnName = "USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario1;

    public Favorito() {
    }

    public Favorito(FavoritoPK favoritoPK) {
        this.favoritoPK = favoritoPK;
    }

    public Favorito(FavoritoPK favoritoPK, BigInteger orden, BigInteger version) {
        this.favoritoPK = favoritoPK;
        this.orden = orden;
        this.version = version;
    }

    public Favorito(String usuario, String componentemenu) {
        this.favoritoPK = new FavoritoPK(usuario, componentemenu);
    }

    public FavoritoPK getFavoritoPK() {
        return favoritoPK;
    }

    public void setFavoritoPK(FavoritoPK favoritoPK) {
        this.favoritoPK = favoritoPK;
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

    public Componentemenu getComponentemenu1() {
        return componentemenu1;
    }

    public void setComponentemenu1(Componentemenu componentemenu1) {
        this.componentemenu1 = componentemenu1;
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
        hash += (favoritoPK != null ? favoritoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Favorito)) {
            return false;
        }
        Favorito other = (Favorito) object;
        if ((this.favoritoPK == null && other.favoritoPK != null) || (this.favoritoPK != null && !this.favoritoPK.equals(other.favoritoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Favorito[ favoritoPK=" + favoritoPK + " ]";
    }
    
}
