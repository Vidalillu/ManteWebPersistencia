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
@Table(name = "PARAMETROS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametros.findAll", query = "SELECT p FROM Parametros p")
    , @NamedQuery(name = "Parametros.findByParametro", query = "SELECT p FROM Parametros p WHERE p.parametro = :parametro")
    , @NamedQuery(name = "Parametros.findByDato", query = "SELECT p FROM Parametros p WHERE p.dato = :dato")
    , @NamedQuery(name = "Parametros.findByDescripcion", query = "SELECT p FROM Parametros p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Parametros.findByCodigo", query = "SELECT p FROM Parametros p WHERE p.codigo = :codigo")
    , @NamedQuery(name = "Parametros.findByVersion", query = "SELECT p FROM Parametros p WHERE p.version = :version")})
public class Parametros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PARAMETRO")
    private String parametro;
    @Column(name = "DATO")
    private String dato;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;

    public Parametros() {
    }

    public Parametros(String parametro) {
        this.parametro = parametro;
    }

    public Parametros(String parametro, String codigo, BigInteger version) {
        this.parametro = parametro;
        this.codigo = codigo;
        this.version = version;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
        hash += (parametro != null ? parametro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametros)) {
            return false;
        }
        Parametros other = (Parametros) object;
        if ((this.parametro == null && other.parametro != null) || (this.parametro != null && !this.parametro.equals(other.parametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Parametros[ parametro=" + parametro + " ]";
    }
    
}
