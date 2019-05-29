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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AMARTEL
 */
@Entity
@Table(name = "INCIDENCIA_PARTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IncidenciaParte.findAll", query = "SELECT i FROM IncidenciaParte i")
    , @NamedQuery(name = "IncidenciaParte.findByNumParte", query = "SELECT i FROM IncidenciaParte i WHERE i.numParte = :numParte")
    , @NamedQuery(name = "IncidenciaParte.findByHoras", query = "SELECT i FROM IncidenciaParte i WHERE i.horas = :horas")
    , @NamedQuery(name = "IncidenciaParte.findByPosicionsap", query = "SELECT i FROM IncidenciaParte i WHERE i.posicionsap = :posicionsap")})
public class IncidenciaParte implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "NUM_PARTE")
    private BigDecimal numParte;
    @Basic(optional = false)
    @Column(name = "HORAS")
    private BigInteger horas;
    @Column(name = "POSICIONSAP")
    private Integer posicionsap;
    @JoinColumn(name = "NUM_PARTE", referencedColumnName = "NUM_PARTE", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Partes partes;
    @JoinColumn(name = "ID_PETICION", referencedColumnName = "IDPETICION")
    @ManyToOne
    private Peticiones idPeticion;
    @JoinColumn(name = "TARIFA", referencedColumnName = "TARIFA")
    @ManyToOne
    private Tarifa tarifa;

    public IncidenciaParte() {
    }

    public IncidenciaParte(BigDecimal numParte) {
        this.numParte = numParte;
    }

    public IncidenciaParte(BigDecimal numParte, BigInteger horas) {
        this.numParte = numParte;
        this.horas = horas;
    }

    public BigDecimal getNumParte() {
        return numParte;
    }

    public void setNumParte(BigDecimal numParte) {
        this.numParte = numParte;
    }

    public BigInteger getHoras() {
        return horas;
    }

    public void setHoras(BigInteger horas) {
        this.horas = horas;
    }

    public Integer getPosicionsap() {
        return posicionsap;
    }

    public void setPosicionsap(Integer posicionsap) {
        this.posicionsap = posicionsap;
    }

    public Partes getPartes() {
        return partes;
    }

    public void setPartes(Partes partes) {
        this.partes = partes;
    }

    public Peticiones getIdPeticion() {
        return idPeticion;
    }

    public void setIdPeticion(Peticiones idPeticion) {
        this.idPeticion = idPeticion;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numParte != null ? numParte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IncidenciaParte)) {
            return false;
        }
        IncidenciaParte other = (IncidenciaParte) object;
        if ((this.numParte == null && other.numParte != null) || (this.numParte != null && !this.numParte.equals(other.numParte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.IncidenciaParte[ numParte=" + numParte + " ]";
    }
    
}
