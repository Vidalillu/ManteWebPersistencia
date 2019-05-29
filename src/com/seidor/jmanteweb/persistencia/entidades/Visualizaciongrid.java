/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "VISUALIZACIONGRID")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visualizaciongrid.findAll", query = "SELECT v FROM Visualizaciongrid v")
    , @NamedQuery(name = "Visualizaciongrid.findByIdvisua", query = "SELECT v FROM Visualizaciongrid v WHERE v.idvisua = :idvisua")
    , @NamedQuery(name = "Visualizaciongrid.findByNombrevisualizacion", query = "SELECT v FROM Visualizaciongrid v WHERE v.nombrevisualizacion = :nombrevisualizacion")
    , @NamedQuery(name = "Visualizaciongrid.findByNombregrid", query = "SELECT v FROM Visualizaciongrid v WHERE v.nombregrid = :nombregrid")
    , @NamedQuery(name = "Visualizaciongrid.findByOrdencolumnas", query = "SELECT v FROM Visualizaciongrid v WHERE v.ordencolumnas = :ordencolumnas")
    , @NamedQuery(name = "Visualizaciongrid.findByOrdensort", query = "SELECT v FROM Visualizaciongrid v WHERE v.ordensort = :ordensort")
    , @NamedQuery(name = "Visualizaciongrid.findByOrden", query = "SELECT v FROM Visualizaciongrid v WHERE v.orden = :orden")
    , @NamedQuery(name = "Visualizaciongrid.findByVersion", query = "SELECT v FROM Visualizaciongrid v WHERE v.version = :version")
    , @NamedQuery(name = "Visualizaciongrid.findByTamanofila", query = "SELECT v FROM Visualizaciongrid v WHERE v.tamanofila = :tamanofila")
    , @NamedQuery(name = "Visualizaciongrid.findByVernumfilas", query = "SELECT v FROM Visualizaciongrid v WHERE v.vernumfilas = :vernumfilas")
    , @NamedQuery(name = "Visualizaciongrid.findByAgregados", query = "SELECT v FROM Visualizaciongrid v WHERE v.agregados = :agregados")
    , @NamedQuery(name = "Visualizaciongrid.findByVertotalesagrupados", query = "SELECT v FROM Visualizaciongrid v WHERE v.vertotalesagrupados = :vertotalesagrupados")
    , @NamedQuery(name = "Visualizaciongrid.findByFormatofecha", query = "SELECT v FROM Visualizaciongrid v WHERE v.formatofecha = :formatofecha")})
public class Visualizaciongrid implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDVISUA")
    private BigDecimal idvisua;
    @Basic(optional = false)
    @Column(name = "NOMBREVISUALIZACION")
    private String nombrevisualizacion;
    @Basic(optional = false)
    @Column(name = "NOMBREGRID")
    private String nombregrid;
    @Column(name = "ORDENCOLUMNAS")
    private String ordencolumnas;
    @Column(name = "ORDENSORT")
    private String ordensort;
    @Lob
    @Column(name = "FILTRO")
    private Serializable filtro;
    @Basic(optional = false)
    @Column(name = "ORDEN")
    private int orden;
    @Column(name = "VERSION")
    private BigInteger version;
    @Basic(optional = false)
    @Column(name = "TAMANOFILA")
    private int tamanofila;
    @Basic(optional = false)
    @Column(name = "VERNUMFILAS")
    private BigInteger vernumfilas;
    @Lob
    @Column(name = "AGRUPACION")
    private Serializable agrupacion;
    @Column(name = "AGREGADOS")
    private String agregados;
    @Column(name = "VERTOTALESAGRUPADOS")
    private BigInteger vertotalesagrupados;
    @Column(name = "FORMATOFECHA")
    private Short formatofecha;
    @JoinColumn(name = "USUARIO", referencedColumnName = "USUARIO")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Visualizaciongrid() {
    }

    public Visualizaciongrid(BigDecimal idvisua) {
        this.idvisua = idvisua;
    }

    public Visualizaciongrid(BigDecimal idvisua, String nombrevisualizacion, String nombregrid, int orden, int tamanofila, BigInteger vernumfilas) {
        this.idvisua = idvisua;
        this.nombrevisualizacion = nombrevisualizacion;
        this.nombregrid = nombregrid;
        this.orden = orden;
        this.tamanofila = tamanofila;
        this.vernumfilas = vernumfilas;
    }

    public BigDecimal getIdvisua() {
        return idvisua;
    }

    public void setIdvisua(BigDecimal idvisua) {
        this.idvisua = idvisua;
    }

    public String getNombrevisualizacion() {
        return nombrevisualizacion;
    }

    public void setNombrevisualizacion(String nombrevisualizacion) {
        this.nombrevisualizacion = nombrevisualizacion;
    }

    public String getNombregrid() {
        return nombregrid;
    }

    public void setNombregrid(String nombregrid) {
        this.nombregrid = nombregrid;
    }

    public String getOrdencolumnas() {
        return ordencolumnas;
    }

    public void setOrdencolumnas(String ordencolumnas) {
        this.ordencolumnas = ordencolumnas;
    }

    public String getOrdensort() {
        return ordensort;
    }

    public void setOrdensort(String ordensort) {
        this.ordensort = ordensort;
    }

    public Serializable getFiltro() {
        return filtro;
    }

    public void setFiltro(Serializable filtro) {
        this.filtro = filtro;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public int getTamanofila() {
        return tamanofila;
    }

    public void setTamanofila(int tamanofila) {
        this.tamanofila = tamanofila;
    }

    public BigInteger getVernumfilas() {
        return vernumfilas;
    }

    public void setVernumfilas(BigInteger vernumfilas) {
        this.vernumfilas = vernumfilas;
    }

    public Serializable getAgrupacion() {
        return agrupacion;
    }

    public void setAgrupacion(Serializable agrupacion) {
        this.agrupacion = agrupacion;
    }

    public String getAgregados() {
        return agregados;
    }

    public void setAgregados(String agregados) {
        this.agregados = agregados;
    }

    public BigInteger getVertotalesagrupados() {
        return vertotalesagrupados;
    }

    public void setVertotalesagrupados(BigInteger vertotalesagrupados) {
        this.vertotalesagrupados = vertotalesagrupados;
    }

    public Short getFormatofecha() {
        return formatofecha;
    }

    public void setFormatofecha(Short formatofecha) {
        this.formatofecha = formatofecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvisua != null ? idvisua.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Visualizaciongrid)) {
            return false;
        }
        Visualizaciongrid other = (Visualizaciongrid) object;
        if ((this.idvisua == null && other.idvisua != null) || (this.idvisua != null && !this.idvisua.equals(other.idvisua))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Visualizaciongrid[ idvisua=" + idvisua + " ]";
    }
    
}
