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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "PETICIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Peticiones.findAll", query = "SELECT p FROM Peticiones p")
    , @NamedQuery(name = "Peticiones.findByIdpeticion", query = "SELECT p FROM Peticiones p WHERE p.idpeticion = :idpeticion")
    , @NamedQuery(name = "Peticiones.findByPrecio", query = "SELECT p FROM Peticiones p WHERE p.precio = :precio")
    , @NamedQuery(name = "Peticiones.findByDisponibleFacturacion", query = "SELECT p FROM Peticiones p WHERE p.disponibleFacturacion = :disponibleFacturacion")
    , @NamedQuery(name = "Peticiones.findByFacturado", query = "SELECT p FROM Peticiones p WHERE p.facturado = :facturado")
    , @NamedQuery(name = "Peticiones.findByTipo", query = "SELECT p FROM Peticiones p WHERE p.tipo = :tipo")
    , @NamedQuery(name = "Peticiones.findByVersion", query = "SELECT p FROM Peticiones p WHERE p.version = :version")
    , @NamedQuery(name = "Peticiones.findByFechacreacion", query = "SELECT p FROM Peticiones p WHERE p.fechacreacion = :fechacreacion")
    , @NamedQuery(name = "Peticiones.findByFechaenviosap", query = "SELECT p FROM Peticiones p WHERE p.fechaenviosap = :fechaenviosap")
    , @NamedQuery(name = "Peticiones.findByEstadosap", query = "SELECT p FROM Peticiones p WHERE p.estadosap = :estadosap")
    , @NamedQuery(name = "Peticiones.findByPeriodofacturacion", query = "SELECT p FROM Peticiones p WHERE p.periodofacturacion = :periodofacturacion")})
public class Peticiones implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDPETICION")
    private BigDecimal idpeticion;
    @Basic(optional = false)
    @Column(name = "PRECIO")
    private BigInteger precio;
    @Basic(optional = false)
    @Column(name = "DISPONIBLE_FACTURACION")
    private String disponibleFacturacion;
    @Basic(optional = false)
    @Column(name = "FACTURADO")
    private BigInteger facturado;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @Basic(optional = false)
    @Column(name = "FECHACREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    @Column(name = "FECHAENVIOSAP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaenviosap;
    @Column(name = "ESTADOSAP")
    private String estadosap;
    @Column(name = "PERIODOFACTURACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date periodofacturacion;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "peticiones")
    private Bonomodis bonomodis;
    @JoinColumn(name = "CLIENTE", referencedColumnName = "CLIENTE")
    @ManyToOne(optional = false)
    private Clientes cliente;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "peticiones")
    private ServiciosMante serviciosMante;
    @OneToMany(mappedBy = "idPeticion")
    private List<IncidenciaParte> incidenciaParteList;
    @OneToMany(mappedBy = "idPeticion")
    private List<Presupuestos> presupuestosList;

    public Peticiones() {
    }

    public Peticiones(BigDecimal idpeticion) {
        this.idpeticion = idpeticion;
    }

    public Peticiones(BigDecimal idpeticion, BigInteger precio, String disponibleFacturacion, BigInteger facturado, String tipo, BigInteger version, Date fechacreacion) {
        this.idpeticion = idpeticion;
        this.precio = precio;
        this.disponibleFacturacion = disponibleFacturacion;
        this.facturado = facturado;
        this.tipo = tipo;
        this.version = version;
        this.fechacreacion = fechacreacion;
    }

    public BigDecimal getIdpeticion() {
        return idpeticion;
    }

    public void setIdpeticion(BigDecimal idpeticion) {
        this.idpeticion = idpeticion;
    }

    public BigInteger getPrecio() {
        return precio;
    }

    public void setPrecio(BigInteger precio) {
        this.precio = precio;
    }

    public String getDisponibleFacturacion() {
        return disponibleFacturacion;
    }

    public void setDisponibleFacturacion(String disponibleFacturacion) {
        this.disponibleFacturacion = disponibleFacturacion;
    }

    public BigInteger getFacturado() {
        return facturado;
    }

    public void setFacturado(BigInteger facturado) {
        this.facturado = facturado;
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

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public Date getFechaenviosap() {
        return fechaenviosap;
    }

    public void setFechaenviosap(Date fechaenviosap) {
        this.fechaenviosap = fechaenviosap;
    }

    public String getEstadosap() {
        return estadosap;
    }

    public void setEstadosap(String estadosap) {
        this.estadosap = estadosap;
    }

    public Date getPeriodofacturacion() {
        return periodofacturacion;
    }

    public void setPeriodofacturacion(Date periodofacturacion) {
        this.periodofacturacion = periodofacturacion;
    }

    public Bonomodis getBonomodis() {
        return bonomodis;
    }

    public void setBonomodis(Bonomodis bonomodis) {
        this.bonomodis = bonomodis;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public ServiciosMante getServiciosMante() {
        return serviciosMante;
    }

    public void setServiciosMante(ServiciosMante serviciosMante) {
        this.serviciosMante = serviciosMante;
    }

    @XmlTransient
    public List<IncidenciaParte> getIncidenciaParteList() {
        return incidenciaParteList;
    }

    public void setIncidenciaParteList(List<IncidenciaParte> incidenciaParteList) {
        this.incidenciaParteList = incidenciaParteList;
    }

    @XmlTransient
    public List<Presupuestos> getPresupuestosList() {
        return presupuestosList;
    }

    public void setPresupuestosList(List<Presupuestos> presupuestosList) {
        this.presupuestosList = presupuestosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpeticion != null ? idpeticion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Peticiones)) {
            return false;
        }
        Peticiones other = (Peticiones) object;
        if ((this.idpeticion == null && other.idpeticion != null) || (this.idpeticion != null && !this.idpeticion.equals(other.idpeticion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Peticiones[ idpeticion=" + idpeticion + " ]";
    }
    
}
