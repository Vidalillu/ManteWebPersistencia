/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AMARTEL
 */
@Entity
@Table(name = "CONEXION_REMOTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConexionRemota.findAll", query = "SELECT c FROM ConexionRemota c")
    , @NamedQuery(name = "ConexionRemota.findById", query = "SELECT c FROM ConexionRemota c WHERE c.id = :id")
    , @NamedQuery(name = "ConexionRemota.findByPrograma", query = "SELECT c FROM ConexionRemota c WHERE c.programa = :programa")
    , @NamedQuery(name = "ConexionRemota.findByObservaciones", query = "SELECT c FROM ConexionRemota c WHERE c.observaciones = :observaciones")
    , @NamedQuery(name = "ConexionRemota.findByVersion", query = "SELECT c FROM ConexionRemota c WHERE c.version = :version")})
public class ConexionRemota implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "PROGRAMA")
    private String programa;
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Column(name = "VERSION")
    private BigInteger version;
    @JoinColumn(name = "CLIENTE", referencedColumnName = "CLIENTE")
    @OneToOne
    private Clientes cliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idConexRemota")
    private List<ConexionPc> conexionPcList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idConexRemota")
    private List<Vpn> vpnList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idConexion")
    private List<ConexionPcsSeidor> conexionPcsSeidorList;
    @OneToMany(mappedBy = "idConexRemota")
    private List<Modem> modemList;

    public ConexionRemota() {
    }

    public ConexionRemota(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    @XmlTransient
    public List<ConexionPc> getConexionPcList() {
        return conexionPcList;
    }

    public void setConexionPcList(List<ConexionPc> conexionPcList) {
        this.conexionPcList = conexionPcList;
    }

    @XmlTransient
    public List<Vpn> getVpnList() {
        return vpnList;
    }

    public void setVpnList(List<Vpn> vpnList) {
        this.vpnList = vpnList;
    }

    @XmlTransient
    public List<ConexionPcsSeidor> getConexionPcsSeidorList() {
        return conexionPcsSeidorList;
    }

    public void setConexionPcsSeidorList(List<ConexionPcsSeidor> conexionPcsSeidorList) {
        this.conexionPcsSeidorList = conexionPcsSeidorList;
    }

    @XmlTransient
    public List<Modem> getModemList() {
        return modemList;
    }

    public void setModemList(List<Modem> modemList) {
        this.modemList = modemList;
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
        if (!(object instanceof ConexionRemota)) {
            return false;
        }
        ConexionRemota other = (ConexionRemota) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.ConexionRemota[ id=" + id + " ]";
    }
    
}
