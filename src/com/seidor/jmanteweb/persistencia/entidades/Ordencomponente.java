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
@Table(name = "ORDENCOMPONENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ordencomponente.findAll", query = "SELECT o FROM Ordencomponente o")
    , @NamedQuery(name = "Ordencomponente.findByMenu", query = "SELECT o FROM Ordencomponente o WHERE o.ordencomponentePK.menu = :menu")
    , @NamedQuery(name = "Ordencomponente.findByComponente", query = "SELECT o FROM Ordencomponente o WHERE o.ordencomponentePK.componente = :componente")
    , @NamedQuery(name = "Ordencomponente.findByOrden", query = "SELECT o FROM Ordencomponente o WHERE o.orden = :orden")
    , @NamedQuery(name = "Ordencomponente.findByVersion", query = "SELECT o FROM Ordencomponente o WHERE o.version = :version")})
public class Ordencomponente implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrdencomponentePK ordencomponentePK;
    @Basic(optional = false)
    @Column(name = "ORDEN")
    private BigInteger orden;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @JoinColumn(name = "COMPONENTE", referencedColumnName = "NOMBRE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Componentemenu componentemenu;
    @JoinColumn(name = "MENU", referencedColumnName = "NOMBRE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Componentemenu componentemenu1;

    public Ordencomponente() {
    }

    public Ordencomponente(OrdencomponentePK ordencomponentePK) {
        this.ordencomponentePK = ordencomponentePK;
    }

    public Ordencomponente(OrdencomponentePK ordencomponentePK, BigInteger orden, BigInteger version) {
        this.ordencomponentePK = ordencomponentePK;
        this.orden = orden;
        this.version = version;
    }

    public Ordencomponente(String menu, String componente) {
        this.ordencomponentePK = new OrdencomponentePK(menu, componente);
    }

    public OrdencomponentePK getOrdencomponentePK() {
        return ordencomponentePK;
    }

    public void setOrdencomponentePK(OrdencomponentePK ordencomponentePK) {
        this.ordencomponentePK = ordencomponentePK;
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

    public Componentemenu getComponentemenu() {
        return componentemenu;
    }

    public void setComponentemenu(Componentemenu componentemenu) {
        this.componentemenu = componentemenu;
    }

    public Componentemenu getComponentemenu1() {
        return componentemenu1;
    }

    public void setComponentemenu1(Componentemenu componentemenu1) {
        this.componentemenu1 = componentemenu1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ordencomponentePK != null ? ordencomponentePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ordencomponente)) {
            return false;
        }
        Ordencomponente other = (Ordencomponente) object;
        if ((this.ordencomponentePK == null && other.ordencomponentePK != null) || (this.ordencomponentePK != null && !this.ordencomponentePK.equals(other.ordencomponentePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Ordencomponente[ ordencomponentePK=" + ordencomponentePK + " ]";
    }
    
}
