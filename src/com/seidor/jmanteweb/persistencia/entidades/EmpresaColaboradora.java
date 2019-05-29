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
import javax.persistence.CascadeType;
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
@Table(name = "EMPRESA_COLABORADORA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpresaColaboradora.findAll", query = "SELECT e FROM EmpresaColaboradora e")
    , @NamedQuery(name = "EmpresaColaboradora.findByNombre", query = "SELECT e FROM EmpresaColaboradora e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "EmpresaColaboradora.findByNombrealternativo", query = "SELECT e FROM EmpresaColaboradora e WHERE e.nombrealternativo = :nombrealternativo")
    , @NamedQuery(name = "EmpresaColaboradora.findByDireccion", query = "SELECT e FROM EmpresaColaboradora e WHERE e.direccion = :direccion")
    , @NamedQuery(name = "EmpresaColaboradora.findByCodpostal", query = "SELECT e FROM EmpresaColaboradora e WHERE e.codpostal = :codpostal")
    , @NamedQuery(name = "EmpresaColaboradora.findByPoblacion", query = "SELECT e FROM EmpresaColaboradora e WHERE e.poblacion = :poblacion")
    , @NamedQuery(name = "EmpresaColaboradora.findByProvincia", query = "SELECT e FROM EmpresaColaboradora e WHERE e.provincia = :provincia")
    , @NamedQuery(name = "EmpresaColaboradora.findByPais", query = "SELECT e FROM EmpresaColaboradora e WHERE e.pais = :pais")
    , @NamedQuery(name = "EmpresaColaboradora.findByTelefono", query = "SELECT e FROM EmpresaColaboradora e WHERE e.telefono = :telefono")
    , @NamedQuery(name = "EmpresaColaboradora.findByFax", query = "SELECT e FROM EmpresaColaboradora e WHERE e.fax = :fax")
    , @NamedQuery(name = "EmpresaColaboradora.findByVersion", query = "SELECT e FROM EmpresaColaboradora e WHERE e.version = :version")
    , @NamedQuery(name = "EmpresaColaboradora.findByWeb", query = "SELECT e FROM EmpresaColaboradora e WHERE e.web = :web")})
public class EmpresaColaboradora implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "NOMBREALTERNATIVO")
    private String nombrealternativo;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "CODPOSTAL")
    private String codpostal;
    @Column(name = "POBLACION")
    private String poblacion;
    @Column(name = "PROVINCIA")
    private String provincia;
    @Column(name = "PAIS")
    private String pais;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "FAX")
    private String fax;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @Column(name = "WEB")
    private String web;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresaColaboradora")
    private List<EmpresaProveedor> empresaProveedorList;
    @OneToMany(mappedBy = "tipoCliente")
    private List<Clientes> clientesList;

    public EmpresaColaboradora() {
    }

    public EmpresaColaboradora(String nombre) {
        this.nombre = nombre;
    }

    public EmpresaColaboradora(String nombre, BigInteger version) {
        this.nombre = nombre;
        this.version = version;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombrealternativo() {
        return nombrealternativo;
    }

    public void setNombrealternativo(String nombrealternativo) {
        this.nombrealternativo = nombrealternativo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodpostal() {
        return codpostal;
    }

    public void setCodpostal(String codpostal) {
        this.codpostal = codpostal;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    @XmlTransient
    public List<EmpresaProveedor> getEmpresaProveedorList() {
        return empresaProveedorList;
    }

    public void setEmpresaProveedorList(List<EmpresaProveedor> empresaProveedorList) {
        this.empresaProveedorList = empresaProveedorList;
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
        hash += (nombre != null ? nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpresaColaboradora)) {
            return false;
        }
        EmpresaColaboradora other = (EmpresaColaboradora) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.EmpresaColaboradora[ nombre=" + nombre + " ]";
    }
    
}
