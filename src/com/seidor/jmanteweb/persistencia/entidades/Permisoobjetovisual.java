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
@Table(name = "PERMISOOBJETOVISUAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Permisoobjetovisual.findAll", query = "SELECT p FROM Permisoobjetovisual p")
    , @NamedQuery(name = "Permisoobjetovisual.findByObjetovisual", query = "SELECT p FROM Permisoobjetovisual p WHERE p.permisoobjetovisualPK.objetovisual = :objetovisual")
    , @NamedQuery(name = "Permisoobjetovisual.findByPerfil", query = "SELECT p FROM Permisoobjetovisual p WHERE p.permisoobjetovisualPK.perfil = :perfil")
    , @NamedQuery(name = "Permisoobjetovisual.findByActivo", query = "SELECT p FROM Permisoobjetovisual p WHERE p.activo = :activo")
    , @NamedQuery(name = "Permisoobjetovisual.findByVersion", query = "SELECT p FROM Permisoobjetovisual p WHERE p.version = :version")})
public class Permisoobjetovisual implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PermisoobjetovisualPK permisoobjetovisualPK;
    @Basic(optional = false)
    @Column(name = "ACTIVO")
    private BigInteger activo;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @JoinColumn(name = "OBJETOVISUAL", referencedColumnName = "IDOBJETO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Objetovisual objetovisual1;
    @JoinColumn(name = "PERFIL", referencedColumnName = "PERFIL", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Perfil perfil1;

    public Permisoobjetovisual() {
    }

    public Permisoobjetovisual(PermisoobjetovisualPK permisoobjetovisualPK) {
        this.permisoobjetovisualPK = permisoobjetovisualPK;
    }

    public Permisoobjetovisual(PermisoobjetovisualPK permisoobjetovisualPK, BigInteger activo, BigInteger version) {
        this.permisoobjetovisualPK = permisoobjetovisualPK;
        this.activo = activo;
        this.version = version;
    }

    public Permisoobjetovisual(BigInteger objetovisual, String perfil) {
        this.permisoobjetovisualPK = new PermisoobjetovisualPK(objetovisual, perfil);
    }

    public PermisoobjetovisualPK getPermisoobjetovisualPK() {
        return permisoobjetovisualPK;
    }

    public void setPermisoobjetovisualPK(PermisoobjetovisualPK permisoobjetovisualPK) {
        this.permisoobjetovisualPK = permisoobjetovisualPK;
    }

    public BigInteger getActivo() {
        return activo;
    }

    public void setActivo(BigInteger activo) {
        this.activo = activo;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public Objetovisual getObjetovisual1() {
        return objetovisual1;
    }

    public void setObjetovisual1(Objetovisual objetovisual1) {
        this.objetovisual1 = objetovisual1;
    }

    public Perfil getPerfil1() {
        return perfil1;
    }

    public void setPerfil1(Perfil perfil1) {
        this.perfil1 = perfil1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (permisoobjetovisualPK != null ? permisoobjetovisualPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permisoobjetovisual)) {
            return false;
        }
        Permisoobjetovisual other = (Permisoobjetovisual) object;
        if ((this.permisoobjetovisualPK == null && other.permisoobjetovisualPK != null) || (this.permisoobjetovisualPK != null && !this.permisoobjetovisualPK.equals(other.permisoobjetovisualPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Permisoobjetovisual[ permisoobjetovisualPK=" + permisoobjetovisualPK + " ]";
    }
    
}
