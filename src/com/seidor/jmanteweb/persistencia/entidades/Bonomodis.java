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
import javax.persistence.OneToOne;
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
@Table(name = "BONOMODIS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bonomodis.findAll", query = "SELECT b FROM Bonomodis b")
    , @NamedQuery(name = "Bonomodis.findByIdPeticion", query = "SELECT b FROM Bonomodis b WHERE b.idPeticion = :idPeticion")
    , @NamedQuery(name = "Bonomodis.findByIniciobono", query = "SELECT b FROM Bonomodis b WHERE b.iniciobono = :iniciobono")
    , @NamedQuery(name = "Bonomodis.findByFinbono", query = "SELECT b FROM Bonomodis b WHERE b.finbono = :finbono")
    , @NamedQuery(name = "Bonomodis.findByTotalHoras", query = "SELECT b FROM Bonomodis b WHERE b.totalHoras = :totalHoras")
    , @NamedQuery(name = "Bonomodis.findByHorasHechas", query = "SELECT b FROM Bonomodis b WHERE b.horasHechas = :horasHechas")})
public class Bonomodis implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PETICION")
    private BigDecimal idPeticion;
    @Basic(optional = false)
    @Column(name = "INICIOBONO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iniciobono;
    @Basic(optional = false)
    @Column(name = "FINBONO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finbono;
    @Basic(optional = false)
    @Column(name = "TOTAL_HORAS")
    private BigInteger totalHoras;
    @Basic(optional = false)
    @Column(name = "HORAS_HECHAS")
    private BigInteger horasHechas;
    @OneToMany(mappedBy = "bonomodisPadre")
    private List<Bonomodis> bonomodisList;
    @JoinColumn(name = "BONOMODIS_PADRE", referencedColumnName = "ID_PETICION")
    @ManyToOne
    private Bonomodis bonomodisPadre;
    @JoinColumn(name = "ID_PETICION", referencedColumnName = "IDPETICION", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Peticiones peticiones;
    @OneToMany(mappedBy = "bonomodis")
    private List<InfoModi> infoModiList;
    @OneToMany(mappedBy = "idBonomodisActivo")
    private List<Clientes> clientesList;

    public Bonomodis() {
    }

    public Bonomodis(BigDecimal idPeticion) {
        this.idPeticion = idPeticion;
    }

    public Bonomodis(BigDecimal idPeticion, Date iniciobono, Date finbono, BigInteger totalHoras, BigInteger horasHechas) {
        this.idPeticion = idPeticion;
        this.iniciobono = iniciobono;
        this.finbono = finbono;
        this.totalHoras = totalHoras;
        this.horasHechas = horasHechas;
    }

    public BigDecimal getIdPeticion() {
        return idPeticion;
    }

    public void setIdPeticion(BigDecimal idPeticion) {
        this.idPeticion = idPeticion;
    }

    public Date getIniciobono() {
        return iniciobono;
    }

    public void setIniciobono(Date iniciobono) {
        this.iniciobono = iniciobono;
    }

    public Date getFinbono() {
        return finbono;
    }

    public void setFinbono(Date finbono) {
        this.finbono = finbono;
    }

    public BigInteger getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(BigInteger totalHoras) {
        this.totalHoras = totalHoras;
    }

    public BigInteger getHorasHechas() {
        return horasHechas;
    }

    public void setHorasHechas(BigInteger horasHechas) {
        this.horasHechas = horasHechas;
    }

    @XmlTransient
    public List<Bonomodis> getBonomodisList() {
        return bonomodisList;
    }

    public void setBonomodisList(List<Bonomodis> bonomodisList) {
        this.bonomodisList = bonomodisList;
    }

    public Bonomodis getBonomodisPadre() {
        return bonomodisPadre;
    }

    public void setBonomodisPadre(Bonomodis bonomodisPadre) {
        this.bonomodisPadre = bonomodisPadre;
    }

    public Peticiones getPeticiones() {
        return peticiones;
    }

    public void setPeticiones(Peticiones peticiones) {
        this.peticiones = peticiones;
    }

    @XmlTransient
    public List<InfoModi> getInfoModiList() {
        return infoModiList;
    }

    public void setInfoModiList(List<InfoModi> infoModiList) {
        this.infoModiList = infoModiList;
    }

    @XmlTransient
    public List<Clientes> getClientesList() {
        return clientesList;
    }

    public void setClientesList(List<Clientes> clientesList) {
        this.clientesList = clientesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPeticion != null ? idPeticion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bonomodis)) {
            return false;
        }
        Bonomodis other = (Bonomodis) object;
        if ((this.idPeticion == null && other.idPeticion != null) || (this.idPeticion != null && !this.idPeticion.equals(other.idPeticion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Bonomodis[ idPeticion=" + idPeticion + " ]";
    }
    
}
