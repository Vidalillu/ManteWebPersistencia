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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AMARTEL
 */
@Entity
@Table(name = "ACTIVIDADES_MANTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActividadesMante.findAll", query = "SELECT a FROM ActividadesMante a")
    , @NamedQuery(name = "ActividadesMante.findByActividad", query = "SELECT a FROM ActividadesMante a WHERE a.actividad = :actividad")
    , @NamedQuery(name = "ActividadesMante.findByColorseleccionado", query = "SELECT a FROM ActividadesMante a WHERE a.colorseleccionado = :colorseleccionado")
    , @NamedQuery(name = "ActividadesMante.findById", query = "SELECT a FROM ActividadesMante a WHERE a.id = :id")
    , @NamedQuery(name = "ActividadesMante.findByVersion", query = "SELECT a FROM ActividadesMante a WHERE a.version = :version")
    , @NamedQuery(name = "ActividadesMante.findByOrden", query = "SELECT a FROM ActividadesMante a WHERE a.orden = :orden")
    , @NamedQuery(name = "ActividadesMante.findByAbreviacion", query = "SELECT a FROM ActividadesMante a WHERE a.abreviacion = :abreviacion")
    , @NamedQuery(name = "ActividadesMante.findByPedircomentario", query = "SELECT a FROM ActividadesMante a WHERE a.pedircomentario = :pedircomentario")})
public class ActividadesMante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "ACTIVIDAD")
    private String actividad;
    @Column(name = "COLORSELECCIONADO")
    private String colorseleccionado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "VERSION")
    private BigInteger version;
    @Column(name = "ORDEN")
    private BigInteger orden;
    @Column(name = "ABREVIACION")
    private String abreviacion;
    @Column(name = "PEDIRCOMENTARIO")
    private BigInteger pedircomentario;

    public ActividadesMante() {
    }

    public ActividadesMante(BigDecimal id) {
        this.id = id;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getColorseleccionado() {
        return colorseleccionado;
    }

    public void setColorseleccionado(String colorseleccionado) {
        this.colorseleccionado = colorseleccionado;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public BigInteger getOrden() {
        return orden;
    }

    public void setOrden(BigInteger orden) {
        this.orden = orden;
    }

    public String getAbreviacion() {
        return abreviacion;
    }

    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }

    public BigInteger getPedircomentario() {
        return pedircomentario;
    }

    public void setPedircomentario(BigInteger pedircomentario) {
        this.pedircomentario = pedircomentario;
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
        if (!(object instanceof ActividadesMante)) {
            return false;
        }
        ActividadesMante other = (ActividadesMante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.ActividadesMante[ id=" + id + " ]";
    }
    
}
