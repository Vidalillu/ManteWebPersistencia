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
@Table(name = "USUARIO_TAREA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioTarea.findAll", query = "SELECT u FROM UsuarioTarea u")
    , @NamedQuery(name = "UsuarioTarea.findByVersion", query = "SELECT u FROM UsuarioTarea u WHERE u.version = :version")
    , @NamedQuery(name = "UsuarioTarea.findById", query = "SELECT u FROM UsuarioTarea u WHERE u.id = :id")})
public class UsuarioTarea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "VERSION")
    private BigInteger version;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @JoinColumn(name = "IDTAREA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Tarea idtarea;
    @JoinColumn(name = "USUARIOASIGNADO", referencedColumnName = "USUARIO")
    @ManyToOne(optional = false)
    private Usuario usuarioasignado;

    public UsuarioTarea() {
    }

    public UsuarioTarea(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Tarea getIdtarea() {
        return idtarea;
    }

    public void setIdtarea(Tarea idtarea) {
        this.idtarea = idtarea;
    }

    public Usuario getUsuarioasignado() {
        return usuarioasignado;
    }

    public void setUsuarioasignado(Usuario usuarioasignado) {
        this.usuarioasignado = usuarioasignado;
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
        if (!(object instanceof UsuarioTarea)) {
            return false;
        }
        UsuarioTarea other = (UsuarioTarea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.UsuarioTarea[ id=" + id + " ]";
    }
    
}
