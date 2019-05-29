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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AMARTEL
 */
@Entity
@Table(name = "CONTACTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contactos.findAll", query = "SELECT c FROM Contactos c")
    , @NamedQuery(name = "Contactos.findByNombre", query = "SELECT c FROM Contactos c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Contactos.findByCargo", query = "SELECT c FROM Contactos c WHERE c.cargo = :cargo")
    , @NamedQuery(name = "Contactos.findByTelefono", query = "SELECT c FROM Contactos c WHERE c.telefono = :telefono")
    , @NamedQuery(name = "Contactos.findByEmail", query = "SELECT c FROM Contactos c WHERE c.email = :email")
    , @NamedQuery(name = "Contactos.findByRecibemailpartes", query = "SELECT c FROM Contactos c WHERE c.recibemailpartes = :recibemailpartes")
    , @NamedQuery(name = "Contactos.findById", query = "SELECT c FROM Contactos c WHERE c.id = :id")
    , @NamedQuery(name = "Contactos.findByVersion", query = "SELECT c FROM Contactos c WHERE c.version = :version")
    , @NamedQuery(name = "Contactos.findByPrioridad", query = "SELECT c FROM Contactos c WHERE c.prioridad = :prioridad")
    , @NamedQuery(name = "Contactos.findByRecibemailseguimiento", query = "SELECT c FROM Contactos c WHERE c.recibemailseguimiento = :recibemailseguimiento")
    , @NamedQuery(name = "Contactos.findByRecibemailrevision", query = "SELECT c FROM Contactos c WHERE c.recibemailrevision = :recibemailrevision")
    , @NamedQuery(name = "Contactos.findByExtension", query = "SELECT c FROM Contactos c WHERE c.extension = :extension")
    , @NamedQuery(name = "Contactos.findByTelefonoaux", query = "SELECT c FROM Contactos c WHERE c.telefonoaux = :telefonoaux")
    , @NamedQuery(name = "Contactos.findByExtensionaux", query = "SELECT c FROM Contactos c WHERE c.extensionaux = :extensionaux")})
public class Contactos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "CARGO")
    private String cargo;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @Column(name = "RECIBEMAILPARTES")
    private BigInteger recibemailpartes;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @Basic(optional = false)
    @Column(name = "PRIORIDAD")
    private short prioridad;
    @Basic(optional = false)
    @Column(name = "RECIBEMAILSEGUIMIENTO")
    private short recibemailseguimiento;
    @Basic(optional = false)
    @Column(name = "RECIBEMAILREVISION")
    private short recibemailrevision;
    @Column(name = "EXTENSION")
    private String extension;
    @Column(name = "TELEFONOAUX")
    private String telefonoaux;
    @Column(name = "EXTENSIONAUX")
    private String extensionaux;
    @JoinColumn(name = "CLIENTE", referencedColumnName = "CLIENTE")
    @ManyToOne(optional = false)
    private Clientes cliente;

    public Contactos() {
    }

    public Contactos(BigDecimal id) {
        this.id = id;
    }

    public Contactos(BigDecimal id, String nombre, BigInteger recibemailpartes, BigInteger version, short prioridad, short recibemailseguimiento, short recibemailrevision) {
        this.id = id;
        this.nombre = nombre;
        this.recibemailpartes = recibemailpartes;
        this.version = version;
        this.prioridad = prioridad;
        this.recibemailseguimiento = recibemailseguimiento;
        this.recibemailrevision = recibemailrevision;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigInteger getRecibemailpartes() {
        return recibemailpartes;
    }

    public void setRecibemailpartes(BigInteger recibemailpartes) {
        this.recibemailpartes = recibemailpartes;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public short getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(short prioridad) {
        this.prioridad = prioridad;
    }

    public short getRecibemailseguimiento() {
        return recibemailseguimiento;
    }

    public void setRecibemailseguimiento(short recibemailseguimiento) {
        this.recibemailseguimiento = recibemailseguimiento;
    }

    public short getRecibemailrevision() {
        return recibemailrevision;
    }

    public void setRecibemailrevision(short recibemailrevision) {
        this.recibemailrevision = recibemailrevision;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getTelefonoaux() {
        return telefonoaux;
    }

    public void setTelefonoaux(String telefonoaux) {
        this.telefonoaux = telefonoaux;
    }

    public String getExtensionaux() {
        return extensionaux;
    }

    public void setExtensionaux(String extensionaux) {
        this.extensionaux = extensionaux;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
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
        if (!(object instanceof Contactos)) {
            return false;
        }
        Contactos other = (Contactos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Contactos[ id=" + id + " ]";
    }
    
}
