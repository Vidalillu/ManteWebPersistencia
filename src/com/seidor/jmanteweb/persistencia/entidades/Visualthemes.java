/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "VISUALTHEMES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visualthemes.findAll", query = "SELECT v FROM Visualthemes v")
    , @NamedQuery(name = "Visualthemes.findByTheme", query = "SELECT v FROM Visualthemes v WHERE v.theme = :theme")
    , @NamedQuery(name = "Visualthemes.findByDescripcion", query = "SELECT v FROM Visualthemes v WHERE v.descripcion = :descripcion")
    , @NamedQuery(name = "Visualthemes.findByIdthemes", query = "SELECT v FROM Visualthemes v WHERE v.idthemes = :idthemes")
    , @NamedQuery(name = "Visualthemes.findByOriginal", query = "SELECT v FROM Visualthemes v WHERE v.original = :original")})
public class Visualthemes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "THEME")
    private String theme;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDTHEMES")
    private BigDecimal idthemes;
    @Basic(optional = false)
    @Column(name = "ORIGINAL")
    private BigInteger original;
    @JoinColumn(name = "LOOKANDFEEL", referencedColumnName = "LOOKANDFEEL")
    @ManyToOne(optional = false)
    private Visuallooks lookandfeel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "theme")
    private List<Visualcomponent> visualcomponentList;

    public Visualthemes() {
    }

    public Visualthemes(BigDecimal idthemes) {
        this.idthemes = idthemes;
    }

    public Visualthemes(BigDecimal idthemes, String theme, BigInteger original) {
        this.idthemes = idthemes;
        this.theme = theme;
        this.original = original;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getIdthemes() {
        return idthemes;
    }

    public void setIdthemes(BigDecimal idthemes) {
        this.idthemes = idthemes;
    }

    public BigInteger getOriginal() {
        return original;
    }

    public void setOriginal(BigInteger original) {
        this.original = original;
    }

    public Visuallooks getLookandfeel() {
        return lookandfeel;
    }

    public void setLookandfeel(Visuallooks lookandfeel) {
        this.lookandfeel = lookandfeel;
    }

    @XmlTransient
    public List<Visualcomponent> getVisualcomponentList() {
        return visualcomponentList;
    }

    public void setVisualcomponentList(List<Visualcomponent> visualcomponentList) {
        this.visualcomponentList = visualcomponentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idthemes != null ? idthemes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Visualthemes)) {
            return false;
        }
        Visualthemes other = (Visualthemes) object;
        if ((this.idthemes == null && other.idthemes != null) || (this.idthemes != null && !this.idthemes.equals(other.idthemes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Visualthemes[ idthemes=" + idthemes + " ]";
    }
    
}
