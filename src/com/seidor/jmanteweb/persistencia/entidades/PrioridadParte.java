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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "PRIORIDAD_PARTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrioridadParte.findAll", query = "SELECT p FROM PrioridadParte p")
    , @NamedQuery(name = "PrioridadParte.findByPrioridad", query = "SELECT p FROM PrioridadParte p WHERE p.prioridad = :prioridad")
    , @NamedQuery(name = "PrioridadParte.findByDias", query = "SELECT p FROM PrioridadParte p WHERE p.dias = :dias")
    , @NamedQuery(name = "PrioridadParte.findByOrden", query = "SELECT p FROM PrioridadParte p WHERE p.orden = :orden")})
public class PrioridadParte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PRIORIDAD")
    private String prioridad;
    @Column(name = "DIAS")
    private BigInteger dias;
    @Column(name = "ORDEN")
    private BigInteger orden;
    @OneToMany(mappedBy = "importancia")
    private List<Partes> partesList;

    public PrioridadParte() {
    }

    public PrioridadParte(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public BigInteger getDias() {
        return dias;
    }

    public void setDias(BigInteger dias) {
        this.dias = dias;
    }

    public BigInteger getOrden() {
        return orden;
    }

    public void setOrden(BigInteger orden) {
        this.orden = orden;
    }

    @XmlTransient
    public List<Partes> getPartesList() {
        return partesList;
    }

    public void setPartesList(List<Partes> partesList) {
        this.partesList = partesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prioridad != null ? prioridad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrioridadParte)) {
            return false;
        }
        PrioridadParte other = (PrioridadParte) object;
        if ((this.prioridad == null && other.prioridad != null) || (this.prioridad != null && !this.prioridad.equals(other.prioridad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.PrioridadParte[ prioridad=" + prioridad + " ]";
    }
    
}
