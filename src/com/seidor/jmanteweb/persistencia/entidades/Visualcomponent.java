/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "VISUALCOMPONENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visualcomponent.findAll", query = "SELECT v FROM Visualcomponent v")
    , @NamedQuery(name = "Visualcomponent.findByIdcomponent", query = "SELECT v FROM Visualcomponent v WHERE v.idcomponent = :idcomponent")
    , @NamedQuery(name = "Visualcomponent.findByComponente", query = "SELECT v FROM Visualcomponent v WHERE v.componente = :componente")
    , @NamedQuery(name = "Visualcomponent.findByColorold", query = "SELECT v FROM Visualcomponent v WHERE v.colorold = :colorold")
    , @NamedQuery(name = "Visualcomponent.findByValor", query = "SELECT v FROM Visualcomponent v WHERE v.valor = :valor")
    , @NamedQuery(name = "Visualcomponent.findByColor", query = "SELECT v FROM Visualcomponent v WHERE v.color = :color")})
public class Visualcomponent implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDCOMPONENT")
    private BigDecimal idcomponent;
    @Basic(optional = false)
    @Column(name = "COMPONENTE")
    private String componente;
    @Column(name = "COLOROLD")
    private String colorold;
    @Column(name = "VALOR")
    private Integer valor;
    @Column(name = "COLOR")
    private String color;
    @JoinColumn(name = "THEME", referencedColumnName = "IDTHEMES")
    @ManyToOne(optional = false)
    private Visualthemes theme;

    public Visualcomponent() {
    }

    public Visualcomponent(BigDecimal idcomponent) {
        this.idcomponent = idcomponent;
    }

    public Visualcomponent(BigDecimal idcomponent, String componente) {
        this.idcomponent = idcomponent;
        this.componente = componente;
    }

    public BigDecimal getIdcomponent() {
        return idcomponent;
    }

    public void setIdcomponent(BigDecimal idcomponent) {
        this.idcomponent = idcomponent;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public String getColorold() {
        return colorold;
    }

    public void setColorold(String colorold) {
        this.colorold = colorold;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Visualthemes getTheme() {
        return theme;
    }

    public void setTheme(Visualthemes theme) {
        this.theme = theme;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcomponent != null ? idcomponent.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Visualcomponent)) {
            return false;
        }
        Visualcomponent other = (Visualcomponent) object;
        if ((this.idcomponent == null && other.idcomponent != null) || (this.idcomponent != null && !this.idcomponent.equals(other.idcomponent))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Visualcomponent[ idcomponent=" + idcomponent + " ]";
    }
    
}
