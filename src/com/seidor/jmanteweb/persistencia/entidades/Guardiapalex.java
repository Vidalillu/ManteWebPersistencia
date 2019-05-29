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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "GUARDIAPALEX")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Guardiapalex.findAll", query = "SELECT g FROM Guardiapalex g")
    , @NamedQuery(name = "Guardiapalex.findByFecha", query = "SELECT g FROM Guardiapalex g WHERE g.fecha = :fecha")
    , @NamedQuery(name = "Guardiapalex.findByTipo", query = "SELECT g FROM Guardiapalex g WHERE g.tipo = :tipo")
    , @NamedQuery(name = "Guardiapalex.findByObservaciones", query = "SELECT g FROM Guardiapalex g WHERE g.observaciones = :observaciones")
    , @NamedQuery(name = "Guardiapalex.findByVersion", query = "SELECT g FROM Guardiapalex g WHERE g.version = :version")
    , @NamedQuery(name = "Guardiapalex.findByIdguardia", query = "SELECT g FROM Guardiapalex g WHERE g.idguardia = :idguardia")
    , @NamedQuery(name = "Guardiapalex.findByNotificado", query = "SELECT g FROM Guardiapalex g WHERE g.notificado = :notificado")
    , @NamedQuery(name = "Guardiapalex.findByPrecio", query = "SELECT g FROM Guardiapalex g WHERE g.precio = :precio")})
public class Guardiapalex implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDGUARDIA")
    private BigDecimal idguardia;
    @Column(name = "NOTIFICADO")
    private BigInteger notificado;
    @Basic(optional = false)
    @Column(name = "PRECIO")
    private int precio;
    @JoinColumn(name = "IDTARIFA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Tarifapalex idtarifa;
    @JoinColumn(name = "TITULAR", referencedColumnName = "USUARIO")
    @ManyToOne
    private Usuario titular;
    @JoinColumn(name = "USUARIO_REAL", referencedColumnName = "USUARIO")
    @ManyToOne
    private Usuario usuarioReal;
    @OneToMany(mappedBy = "guardiapalex")
    private List<Partes> partesList;

    public Guardiapalex() {
    }

    public Guardiapalex(BigDecimal idguardia) {
        this.idguardia = idguardia;
    }

    public Guardiapalex(BigDecimal idguardia, String tipo, BigInteger version, int precio) {
        this.idguardia = idguardia;
        this.tipo = tipo;
        this.version = version;
        this.precio = precio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public BigDecimal getIdguardia() {
        return idguardia;
    }

    public void setIdguardia(BigDecimal idguardia) {
        this.idguardia = idguardia;
    }

    public BigInteger getNotificado() {
        return notificado;
    }

    public void setNotificado(BigInteger notificado) {
        this.notificado = notificado;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Tarifapalex getIdtarifa() {
        return idtarifa;
    }

    public void setIdtarifa(Tarifapalex idtarifa) {
        this.idtarifa = idtarifa;
    }

    public Usuario getTitular() {
        return titular;
    }

    public void setTitular(Usuario titular) {
        this.titular = titular;
    }

    public Usuario getUsuarioReal() {
        return usuarioReal;
    }

    public void setUsuarioReal(Usuario usuarioReal) {
        this.usuarioReal = usuarioReal;
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
        hash += (idguardia != null ? idguardia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Guardiapalex)) {
            return false;
        }
        Guardiapalex other = (Guardiapalex) object;
        if ((this.idguardia == null && other.idguardia != null) || (this.idguardia != null && !this.idguardia.equals(other.idguardia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Guardiapalex[ idguardia=" + idguardia + " ]";
    }
    
}
