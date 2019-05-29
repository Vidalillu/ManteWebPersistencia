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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AMARTEL
 */
@Entity
@Table(name = "REGISTROPARTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Registroparte.findAll", query = "SELECT r FROM Registroparte r")
    , @NamedQuery(name = "Registroparte.findById", query = "SELECT r FROM Registroparte r WHERE r.id = :id")
    , @NamedQuery(name = "Registroparte.findByFecha", query = "SELECT r FROM Registroparte r WHERE r.fecha = :fecha")
    , @NamedQuery(name = "Registroparte.findByVersion", query = "SELECT r FROM Registroparte r WHERE r.version = :version")})
public class Registroparte implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Lob
    @Column(name = "REGISTRO")
    private String registro;
    @Column(name = "VERSION")
    private BigInteger version;
    @JoinColumn(name = "NUM_PARTE", referencedColumnName = "NUM_PARTE")
    @ManyToOne
    private Partes numParte;
    @JoinColumn(name = "USUARIO", referencedColumnName = "USUARIO")
    @ManyToOne
    private Usuario usuario;

    public Registroparte() {
    }

    public Registroparte(BigDecimal id) {
        this.id = id;
    }

    public Registroparte(BigDecimal id, Date fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public Partes getNumParte() {
        return numParte;
    }

    public void setNumParte(Partes numParte) {
        this.numParte = numParte;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registroparte)) {
            return false;
        }
        Registroparte other = (Registroparte) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Registroparte[ id=" + id + " ]";
    }
    
}
