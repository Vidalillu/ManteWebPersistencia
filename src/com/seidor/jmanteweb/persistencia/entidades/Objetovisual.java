/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AMARTEL
 */
@Entity
@Table(name = "OBJETOVISUAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Objetovisual.findAll", query = "SELECT o FROM Objetovisual o")
    , @NamedQuery(name = "Objetovisual.findByIdobjeto", query = "SELECT o FROM Objetovisual o WHERE o.idobjeto = :idobjeto")
    , @NamedQuery(name = "Objetovisual.findByNombre", query = "SELECT o FROM Objetovisual o WHERE o.nombre = :nombre")
    , @NamedQuery(name = "Objetovisual.findByVersion", query = "SELECT o FROM Objetovisual o WHERE o.version = :version")
    , @NamedQuery(name = "Objetovisual.findByTipo", query = "SELECT o FROM Objetovisual o WHERE o.tipo = :tipo")
    , @NamedQuery(name = "Objetovisual.findByTipointerno", query = "SELECT o FROM Objetovisual o WHERE o.tipointerno = :tipointerno")
    , @NamedQuery(name = "Objetovisual.findByNombrealt", query = "SELECT o FROM Objetovisual o WHERE o.nombrealt = :nombrealt")})
public class Objetovisual implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDOBJETO")
    private BigInteger idobjeto;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "TIPOINTERNO")
    private String tipointerno;
    @Column(name = "NOMBREALT")
    private String nombrealt;
    @JoinColumn(name = "PANTALLA", referencedColumnName = "NOMBRE")
    @ManyToOne(optional = false)
    private Componentemenu pantalla;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "objetovisual1")
    private List<Permisoobjetovisual> permisoobjetovisualList;

    public Objetovisual() {
    }

    public Objetovisual(BigInteger idobjeto) {
        this.idobjeto = idobjeto;
    }

    public Objetovisual(BigInteger idobjeto, String nombre, BigInteger version, String tipo) {
        this.idobjeto = idobjeto;
        this.nombre = nombre;
        this.version = version;
        this.tipo = tipo;
    }

    public BigInteger getIdobjeto() {
        return idobjeto;
    }

    public void setIdobjeto(BigInteger idobjeto) {
        this.idobjeto = idobjeto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipointerno() {
        return tipointerno;
    }

    public void setTipointerno(String tipointerno) {
        this.tipointerno = tipointerno;
    }

    public String getNombrealt() {
        return nombrealt;
    }

    public void setNombrealt(String nombrealt) {
        this.nombrealt = nombrealt;
    }

    public Componentemenu getPantalla() {
        return pantalla;
    }

    public void setPantalla(Componentemenu pantalla) {
        this.pantalla = pantalla;
    }

    @XmlTransient
    public List<Permisoobjetovisual> getPermisoobjetovisualList() {
        return permisoobjetovisualList;
    }

    public void setPermisoobjetovisualList(List<Permisoobjetovisual> permisoobjetovisualList) {
        this.permisoobjetovisualList = permisoobjetovisualList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idobjeto != null ? idobjeto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Objetovisual)) {
            return false;
        }
        Objetovisual other = (Objetovisual) object;
        if ((this.idobjeto == null && other.idobjeto != null) || (this.idobjeto != null && !this.idobjeto.equals(other.idobjeto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Objetovisual[ idobjeto=" + idobjeto + " ]";
    }
    
}
