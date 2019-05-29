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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "RESPONSABILIDAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Responsabilidad.findAll", query = "SELECT r FROM Responsabilidad r")
    , @NamedQuery(name = "Responsabilidad.findByCliente", query = "SELECT r FROM Responsabilidad r WHERE r.responsabilidadPK.cliente = :cliente")
    , @NamedQuery(name = "Responsabilidad.findByUsuarioMante", query = "SELECT r FROM Responsabilidad r WHERE r.responsabilidadPK.usuarioMante = :usuarioMante")
    , @NamedQuery(name = "Responsabilidad.findByGrado", query = "SELECT r FROM Responsabilidad r WHERE r.grado = :grado")
    , @NamedQuery(name = "Responsabilidad.findByVersion", query = "SELECT r FROM Responsabilidad r WHERE r.version = :version")})
public class Responsabilidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ResponsabilidadPK responsabilidadPK;
    @Basic(optional = false)
    @Column(name = "GRADO")
    private BigInteger grado;
    @Column(name = "VERSION")
    private BigInteger version;
    @JoinColumn(name = "CLIENTE", referencedColumnName = "CLIENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Clientes clientes;
    @JoinColumn(name = "USUARIO_MANTE", referencedColumnName = "USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Responsabilidad() {
    }

    public Responsabilidad(ResponsabilidadPK responsabilidadPK) {
        this.responsabilidadPK = responsabilidadPK;
    }

    public Responsabilidad(ResponsabilidadPK responsabilidadPK, BigInteger grado) {
        this.responsabilidadPK = responsabilidadPK;
        this.grado = grado;
    }

    public Responsabilidad(String cliente, String usuarioMante) {
        this.responsabilidadPK = new ResponsabilidadPK(cliente, usuarioMante);
    }

    public ResponsabilidadPK getResponsabilidadPK() {
        return responsabilidadPK;
    }

    public void setResponsabilidadPK(ResponsabilidadPK responsabilidadPK) {
        this.responsabilidadPK = responsabilidadPK;
    }

    public BigInteger getGrado() {
        return grado;
    }

    public void setGrado(BigInteger grado) {
        this.grado = grado;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (responsabilidadPK != null ? responsabilidadPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Responsabilidad)) {
            return false;
        }
        Responsabilidad other = (Responsabilidad) object;
        if ((this.responsabilidadPK == null && other.responsabilidadPK != null) || (this.responsabilidadPK != null && !this.responsabilidadPK.equals(other.responsabilidadPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Responsabilidad[ responsabilidadPK=" + responsabilidadPK + " ]";
    }
    
}
