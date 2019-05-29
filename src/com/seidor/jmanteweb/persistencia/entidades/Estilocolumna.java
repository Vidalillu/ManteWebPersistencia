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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AMARTEL
 */
@Entity
@Table(name = "ESTILOCOLUMNA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estilocolumna.findAll", query = "SELECT e FROM Estilocolumna e")
    , @NamedQuery(name = "Estilocolumna.findById", query = "SELECT e FROM Estilocolumna e WHERE e.id = :id")
    , @NamedQuery(name = "Estilocolumna.findByNombregrid", query = "SELECT e FROM Estilocolumna e WHERE e.nombregrid = :nombregrid")
    , @NamedQuery(name = "Estilocolumna.findByCabecera", query = "SELECT e FROM Estilocolumna e WHERE e.cabecera = :cabecera")
    , @NamedQuery(name = "Estilocolumna.findByNegrita", query = "SELECT e FROM Estilocolumna e WHERE e.negrita = :negrita")
    , @NamedQuery(name = "Estilocolumna.findByItalica", query = "SELECT e FROM Estilocolumna e WHERE e.italica = :italica")
    , @NamedQuery(name = "Estilocolumna.findByColorletra", query = "SELECT e FROM Estilocolumna e WHERE e.colorletra = :colorletra")
    , @NamedQuery(name = "Estilocolumna.findByColorfondo", query = "SELECT e FROM Estilocolumna e WHERE e.colorfondo = :colorfondo")
    , @NamedQuery(name = "Estilocolumna.findByUsuario", query = "SELECT e FROM Estilocolumna e WHERE e.usuario = :usuario")
    , @NamedQuery(name = "Estilocolumna.findByVersion", query = "SELECT e FROM Estilocolumna e WHERE e.version = :version")})
public class Estilocolumna implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "NOMBREGRID")
    private String nombregrid;
    @Basic(optional = false)
    @Column(name = "CABECERA")
    private String cabecera;
    @Basic(optional = false)
    @Column(name = "NEGRITA")
    private BigInteger negrita;
    @Basic(optional = false)
    @Column(name = "ITALICA")
    private BigInteger italica;
    @Column(name = "COLORLETRA")
    private String colorletra;
    @Column(name = "COLORFONDO")
    private String colorfondo;
    @Basic(optional = false)
    @Column(name = "USUARIO")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;

    public Estilocolumna() {
    }

    public Estilocolumna(BigDecimal id) {
        this.id = id;
    }

    public Estilocolumna(BigDecimal id, String nombregrid, String cabecera, BigInteger negrita, BigInteger italica, String usuario, BigInteger version) {
        this.id = id;
        this.nombregrid = nombregrid;
        this.cabecera = cabecera;
        this.negrita = negrita;
        this.italica = italica;
        this.usuario = usuario;
        this.version = version;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNombregrid() {
        return nombregrid;
    }

    public void setNombregrid(String nombregrid) {
        this.nombregrid = nombregrid;
    }

    public String getCabecera() {
        return cabecera;
    }

    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    public BigInteger getNegrita() {
        return negrita;
    }

    public void setNegrita(BigInteger negrita) {
        this.negrita = negrita;
    }

    public BigInteger getItalica() {
        return italica;
    }

    public void setItalica(BigInteger italica) {
        this.italica = italica;
    }

    public String getColorletra() {
        return colorletra;
    }

    public void setColorletra(String colorletra) {
        this.colorletra = colorletra;
    }

    public String getColorfondo() {
        return colorfondo;
    }

    public void setColorfondo(String colorfondo) {
        this.colorfondo = colorfondo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
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
        if (!(object instanceof Estilocolumna)) {
            return false;
        }
        Estilocolumna other = (Estilocolumna) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Estilocolumna[ id=" + id + " ]";
    }
    
}
