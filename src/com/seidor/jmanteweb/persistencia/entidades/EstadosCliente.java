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
@Table(name = "ESTADOS_CLIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadosCliente.findAll", query = "SELECT e FROM EstadosCliente e")
    , @NamedQuery(name = "EstadosCliente.findByEstado", query = "SELECT e FROM EstadosCliente e WHERE e.estado = :estado")
    , @NamedQuery(name = "EstadosCliente.findByPrioridad", query = "SELECT e FROM EstadosCliente e WHERE e.prioridad = :prioridad")})
public class EstadosCliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "PRIORIDAD")
    private BigInteger prioridad;
    @OneToMany(mappedBy = "estado")
    private List<Clientes> clientesList;

    public EstadosCliente() {
    }

    public EstadosCliente(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigInteger getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(BigInteger prioridad) {
        this.prioridad = prioridad;
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
        hash += (estado != null ? estado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadosCliente)) {
            return false;
        }
        EstadosCliente other = (EstadosCliente) object;
        if ((this.estado == null && other.estado != null) || (this.estado != null && !this.estado.equals(other.estado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.EstadosCliente[ estado=" + estado + " ]";
    }
    
}
