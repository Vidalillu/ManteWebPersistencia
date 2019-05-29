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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Table(name = "PROGRAMAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Programas.findAll", query = "SELECT p FROM Programas p")
    , @NamedQuery(name = "Programas.findByPrograma", query = "SELECT p FROM Programas p WHERE p.programa = :programa")
    , @NamedQuery(name = "Programas.findByVersion", query = "SELECT p FROM Programas p WHERE p.version = :version")})
public class Programas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PROGRAMA")
    private String programa;
    @Column(name = "VERSION")
    private BigInteger version;
    @ManyToMany(mappedBy = "programasList")
    private List<Modis> modisList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programas")
    private List<InfoModiPrograma> infoModiProgramaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programas")
    private List<InfoPrograma> infoProgramaList;
    @OneToMany(mappedBy = "programa")
    private List<Partes> partesList;

    public Programas() {
    }

    public Programas(String programa) {
        this.programa = programa;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    @XmlTransient
    public List<Modis> getModisList() {
        return modisList;
    }

    public void setModisList(List<Modis> modisList) {
        this.modisList = modisList;
    }

    @XmlTransient
    public List<InfoModiPrograma> getInfoModiProgramaList() {
        return infoModiProgramaList;
    }

    public void setInfoModiProgramaList(List<InfoModiPrograma> infoModiProgramaList) {
        this.infoModiProgramaList = infoModiProgramaList;
    }

    @XmlTransient
    public List<InfoPrograma> getInfoProgramaList() {
        return infoProgramaList;
    }

    public void setInfoProgramaList(List<InfoPrograma> infoProgramaList) {
        this.infoProgramaList = infoProgramaList;
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
        hash += (programa != null ? programa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Programas)) {
            return false;
        }
        Programas other = (Programas) object;
        if ((this.programa == null && other.programa != null) || (this.programa != null && !this.programa.equals(other.programa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Programas[ programa=" + programa + " ]";
    }
    
}
