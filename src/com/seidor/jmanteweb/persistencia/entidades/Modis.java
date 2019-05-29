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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
@Table(name = "MODIS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modis.findAll", query = "SELECT m FROM Modis m")
    , @NamedQuery(name = "Modis.findByIdmodi", query = "SELECT m FROM Modis m WHERE m.idmodi = :idmodi")
    , @NamedQuery(name = "Modis.findByTitulo", query = "SELECT m FROM Modis m WHERE m.titulo = :titulo")
    , @NamedQuery(name = "Modis.findByDescripbreve", query = "SELECT m FROM Modis m WHERE m.descripbreve = :descripbreve")
    , @NamedQuery(name = "Modis.findByFechacreacion", query = "SELECT m FROM Modis m WHERE m.fechacreacion = :fechacreacion")
    , @NamedQuery(name = "Modis.findByVersion", query = "SELECT m FROM Modis m WHERE m.version = :version")
    , @NamedQuery(name = "Modis.findByHaytarea", query = "SELECT m FROM Modis m WHERE m.haytarea = :haytarea")})
public class Modis implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDMODI")
    private BigInteger idmodi;
    @Basic(optional = false)
    @Column(name = "TITULO")
    private String titulo;
    @Column(name = "DESCRIPBREVE")
    private String descripbreve;
    @Basic(optional = false)
    @Column(name = "FECHACREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    @Column(name = "VERSION")
    private BigInteger version;
    @Basic(optional = false)
    @Column(name = "HAYTAREA")
    private BigInteger haytarea;
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Lob
    @Column(name = "CAMBIOS")
    private String cambios;
    @JoinTable(name = "MODI_PROGRAMA", joinColumns = {
        @JoinColumn(name = "IDMODI", referencedColumnName = "IDMODI")}, inverseJoinColumns = {
        @JoinColumn(name = "PROGRAMA", referencedColumnName = "PROGRAMA")})
    @ManyToMany
    private List<Programas> programasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modis")
    private List<InfoModiPrograma> infoModiProgramaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modis")
    private List<InfoModi> infoModiList;
    @OneToMany(mappedBy = "idmodi")
    private List<Tarea> tareaList;

    public Modis() {
    }

    public Modis(BigInteger idmodi) {
        this.idmodi = idmodi;
    }

    public Modis(BigInteger idmodi, String titulo, Date fechacreacion, BigInteger haytarea) {
        this.idmodi = idmodi;
        this.titulo = titulo;
        this.fechacreacion = fechacreacion;
        this.haytarea = haytarea;
    }

    public BigInteger getIdmodi() {
        return idmodi;
    }

    public void setIdmodi(BigInteger idmodi) {
        this.idmodi = idmodi;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripbreve() {
        return descripbreve;
    }

    public void setDescripbreve(String descripbreve) {
        this.descripbreve = descripbreve;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public BigInteger getHaytarea() {
        return haytarea;
    }

    public void setHaytarea(BigInteger haytarea) {
        this.haytarea = haytarea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCambios() {
        return cambios;
    }

    public void setCambios(String cambios) {
        this.cambios = cambios;
    }

    @XmlTransient
    public List<Programas> getProgramasList() {
        return programasList;
    }

    public void setProgramasList(List<Programas> programasList) {
        this.programasList = programasList;
    }

    @XmlTransient
    public List<InfoModiPrograma> getInfoModiProgramaList() {
        return infoModiProgramaList;
    }

    public void setInfoModiProgramaList(List<InfoModiPrograma> infoModiProgramaList) {
        this.infoModiProgramaList = infoModiProgramaList;
    }

    @XmlTransient
    public List<InfoModi> getInfoModiList() {
        return infoModiList;
    }

    public void setInfoModiList(List<InfoModi> infoModiList) {
        this.infoModiList = infoModiList;
    }

    @XmlTransient
    public List<Tarea> getTareaList() {
        return tareaList;
    }

    public void setTareaList(List<Tarea> tareaList) {
        this.tareaList = tareaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmodi != null ? idmodi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modis)) {
            return false;
        }
        Modis other = (Modis) object;
        if ((this.idmodi == null && other.idmodi != null) || (this.idmodi != null && !this.idmodi.equals(other.idmodi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Modis[ idmodi=" + idmodi + " ]";
    }
    
}
