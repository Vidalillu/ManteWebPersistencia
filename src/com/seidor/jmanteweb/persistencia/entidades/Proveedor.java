/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AMARTEL
 */
@Entity
@Table(name = "PROVEEDOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proveedor.findAll", query = "SELECT p FROM Proveedor p")
    , @NamedQuery(name = "Proveedor.findByNombre", query = "SELECT p FROM Proveedor p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Proveedor.findByTelefono", query = "SELECT p FROM Proveedor p WHERE p.telefono = :telefono")
    , @NamedQuery(name = "Proveedor.findByMail", query = "SELECT p FROM Proveedor p WHERE p.mail = :mail")
    , @NamedQuery(name = "Proveedor.findByVersion", query = "SELECT p FROM Proveedor p WHERE p.version = :version")
    , @NamedQuery(name = "Proveedor.findByCargo", query = "SELECT p FROM Proveedor p WHERE p.cargo = :cargo")
    , @NamedQuery(name = "Proveedor.findByComercial", query = "SELECT p FROM Proveedor p WHERE p.comercial = :comercial")
    , @NamedQuery(name = "Proveedor.findByResponsableproyecto", query = "SELECT p FROM Proveedor p WHERE p.responsableproyecto = :responsableproyecto")})
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "MAIL")
    private String mail;
    @Column(name = "VERSION")
    private BigInteger version;
    @Column(name = "CARGO")
    private String cargo;
    @Basic(optional = false)
    @Column(name = "COMERCIAL")
    private BigInteger comercial;
    @Basic(optional = false)
    @Column(name = "RESPONSABLEPROYECTO")
    private BigInteger responsableproyecto;

    public Proveedor() {
    }

    public Proveedor(String nombre) {
        this.nombre = nombre;
    }

    public Proveedor(String nombre, BigInteger comercial, BigInteger responsableproyecto) {
        this.nombre = nombre;
        this.comercial = comercial;
        this.responsableproyecto = responsableproyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public BigInteger getComercial() {
        return comercial;
    }

    public void setComercial(BigInteger comercial) {
        this.comercial = comercial;
    }

    public BigInteger getResponsableproyecto() {
        return responsableproyecto;
    }

    public void setResponsableproyecto(BigInteger responsableproyecto) {
        this.responsableproyecto = responsableproyecto;
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
        if (!(object instanceof Proveedor)) {
            return false;
        }
        Proveedor other = (Proveedor) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Proveedor[ nombre=" + nombre + " ]";
    }
    
}
