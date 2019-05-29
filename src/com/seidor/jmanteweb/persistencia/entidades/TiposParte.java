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
@Table(name = "TIPOS_PARTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposParte.findAll", query = "SELECT t FROM TiposParte t")
    , @NamedQuery(name = "TiposParte.findByCodigo", query = "SELECT t FROM TiposParte t WHERE t.codigo = :codigo")
    , @NamedQuery(name = "TiposParte.findByDescripcion", query = "SELECT t FROM TiposParte t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "TiposParte.findByVersion", query = "SELECT t FROM TiposParte t WHERE t.version = :version")
    , @NamedQuery(name = "TiposParte.findByFactasistencia", query = "SELECT t FROM TiposParte t WHERE t.factasistencia = :factasistencia")
    , @NamedQuery(name = "TiposParte.findByVisible", query = "SELECT t FROM TiposParte t WHERE t.visible = :visible")
    , @NamedQuery(name = "TiposParte.findByVisibleCliente", query = "SELECT t FROM TiposParte t WHERE t.visibleCliente = :visibleCliente")})
public class TiposParte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private String codigo;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @Column(name = "FACTASISTENCIA")
    private Short factasistencia;
    @Column(name = "VISIBLE")
    private Short visible;
    @Column(name = "VISIBLE_CLIENTE")
    private Short visibleCliente;
    @OneToMany(mappedBy = "tipo")
    private List<Partes> partesList;

    public TiposParte() {
    }

    public TiposParte(String codigo) {
        this.codigo = codigo;
    }

    public TiposParte(String codigo, BigInteger version) {
        this.codigo = codigo;
        this.version = version;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public Short getFactasistencia() {
        return factasistencia;
    }

    public void setFactasistencia(Short factasistencia) {
        this.factasistencia = factasistencia;
    }

    public Short getVisible() {
        return visible;
    }

    public void setVisible(Short visible) {
        this.visible = visible;
    }

    public Short getVisibleCliente() {
        return visibleCliente;
    }

    public void setVisibleCliente(Short visibleCliente) {
        this.visibleCliente = visibleCliente;
    }

    @XmlTransient
    public List<Partes> getPartesList() {
        return partesList;
    }

    public void setPartesList(List<Partes> partesList) {
        this.partesList = partesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TiposParte)) {
            return false;
        }
        TiposParte other = (TiposParte) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.TiposParte[ codigo=" + codigo + " ]";
    }
    
}
