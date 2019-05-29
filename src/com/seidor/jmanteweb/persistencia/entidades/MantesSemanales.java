/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AMARTEL
 */
@Entity
@Table(name = "MANTES_SEMANALES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MantesSemanales.findAll", query = "SELECT m FROM MantesSemanales m")
    , @NamedQuery(name = "MantesSemanales.findByUsuario", query = "SELECT m FROM MantesSemanales m WHERE m.usuario = :usuario")
    , @NamedQuery(name = "MantesSemanales.findByDia", query = "SELECT m FROM MantesSemanales m WHERE m.dia = :dia")
    , @NamedQuery(name = "MantesSemanales.findByActividad", query = "SELECT m FROM MantesSemanales m WHERE m.actividad = :actividad")
    , @NamedQuery(name = "MantesSemanales.findByComentario", query = "SELECT m FROM MantesSemanales m WHERE m.comentario = :comentario")
    , @NamedQuery(name = "MantesSemanales.findById", query = "SELECT m FROM MantesSemanales m WHERE m.id = :id")
    , @NamedQuery(name = "MantesSemanales.findByVersion", query = "SELECT m FROM MantesSemanales m WHERE m.version = :version")
    , @NamedQuery(name = "MantesSemanales.findByTipofestivo", query = "SELECT m FROM MantesSemanales m WHERE m.tipofestivo = :tipofestivo")})
public class MantesSemanales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "USUARIO")
    private String usuario;
    @Column(name = "DIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dia;
    @Column(name = "ACTIVIDAD")
    private String actividad;
    @Column(name = "COMENTARIO")
    private String comentario;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @Column(name = "TIPOFESTIVO")
    private String tipofestivo;

    public MantesSemanales() {
    }

    public MantesSemanales(BigDecimal id) {
        this.id = id;
    }

    public MantesSemanales(BigDecimal id, BigInteger version) {
        this.id = id;
        this.version = version;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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

    public String getTipofestivo() {
        return tipofestivo;
    }

    public void setTipofestivo(String tipofestivo) {
        this.tipofestivo = tipofestivo;
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
        if (!(object instanceof MantesSemanales)) {
            return false;
        }
        MantesSemanales other = (MantesSemanales) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.MantesSemanales[ id=" + id + " ]";
    }
    
}
