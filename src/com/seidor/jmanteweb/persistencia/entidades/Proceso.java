/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.entidades;

import java.io.Serializable;
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
@Table(name = "PROCESO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proceso.findAll", query = "SELECT p FROM Proceso p")
    , @NamedQuery(name = "Proceso.findByNombre", query = "SELECT p FROM Proceso p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Proceso.findByHorainicio", query = "SELECT p FROM Proceso p WHERE p.horainicio = :horainicio")
    , @NamedQuery(name = "Proceso.findByIntervalo", query = "SELECT p FROM Proceso p WHERE p.intervalo = :intervalo")
    , @NamedQuery(name = "Proceso.findByFechasigejecucion", query = "SELECT p FROM Proceso p WHERE p.fechasigejecucion = :fechasigejecucion")
    , @NamedQuery(name = "Proceso.findByActivo", query = "SELECT p FROM Proceso p WHERE p.activo = :activo")
    , @NamedQuery(name = "Proceso.findByTipo", query = "SELECT p FROM Proceso p WHERE p.tipo = :tipo")
    , @NamedQuery(name = "Proceso.findByVersion", query = "SELECT p FROM Proceso p WHERE p.version = :version")
    , @NamedQuery(name = "Proceso.findByDias", query = "SELECT p FROM Proceso p WHERE p.dias = :dias")})
public class Proceso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "HORAINICIO")
    private String horainicio;
    @Column(name = "INTERVALO")
    private Long intervalo;
    @Column(name = "FECHASIGEJECUCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechasigejecucion;
    @Column(name = "ACTIVO")
    private Short activo;
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "VERSION")
    private BigInteger version;
    @Column(name = "DIAS")
    private String dias;

    public Proceso() {
    }

    public Proceso(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHorainicio() {
        return horainicio;
    }

    public void setHorainicio(String horainicio) {
        this.horainicio = horainicio;
    }

    public Long getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(Long intervalo) {
        this.intervalo = intervalo;
    }

    public Date getFechasigejecucion() {
        return fechasigejecucion;
    }

    public void setFechasigejecucion(Date fechasigejecucion) {
        this.fechasigejecucion = fechasigejecucion;
    }

    public Short getActivo() {
        return activo;
    }

    public void setActivo(Short activo) {
        this.activo = activo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombre != null ? nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proceso)) {
            return false;
        }
        Proceso other = (Proceso) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Proceso[ nombre=" + nombre + " ]";
    }
    
}
