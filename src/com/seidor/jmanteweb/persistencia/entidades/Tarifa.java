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
@Table(name = "TARIFA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tarifa.findAll", query = "SELECT t FROM Tarifa t")
    , @NamedQuery(name = "Tarifa.findByTarifa", query = "SELECT t FROM Tarifa t WHERE t.tarifa = :tarifa")
    , @NamedQuery(name = "Tarifa.findByDescripcion", query = "SELECT t FROM Tarifa t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "Tarifa.findByPrecio", query = "SELECT t FROM Tarifa t WHERE t.precio = :precio")
    , @NamedQuery(name = "Tarifa.findByVersion", query = "SELECT t FROM Tarifa t WHERE t.version = :version")})
public class Tarifa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TARIFA")
    private String tarifa;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "PRECIO")
    private BigInteger precio;
    @Column(name = "VERSION")
    private BigInteger version;
    @OneToMany(mappedBy = "tarifa")
    private List<ServiciosMante> serviciosManteList;
    @OneToMany(mappedBy = "tarifa")
    private List<IncidenciaParte> incidenciaParteList;
    @OneToMany(mappedBy = "tarifa")
    private List<Clientes> clientesList;

    public Tarifa() {
    }

    public Tarifa(String tarifa) {
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

    @XmlTransient
    public List<ServiciosMante> getServiciosManteList() {
        return serviciosManteList;
    }

    public void setServiciosManteList(List<ServiciosMante> serviciosManteList) {
        this.serviciosManteList = serviciosManteList;
    }

    @XmlTransient
    public List<IncidenciaParte> getIncidenciaParteList() {
        return incidenciaParteList;
    }

    public void setIncidenciaParteList(List<IncidenciaParte> incidenciaParteList) {
        this.incidenciaParteList = incidenciaParteList;
    }

    @XmlTransient
    public List<Clientes> getClientesList() {
        return clientesList;
    }

    public void setClientesList(List<Clientes> clientesList) {
        this.clientesList = clientesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tarifa != null ? tarifa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tarifa)) {
            return false;
        }
        Tarifa other = (Tarifa) object;
        if ((this.tarifa == null && other.tarifa != null) || (this.tarifa != null && !this.tarifa.equals(other.tarifa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Tarifa[ tarifa=" + tarifa + " ]";
    }
    
}
