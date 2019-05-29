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
import javax.persistence.Lob;
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
@Table(name = "PRESUPUESTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Presupuestos.findAll", query = "SELECT p FROM Presupuestos p")
    , @NamedQuery(name = "Presupuestos.findByNum", query = "SELECT p FROM Presupuestos p WHERE p.num = :num")
    , @NamedQuery(name = "Presupuestos.findByFechaAceptacion", query = "SELECT p FROM Presupuestos p WHERE p.fechaAceptacion = :fechaAceptacion")
    , @NamedQuery(name = "Presupuestos.findBySolicitante", query = "SELECT p FROM Presupuestos p WHERE p.solicitante = :solicitante")
    , @NamedQuery(name = "Presupuestos.findByFechaRealizacion", query = "SELECT p FROM Presupuestos p WHERE p.fechaRealizacion = :fechaRealizacion")
    , @NamedQuery(name = "Presupuestos.findByTitulo", query = "SELECT p FROM Presupuestos p WHERE p.titulo = :titulo")})
public class Presupuestos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "NUM")
    private BigDecimal num;
    @Column(name = "FECHA_ACEPTACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAceptacion;
    @Column(name = "SOLICITANTE")
    private BigInteger solicitante;
    @Column(name = "FECHA_REALIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRealizacion;
    @Column(name = "TITULO")
    private String titulo;
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "ID_PETICION", referencedColumnName = "IDPETICION")
    @ManyToOne
    private Peticiones idPeticion;
    @JoinColumn(name = "USUARIO_MANTE", referencedColumnName = "USUARIO")
    @ManyToOne(optional = false)
    private Usuario usuarioMante;
    @OneToMany(mappedBy = "presupuesto")
    private List<InfoModi> infoModiList;

    public Presupuestos() {
    }

    public Presupuestos(BigDecimal num) {
        this.num = num;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public Date getFechaAceptacion() {
        return fechaAceptacion;
    }

    public void setFechaAceptacion(Date fechaAceptacion) {
        this.fechaAceptacion = fechaAceptacion;
    }

    public BigInteger getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(BigInteger solicitante) {
        this.solicitante = solicitante;
    }

    public Date getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(Date fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Peticiones getIdPeticion() {
        return idPeticion;
    }

    public void setIdPeticion(Peticiones idPeticion) {
        this.idPeticion = idPeticion;
    }

    public Usuario getUsuarioMante() {
        return usuarioMante;
    }

    public void setUsuarioMante(Usuario usuarioMante) {
        this.usuarioMante = usuarioMante;
    }

    @XmlTransient
    public List<InfoModi> getInfoModiList() {
        return infoModiList;
    }

    public void setInfoModiList(List<InfoModi> infoModiList) {
        this.infoModiList = infoModiList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (num != null ? num.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Presupuestos)) {
            return false;
        }
        Presupuestos other = (Presupuestos) object;
        if ((this.num == null && other.num != null) || (this.num != null && !this.num.equals(other.num))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Presupuestos[ num=" + num + " ]";
    }
    
}
