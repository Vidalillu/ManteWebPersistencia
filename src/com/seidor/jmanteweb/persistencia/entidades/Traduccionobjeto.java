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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "TRADUCCIONOBJETO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Traduccionobjeto.findAll", query = "SELECT t FROM Traduccionobjeto t")
    , @NamedQuery(name = "Traduccionobjeto.findByIdtrad", query = "SELECT t FROM Traduccionobjeto t WHERE t.idtrad = :idtrad")
    , @NamedQuery(name = "Traduccionobjeto.findByTexto", query = "SELECT t FROM Traduccionobjeto t WHERE t.texto = :texto")
    , @NamedQuery(name = "Traduccionobjeto.findByIdioma", query = "SELECT t FROM Traduccionobjeto t WHERE t.idioma = :idioma")
    , @NamedQuery(name = "Traduccionobjeto.findByTraduccion", query = "SELECT t FROM Traduccionobjeto t WHERE t.traduccion = :traduccion")
    , @NamedQuery(name = "Traduccionobjeto.findByVersion", query = "SELECT t FROM Traduccionobjeto t WHERE t.version = :version")
    , @NamedQuery(name = "Traduccionobjeto.findByFechatraduccion", query = "SELECT t FROM Traduccionobjeto t WHERE t.fechatraduccion = :fechatraduccion")})
public class Traduccionobjeto implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDTRAD")
    private BigDecimal idtrad;
    @Basic(optional = false)
    @Column(name = "TEXTO")
    private String texto;
    @Basic(optional = false)
    @Column(name = "IDIOMA")
    private String idioma;
    @Basic(optional = false)
    @Column(name = "TRADUCCION")
    private String traduccion;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @Column(name = "FECHATRADUCCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechatraduccion;

    public Traduccionobjeto() {
    }

    public Traduccionobjeto(BigDecimal idtrad) {
        this.idtrad = idtrad;
    }

    public Traduccionobjeto(BigDecimal idtrad, String texto, String idioma, String traduccion, BigInteger version) {
        this.idtrad = idtrad;
        this.texto = texto;
        this.idioma = idioma;
        this.traduccion = traduccion;
        this.version = version;
    }

    public BigDecimal getIdtrad() {
        return idtrad;
    }

    public void setIdtrad(BigDecimal idtrad) {
        this.idtrad = idtrad;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getTraduccion() {
        return traduccion;
    }

    public void setTraduccion(String traduccion) {
        this.traduccion = traduccion;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public Date getFechatraduccion() {
        return fechatraduccion;
    }

    public void setFechatraduccion(Date fechatraduccion) {
        this.fechatraduccion = fechatraduccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtrad != null ? idtrad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Traduccionobjeto)) {
            return false;
        }
        Traduccionobjeto other = (Traduccionobjeto) object;
        if ((this.idtrad == null && other.idtrad != null) || (this.idtrad != null && !this.idtrad.equals(other.idtrad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Traduccionobjeto[ idtrad=" + idtrad + " ]";
    }
    
}
