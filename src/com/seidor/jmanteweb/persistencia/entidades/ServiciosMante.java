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
@Table(name = "SERVICIOS_MANTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ServiciosMante.findAll", query = "SELECT s FROM ServiciosMante s")
    , @NamedQuery(name = "ServiciosMante.findByTipoMante", query = "SELECT s FROM ServiciosMante s WHERE s.tipoMante = :tipoMante")
    , @NamedQuery(name = "ServiciosMante.findByInicioServicio", query = "SELECT s FROM ServiciosMante s WHERE s.inicioServicio = :inicioServicio")
    , @NamedQuery(name = "ServiciosMante.findByFinServicio", query = "SELECT s FROM ServiciosMante s WHERE s.finServicio = :finServicio")
    , @NamedQuery(name = "ServiciosMante.findByTotalHoras", query = "SELECT s FROM ServiciosMante s WHERE s.totalHoras = :totalHoras")
    , @NamedQuery(name = "ServiciosMante.findByHorasHechas", query = "SELECT s FROM ServiciosMante s WHERE s.horasHechas = :horasHechas")
    , @NamedQuery(name = "ServiciosMante.findByIdPeticion", query = "SELECT s FROM ServiciosMante s WHERE s.idPeticion = :idPeticion")
    , @NamedQuery(name = "ServiciosMante.findByMeses", query = "SELECT s FROM ServiciosMante s WHERE s.meses = :meses")
    , @NamedQuery(name = "ServiciosMante.findByFechaRenovacionOra", query = "SELECT s FROM ServiciosMante s WHERE s.fechaRenovacionOra = :fechaRenovacionOra")
    , @NamedQuery(name = "ServiciosMante.findByMantefindes", query = "SELECT s FROM ServiciosMante s WHERE s.mantefindes = :mantefindes")
    , @NamedQuery(name = "ServiciosMante.findByRenuevaAuto", query = "SELECT s FROM ServiciosMante s WHERE s.renuevaAuto = :renuevaAuto")
    , @NamedQuery(name = "ServiciosMante.findByRenuevanotificado", query = "SELECT s FROM ServiciosMante s WHERE s.renuevanotificado = :renuevanotificado")
    , @NamedQuery(name = "ServiciosMante.findByObservacionhoraria", query = "SELECT s FROM ServiciosMante s WHERE s.observacionhoraria = :observacionhoraria")})
public class ServiciosMante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "TIPO_MANTE")
    private String tipoMante;
    @Basic(optional = false)
    @Column(name = "INICIO_SERVICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inicioServicio;
    @Basic(optional = false)
    @Column(name = "FIN_SERVICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finServicio;
    @Column(name = "TOTAL_HORAS")
    private BigInteger totalHoras;
    @Column(name = "HORAS_HECHAS")
    private BigInteger horasHechas;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PETICION")
    private BigDecimal idPeticion;
    @Basic(optional = false)
    @Column(name = "MESES")
    private BigInteger meses;
    @Column(name = "FECHA_RENOVACION_ORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRenovacionOra;
    @Column(name = "MANTEFINDES")
    private Short mantefindes;
    @Column(name = "RENUEVA_AUTO")
    private Short renuevaAuto;
    @Column(name = "RENUEVANOTIFICADO")
    private Short renuevanotificado;
    @Column(name = "OBSERVACIONHORARIA")
    private String observacionhoraria;
    @JoinColumn(name = "ID_PETICION", referencedColumnName = "IDPETICION", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Peticiones peticiones;
    @OneToMany(mappedBy = "serviciomantePadre")
    private List<ServiciosMante> serviciosManteList;
    @JoinColumn(name = "SERVICIOMANTE_PADRE", referencedColumnName = "ID_PETICION")
    @ManyToOne
    private ServiciosMante serviciomantePadre;
    @JoinColumn(name = "TARIFA", referencedColumnName = "TARIFA")
    @ManyToOne
    private Tarifa tarifa;
    @OneToMany(mappedBy = "idServiciomanteActivo")
    private List<Clientes> clientesList;

    public ServiciosMante() {
    }

    public ServiciosMante(BigDecimal idPeticion) {
        this.idPeticion = idPeticion;
    }

    public ServiciosMante(BigDecimal idPeticion, String tipoMante, Date inicioServicio, Date finServicio, BigInteger meses) {
        this.idPeticion = idPeticion;
        this.tipoMante = tipoMante;
        this.inicioServicio = inicioServicio;
        this.finServicio = finServicio;
        this.meses = meses;
    }

    public String getTipoMante() {
        return tipoMante;
    }

    public void setTipoMante(String tipoMante) {
        this.tipoMante = tipoMante;
    }

    public Date getInicioServicio() {
        return inicioServicio;
    }

    public void setInicioServicio(Date inicioServicio) {
        this.inicioServicio = inicioServicio;
    }

    public Date getFinServicio() {
        return finServicio;
    }

    public void setFinServicio(Date finServicio) {
        this.finServicio = finServicio;
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

    public BigDecimal getIdPeticion() {
        return idPeticion;
    }

    public void setIdPeticion(BigDecimal idPeticion) {
        this.idPeticion = idPeticion;
    }

    public BigInteger getMeses() {
        return meses;
    }

    public void setMeses(BigInteger meses) {
        this.meses = meses;
    }

    public Date getFechaRenovacionOra() {
        return fechaRenovacionOra;
    }

    public void setFechaRenovacionOra(Date fechaRenovacionOra) {
        this.fechaRenovacionOra = fechaRenovacionOra;
    }

    public Short getMantefindes() {
        return mantefindes;
    }

    public void setMantefindes(Short mantefindes) {
        this.mantefindes = mantefindes;
    }

    public Short getRenuevaAuto() {
        return renuevaAuto;
    }

    public void setRenuevaAuto(Short renuevaAuto) {
        this.renuevaAuto = renuevaAuto;
    }

    public Short getRenuevanotificado() {
        return renuevanotificado;
    }

    public void setRenuevanotificado(Short renuevanotificado) {
        this.renuevanotificado = renuevanotificado;
    }

    public String getObservacionhoraria() {
        return observacionhoraria;
    }

    public void setObservacionhoraria(String observacionhoraria) {
        this.observacionhoraria = observacionhoraria;
    }

    public Peticiones getPeticiones() {
        return peticiones;
    }

    public void setPeticiones(Peticiones peticiones) {
        this.peticiones = peticiones;
    }

    @XmlTransient
    public List<ServiciosMante> getServiciosManteList() {
        return serviciosManteList;
    }

    public void setServiciosManteList(List<ServiciosMante> serviciosManteList) {
        this.serviciosManteList = serviciosManteList;
    }

    public ServiciosMante getServiciomantePadre() {
        return serviciomantePadre;
    }

    public void setServiciomantePadre(ServiciosMante serviciomantePadre) {
        this.serviciomantePadre = serviciomantePadre;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
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
        if (!(object instanceof ServiciosMante)) {
            return false;
        }
        ServiciosMante other = (ServiciosMante) object;
        if ((this.idPeticion == null && other.idPeticion != null) || (this.idPeticion != null && !this.idPeticion.equals(other.idPeticion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.ServiciosMante[ idPeticion=" + idPeticion + " ]";
    }
    
}
