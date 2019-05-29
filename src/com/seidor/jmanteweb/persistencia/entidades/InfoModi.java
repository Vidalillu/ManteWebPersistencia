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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "INFO_MODI")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InfoModi.findAll", query = "SELECT i FROM InfoModi i")
    , @NamedQuery(name = "InfoModi.findByIdmodi", query = "SELECT i FROM InfoModi i WHERE i.infoModiPK.idmodi = :idmodi")
    , @NamedQuery(name = "InfoModi.findByCliente", query = "SELECT i FROM InfoModi i WHERE i.infoModiPK.cliente = :cliente")
    , @NamedQuery(name = "InfoModi.findByFechaaplicacion", query = "SELECT i FROM InfoModi i WHERE i.fechaaplicacion = :fechaaplicacion")
    , @NamedQuery(name = "InfoModi.findByVersion", query = "SELECT i FROM InfoModi i WHERE i.version = :version")
    , @NamedQuery(name = "InfoModi.findByHoras", query = "SELECT i FROM InfoModi i WHERE i.horas = :horas")})
public class InfoModi implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InfoModiPK infoModiPK;
    @Basic(optional = false)
    @Column(name = "FECHAAPLICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaaplicacion;
    @Column(name = "VERSION")
    private BigInteger version;
    @Column(name = "HORAS")
    private Integer horas;
    @JoinColumn(name = "BONOMODIS", referencedColumnName = "ID_PETICION")
    @ManyToOne
    private Bonomodis bonomodis;
    @JoinColumn(name = "CLIENTE", referencedColumnName = "CLIENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Clientes clientes;
    @JoinColumn(name = "IDMODI", referencedColumnName = "IDMODI", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Modis modis;
    @JoinColumn(name = "PRESUPUESTO", referencedColumnName = "NUM")
    @ManyToOne
    private Presupuestos presupuesto;
    @JoinColumn(name = "USUARIOAPLICACION", referencedColumnName = "USUARIO")
    @ManyToOne(optional = false)
    private Usuario usuarioaplicacion;

    public InfoModi() {
    }

    public InfoModi(InfoModiPK infoModiPK) {
        this.infoModiPK = infoModiPK;
    }

    public InfoModi(InfoModiPK infoModiPK, Date fechaaplicacion) {
        this.infoModiPK = infoModiPK;
        this.fechaaplicacion = fechaaplicacion;
    }

    public InfoModi(BigInteger idmodi, String cliente) {
        this.infoModiPK = new InfoModiPK(idmodi, cliente);
    }

    public InfoModiPK getInfoModiPK() {
        return infoModiPK;
    }

    public void setInfoModiPK(InfoModiPK infoModiPK) {
        this.infoModiPK = infoModiPK;
    }

    public Date getFechaaplicacion() {
        return fechaaplicacion;
    }

    public void setFechaaplicacion(Date fechaaplicacion) {
        this.fechaaplicacion = fechaaplicacion;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public Bonomodis getBonomodis() {
        return bonomodis;
    }

    public void setBonomodis(Bonomodis bonomodis) {
        this.bonomodis = bonomodis;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public Modis getModis() {
        return modis;
    }

    public void setModis(Modis modis) {
        this.modis = modis;
    }

    public Presupuestos getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Presupuestos presupuesto) {
        this.presupuesto = presupuesto;
    }

    public Usuario getUsuarioaplicacion() {
        return usuarioaplicacion;
    }

    public void setUsuarioaplicacion(Usuario usuarioaplicacion) {
        this.usuarioaplicacion = usuarioaplicacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (infoModiPK != null ? infoModiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InfoModi)) {
            return false;
        }
        InfoModi other = (InfoModi) object;
        if ((this.infoModiPK == null && other.infoModiPK != null) || (this.infoModiPK != null && !this.infoModiPK.equals(other.infoModiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.InfoModi[ infoModiPK=" + infoModiPK + " ]";
    }
    
}
