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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "TARIFAPALEX")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tarifapalex.findAll", query = "SELECT t FROM Tarifapalex t")
    , @NamedQuery(name = "Tarifapalex.findByTarifa", query = "SELECT t FROM Tarifapalex t WHERE t.tarifa = :tarifa")
    , @NamedQuery(name = "Tarifapalex.findByDescripcion", query = "SELECT t FROM Tarifapalex t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "Tarifapalex.findByPrecio", query = "SELECT t FROM Tarifapalex t WHERE t.precio = :precio")
    , @NamedQuery(name = "Tarifapalex.findByVersion", query = "SELECT t FROM Tarifapalex t WHERE t.version = :version")
    , @NamedQuery(name = "Tarifapalex.findByInicioTarifa", query = "SELECT t FROM Tarifapalex t WHERE t.inicioTarifa = :inicioTarifa")
    , @NamedQuery(name = "Tarifapalex.findByFinTarifa", query = "SELECT t FROM Tarifapalex t WHERE t.finTarifa = :finTarifa")
    , @NamedQuery(name = "Tarifapalex.findById", query = "SELECT t FROM Tarifapalex t WHERE t.id = :id")})
public class Tarifapalex implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "TARIFA")
    private String tarifa;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "PRECIO")
    private BigInteger precio;
    @Column(name = "VERSION")
    private BigInteger version;
    @Column(name = "INICIO_TARIFA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inicioTarifa;
    @Column(name = "FIN_TARIFA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finTarifa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtarifa")
    private List<Guardiapalex> guardiapalexList;

    public Tarifapalex() {
    }

    public Tarifapalex(BigDecimal id) {
        this.id = id;
    }

    public Tarifapalex(BigDecimal id, String tarifa) {
        this.id = id;
        this.tarifa = tarifa;
    }

    public String getTarifa() {
        return tarifa;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigInteger getPrecio() {
        return precio;
    }

    public void setPrecio(BigInteger precio) {
        this.precio = precio;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public Date getInicioTarifa() {
        return inicioTarifa;
    }

    public void setInicioTarifa(Date inicioTarifa) {
        this.inicioTarifa = inicioTarifa;
    }

    public Date getFinTarifa() {
        return finTarifa;
    }

    public void setFinTarifa(Date finTarifa) {
        this.finTarifa = finTarifa;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    @XmlTransient
    public List<Guardiapalex> getGuardiapalexList() {
        return guardiapalexList;
    }

    public void setGuardiapalexList(List<Guardiapalex> guardiapalexList) {
        this.guardiapalexList = guardiapalexList;
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
        if (!(object instanceof Tarifapalex)) {
            return false;
        }
        Tarifapalex other = (Tarifapalex) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Tarifapalex[ id=" + id + " ]";
    }
    
}
