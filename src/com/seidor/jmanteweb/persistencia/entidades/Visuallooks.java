/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "VISUALLOOKS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visuallooks.findAll", query = "SELECT v FROM Visuallooks v")
    , @NamedQuery(name = "Visuallooks.findByLookandfeel", query = "SELECT v FROM Visuallooks v WHERE v.lookandfeel = :lookandfeel")
    , @NamedQuery(name = "Visuallooks.findByDescripcion", query = "SELECT v FROM Visuallooks v WHERE v.descripcion = :descripcion")})
public class Visuallooks implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "LOOKANDFEEL")
    private String lookandfeel;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lookandfeel")
    private List<Visualthemes> visualthemesList;

    public Visuallooks() {
    }

    public Visuallooks(String lookandfeel) {
        this.lookandfeel = lookandfeel;
    }

    public String getLookandfeel() {
        return lookandfeel;
    }

    public void setLookandfeel(String lookandfeel) {
        this.lookandfeel = lookandfeel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Visualthemes> getVisualthemesList() {
        return visualthemesList;
    }

    public void setVisualthemesList(List<Visualthemes> visualthemesList) {
        this.visualthemesList = visualthemesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lookandfeel != null ? lookandfeel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Visuallooks)) {
            return false;
        }
        Visuallooks other = (Visuallooks) object;
        if ((this.lookandfeel == null && other.lookandfeel != null) || (this.lookandfeel != null && !this.lookandfeel.equals(other.lookandfeel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Visuallooks[ lookandfeel=" + lookandfeel + " ]";
    }
    
}
