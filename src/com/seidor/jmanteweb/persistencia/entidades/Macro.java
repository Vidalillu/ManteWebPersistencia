/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AMARTEL
 */
@Entity
@Table(name = "MACRO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Macro.findAll", query = "SELECT m FROM Macro m")
    , @NamedQuery(name = "Macro.findByMacro", query = "SELECT m FROM Macro m WHERE m.macro = :macro")
    , @NamedQuery(name = "Macro.findByDescripcion", query = "SELECT m FROM Macro m WHERE m.descripcion = :descripcion")
    , @NamedQuery(name = "Macro.findByFechacreacion", query = "SELECT m FROM Macro m WHERE m.fechacreacion = :fechacreacion")
    , @NamedQuery(name = "Macro.findByVersion", query = "SELECT m FROM Macro m WHERE m.version = :version")
    , @NamedQuery(name = "Macro.findByConfichero", query = "SELECT m FROM Macro m WHERE m.confichero = :confichero")
    , @NamedQuery(name = "Macro.findByFechaultimaejec", query = "SELECT m FROM Macro m WHERE m.fechaultimaejec = :fechaultimaejec")})
public class Macro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "MACRO")
    private String macro;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "FECHACREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @Basic(optional = false)
    @Column(name = "CONFICHERO")
    private short confichero;
    @Column(name = "FECHAULTIMAEJEC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaultimaejec;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "macro")
    private List<Macrodetalle> macrodetalleList;
    @JoinColumn(name = "PANTALLA", referencedColumnName = "NOMBRE")
    @ManyToOne(optional = false)
    private Componentemenu pantalla;

    public Macro() {
    }

    public Macro(String macro) {
        this.macro = macro;
    }

    public Macro(String macro, Date fechacreacion, BigInteger version, short confichero) {
        this.macro = macro;
        this.fechacreacion = fechacreacion;
        this.version = version;
        this.confichero = confichero;
    }

    public String getMacro() {
        return macro;
    }

    public void setMacro(String macro) {
        this.macro = macro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public short getConfichero() {
        return confichero;
    }

    public void setConfichero(short confichero) {
        this.confichero = confichero;
    }

    public Date getFechaultimaejec() {
        return fechaultimaejec;
    }

    public void setFechaultimaejec(Date fechaultimaejec) {
        this.fechaultimaejec = fechaultimaejec;
    }

    @XmlTransient
    public List<Macrodetalle> getMacrodetalleList() {
        return macrodetalleList;
    }

    public void setMacrodetalleList(List<Macrodetalle> macrodetalleList) {
        this.macrodetalleList = macrodetalleList;
    }

    public Componentemenu getPantalla() {
        return pantalla;
    }

    public void setPantalla(Componentemenu pantalla) {
        this.pantalla = pantalla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (macro != null ? macro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Macro)) {
            return false;
        }
        Macro other = (Macro) object;
        if ((this.macro == null && other.macro != null) || (this.macro != null && !this.macro.equals(other.macro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Macro[ macro=" + macro + " ]";
    }
    
}
