/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "CAMBIOPROGRAMA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cambioprograma.findAll", query = "SELECT c FROM Cambioprograma c")
    , @NamedQuery(name = "Cambioprograma.findByCampo", query = "SELECT c FROM Cambioprograma c WHERE c.campo = :campo")
    , @NamedQuery(name = "Cambioprograma.findByValorold", query = "SELECT c FROM Cambioprograma c WHERE c.valorold = :valorold")
    , @NamedQuery(name = "Cambioprograma.findByValornew", query = "SELECT c FROM Cambioprograma c WHERE c.valornew = :valornew")
    , @NamedQuery(name = "Cambioprograma.findByFechacambio", query = "SELECT c FROM Cambioprograma c WHERE c.fechacambio = :fechacambio")
    , @NamedQuery(name = "Cambioprograma.findByVersion", query = "SELECT c FROM Cambioprograma c WHERE c.version = :version")
    , @NamedQuery(name = "Cambioprograma.findByTipo", query = "SELECT c FROM Cambioprograma c WHERE c.tipo = :tipo")
    , @NamedQuery(name = "Cambioprograma.findByObjeto", query = "SELECT c FROM Cambioprograma c WHERE c.objeto = :objeto")
    , @NamedQuery(name = "Cambioprograma.findByBaseform", query = "SELECT c FROM Cambioprograma c WHERE c.baseform = :baseform")
    , @NamedQuery(name = "Cambioprograma.findByClave", query = "SELECT c FROM Cambioprograma c WHERE c.clave = :clave")
    , @NamedQuery(name = "Cambioprograma.findByIdcambio", query = "SELECT c FROM Cambioprograma c WHERE c.idcambio = :idcambio")})
public class Cambioprograma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "CAMPO")
    private String campo;
    @Column(name = "VALOROLD")
    private String valorold;
    @Column(name = "VALORNEW")
    private String valornew;
    @Basic(optional = false)
    @Column(name = "FECHACAMBIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacambio;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "OBJETO")
    private String objeto;
    @Column(name = "BASEFORM")
    private String baseform;
    @Column(name = "CLAVE")
    private String clave;
    @Id
    @Basic(optional = false)
    @Column(name = "IDCAMBIO")
    private String idcambio;
    @JoinColumn(name = "PANTALLA", referencedColumnName = "NOMBRE")
    @ManyToOne(optional = false)
    private Componentemenu pantalla;
    @JoinColumn(name = "USUARIO", referencedColumnName = "USUARIO")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Cambioprograma() {
    }

    public Cambioprograma(String idcambio) {
        this.idcambio = idcambio;
    }

    public Cambioprograma(String idcambio, Date fechacambio, BigInteger version, String tipo, String objeto) {
        this.idcambio = idcambio;
        this.fechacambio = fechacambio;
        this.version = version;
        this.tipo = tipo;
        this.objeto = objeto;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getValorold() {
        return valorold;
    }

    public void setValorold(String valorold) {
        this.valorold = valorold;
    }

    public String getValornew() {
        return valornew;
    }

    public void setValornew(String valornew) {
        this.valornew = valornew;
    }

    public Date getFechacambio() {
        return fechacambio;
    }

    public void setFechacambio(Date fechacambio) {
        this.fechacambio = fechacambio;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public String getBaseform() {
        return baseform;
    }

    public void setBaseform(String baseform) {
        this.baseform = baseform;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getIdcambio() {
        return idcambio;
    }

    public void setIdcambio(String idcambio) {
        this.idcambio = idcambio;
    }

    public Componentemenu getPantalla() {
        return pantalla;
    }

    public void setPantalla(Componentemenu pantalla) {
        this.pantalla = pantalla;
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
        hash += (idcambio != null ? idcambio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cambioprograma)) {
            return false;
        }
        Cambioprograma other = (Cambioprograma) object;
        if ((this.idcambio == null && other.idcambio != null) || (this.idcambio != null && !this.idcambio.equals(other.idcambio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Cambioprograma[ idcambio=" + idcambio + " ]";
    }
    
}
