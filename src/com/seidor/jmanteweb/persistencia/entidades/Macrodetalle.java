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
@Table(name = "MACRODETALLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Macrodetalle.findAll", query = "SELECT m FROM Macrodetalle m")
    , @NamedQuery(name = "Macrodetalle.findByIdmacrodeta", query = "SELECT m FROM Macrodetalle m WHERE m.idmacrodeta = :idmacrodeta")
    , @NamedQuery(name = "Macrodetalle.findByTipo", query = "SELECT m FROM Macrodetalle m WHERE m.tipo = :tipo")
    , @NamedQuery(name = "Macrodetalle.findByAccion", query = "SELECT m FROM Macrodetalle m WHERE m.accion = :accion")
    , @NamedQuery(name = "Macrodetalle.findByVersion", query = "SELECT m FROM Macrodetalle m WHERE m.version = :version")
    , @NamedQuery(name = "Macrodetalle.findByComponente", query = "SELECT m FROM Macrodetalle m WHERE m.componente = :componente")
    , @NamedQuery(name = "Macrodetalle.findByOrden", query = "SELECT m FROM Macrodetalle m WHERE m.orden = :orden")
    , @NamedQuery(name = "Macrodetalle.findByCampo", query = "SELECT m FROM Macrodetalle m WHERE m.campo = :campo")
    , @NamedQuery(name = "Macrodetalle.findByTipodato", query = "SELECT m FROM Macrodetalle m WHERE m.tipodato = :tipodato")
    , @NamedQuery(name = "Macrodetalle.findByNulos", query = "SELECT m FROM Macrodetalle m WHERE m.nulos = :nulos")
    , @NamedQuery(name = "Macrodetalle.findByFormula", query = "SELECT m FROM Macrodetalle m WHERE m.formula = :formula")})
public class Macrodetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDMACRODETA")
    private BigDecimal idmacrodeta;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "ACCION")
    private String accion;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @Column(name = "COMPONENTE")
    private String componente;
    @Basic(optional = false)
    @Column(name = "ORDEN")
    private short orden;
    @Column(name = "CAMPO")
    private String campo;
    @Column(name = "TIPODATO")
    private String tipodato;
    @Basic(optional = false)
    @Column(name = "NULOS")
    private short nulos;
    @Column(name = "FORMULA")
    private String formula;
    @JoinColumn(name = "MACRO", referencedColumnName = "MACRO")
    @ManyToOne(optional = false)
    private Macro macro;

    public Macrodetalle() {
    }

    public Macrodetalle(BigDecimal idmacrodeta) {
        this.idmacrodeta = idmacrodeta;
    }

    public Macrodetalle(BigDecimal idmacrodeta, String tipo, BigInteger version, short orden, short nulos) {
        this.idmacrodeta = idmacrodeta;
        this.tipo = tipo;
        this.version = version;
        this.orden = orden;
        this.nulos = nulos;
    }

    public BigDecimal getIdmacrodeta() {
        return idmacrodeta;
    }

    public void setIdmacrodeta(BigDecimal idmacrodeta) {
        this.idmacrodeta = idmacrodeta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public short getOrden() {
        return orden;
    }

    public void setOrden(short orden) {
        this.orden = orden;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getTipodato() {
        return tipodato;
    }

    public void setTipodato(String tipodato) {
        this.tipodato = tipodato;
    }

    public short getNulos() {
        return nulos;
    }

    public void setNulos(short nulos) {
        this.nulos = nulos;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Macro getMacro() {
        return macro;
    }

    public void setMacro(Macro macro) {
        this.macro = macro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmacrodeta != null ? idmacrodeta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Macrodetalle)) {
            return false;
        }
        Macrodetalle other = (Macrodetalle) object;
        if ((this.idmacrodeta == null && other.idmacrodeta != null) || (this.idmacrodeta != null && !this.idmacrodeta.equals(other.idmacrodeta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Macrodetalle[ idmacrodeta=" + idmacrodeta + " ]";
    }
    
}
