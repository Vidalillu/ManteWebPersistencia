/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.entidades;

import java.io.Serializable;
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
@Table(name = "PERFIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Perfil.findAll", query = "SELECT p FROM Perfil p")
    , @NamedQuery(name = "Perfil.findByPerfil", query = "SELECT p FROM Perfil p WHERE p.perfil = :perfil")
    , @NamedQuery(name = "Perfil.findByDescripcion", query = "SELECT p FROM Perfil p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Perfil.findByVersion", query = "SELECT p FROM Perfil p WHERE p.version = :version")
    , @NamedQuery(name = "Perfil.findByTipo", query = "SELECT p FROM Perfil p WHERE p.tipo = :tipo")
    , @NamedQuery(name = "Perfil.findByPerfiladmin", query = "SELECT p FROM Perfil p WHERE p.perfiladmin = :perfiladmin")})
public class Perfil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PERFIL")
    private String perfil;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @Column(name = "TIPO")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "PERFILADMIN")
    private BigInteger perfiladmin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfil1")
    private List<Permisoobjetovisual> permisoobjetovisualList;
    @JoinColumn(name = "MENUPROGRAMA", referencedColumnName = "NOMBRE")
    @ManyToOne
    private Componentemenu menuprograma;
    @OneToMany(mappedBy = "perfil")
    private List<Usuario> usuarioList;

    public Perfil() {
    }

    public Perfil(String perfil) {
        this.perfil = perfil;
    }

    public Perfil(String perfil, BigInteger version, BigInteger perfiladmin) {
        this.perfil = perfil;
        this.version = version;
        this.perfiladmin = perfiladmin;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public BigInteger getPerfiladmin() {
        return perfiladmin;
    }

    public void setPerfiladmin(BigInteger perfiladmin) {
        this.perfiladmin = perfiladmin;
    }

    @XmlTransient
    public List<Permisoobjetovisual> getPermisoobjetovisualList() {
        return permisoobjetovisualList;
    }

    public void setPermisoobjetovisualList(List<Permisoobjetovisual> permisoobjetovisualList) {
        this.permisoobjetovisualList = permisoobjetovisualList;
    }

    public Componentemenu getMenuprograma() {
        return menuprograma;
    }

    public void setMenuprograma(Componentemenu menuprograma) {
        this.menuprograma = menuprograma;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (perfil != null ? perfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Perfil)) {
            return false;
        }
        Perfil other = (Perfil) object;
        if ((this.perfil == null && other.perfil != null) || (this.perfil != null && !this.perfil.equals(other.perfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Perfil[ perfil=" + perfil + " ]";
    }
    
}
