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
@Table(name = "COMPONENTEMENU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Componentemenu.findAll", query = "SELECT c FROM Componentemenu c")
    , @NamedQuery(name = "Componentemenu.findByNombre", query = "SELECT c FROM Componentemenu c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Componentemenu.findByDescripcion", query = "SELECT c FROM Componentemenu c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "Componentemenu.findByDescripcionlarga", query = "SELECT c FROM Componentemenu c WHERE c.descripcionlarga = :descripcionlarga")
    , @NamedQuery(name = "Componentemenu.findByIcono", query = "SELECT c FROM Componentemenu c WHERE c.icono = :icono")
    , @NamedQuery(name = "Componentemenu.findByVersion", query = "SELECT c FROM Componentemenu c WHERE c.version = :version")
    , @NamedQuery(name = "Componentemenu.findByTipo", query = "SELECT c FROM Componentemenu c WHERE c.tipo = :tipo")
    , @NamedQuery(name = "Componentemenu.findByPrincipal", query = "SELECT c FROM Componentemenu c WHERE c.principal = :principal")
    , @NamedQuery(name = "Componentemenu.findByAuditoria", query = "SELECT c FROM Componentemenu c WHERE c.auditoria = :auditoria")
    , @NamedQuery(name = "Componentemenu.findByMetodo", query = "SELECT c FROM Componentemenu c WHERE c.metodo = :metodo")
    , @NamedQuery(name = "Componentemenu.findByTipocomponente", query = "SELECT c FROM Componentemenu c WHERE c.tipocomponente = :tipocomponente")
    , @NamedQuery(name = "Componentemenu.findByListado", query = "SELECT c FROM Componentemenu c WHERE c.listado = :listado")
    , @NamedQuery(name = "Componentemenu.findByAyuda", query = "SELECT c FROM Componentemenu c WHERE c.ayuda = :ayuda")
    , @NamedQuery(name = "Componentemenu.findByTeclaacceso", query = "SELECT c FROM Componentemenu c WHERE c.teclaacceso = :teclaacceso")
    , @NamedQuery(name = "Componentemenu.findByPantallaparametros", query = "SELECT c FROM Componentemenu c WHERE c.pantallaparametros = :pantallaparametros")
    , @NamedQuery(name = "Componentemenu.findByPantallalenta", query = "SELECT c FROM Componentemenu c WHERE c.pantallalenta = :pantallalenta")
    , @NamedQuery(name = "Componentemenu.findByCheckradio", query = "SELECT c FROM Componentemenu c WHERE c.checkradio = :checkradio")
    , @NamedQuery(name = "Componentemenu.findByInterno", query = "SELECT c FROM Componentemenu c WHERE c.interno = :interno")
    , @NamedQuery(name = "Componentemenu.findByGuardarcerrar", query = "SELECT c FROM Componentemenu c WHERE c.guardarcerrar = :guardarcerrar")
    , @NamedQuery(name = "Componentemenu.findByDescripcionvoz", query = "SELECT c FROM Componentemenu c WHERE c.descripcionvoz = :descripcionvoz")})
public class Componentemenu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "DESCRIPCIONLARGA")
    private String descripcionlarga;
    @Column(name = "ICONO")
    private String icono;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "PRINCIPAL")
    private BigInteger principal;
    @Column(name = "AUDITORIA")
    private BigInteger auditoria;
    @Column(name = "METODO")
    private String metodo;
    @Basic(optional = false)
    @Column(name = "TIPOCOMPONENTE")
    private String tipocomponente;
    @Column(name = "LISTADO")
    private String listado;
    @Column(name = "AYUDA")
    private String ayuda;
    @Column(name = "TECLAACCESO")
    private String teclaacceso;
    @Column(name = "PANTALLAPARAMETROS")
    private String pantallaparametros;
    @Column(name = "PANTALLALENTA")
    private BigInteger pantallalenta;
    @Column(name = "CHECKRADIO")
    private String checkradio;
    @Column(name = "INTERNO")
    private Short interno;
    @Basic(optional = false)
    @Column(name = "GUARDARCERRAR")
    private short guardarcerrar;
    @Column(name = "DESCRIPCIONVOZ")
    private String descripcionvoz;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pantalla")
    private List<Cambioprograma> cambioprogramaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pantalla")
    private List<Objetovisual> objetovisualList;
    @OneToMany(mappedBy = "menuprograma")
    private List<Perfil> perfilList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componente")
    private List<Pantallaini> pantallainiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componentemenu1")
    private List<Favorito> favoritoList;
    @OneToMany(mappedBy = "pantallapadre")
    private List<Componentemenu> componentemenuList;
    @JoinColumn(name = "PANTALLAPADRE", referencedColumnName = "NOMBRE")
    @ManyToOne
    private Componentemenu pantallapadre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componentemenu")
    private List<Ordencomponente> ordencomponenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componentemenu1")
    private List<Ordencomponente> ordencomponenteList1;
    @OneToMany(mappedBy = "menu")
    private List<Usuario> usuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pantalla")
    private List<Macro> macroList;

    public Componentemenu() {
    }

    public Componentemenu(String nombre) {
        this.nombre = nombre;
    }

    public Componentemenu(String nombre, String descripcion, BigInteger version, String tipo, String tipocomponente, short guardarcerrar) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.version = version;
        this.tipo = tipo;
        this.tipocomponente = tipocomponente;
        this.guardarcerrar = guardarcerrar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcionlarga() {
        return descripcionlarga;
    }

    public void setDescripcionlarga(String descripcionlarga) {
        this.descripcionlarga = descripcionlarga;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigInteger getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigInteger principal) {
        this.principal = principal;
    }

    public BigInteger getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(BigInteger auditoria) {
        this.auditoria = auditoria;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getTipocomponente() {
        return tipocomponente;
    }

    public void setTipocomponente(String tipocomponente) {
        this.tipocomponente = tipocomponente;
    }

    public String getListado() {
        return listado;
    }

    public void setListado(String listado) {
        this.listado = listado;
    }

    public String getAyuda() {
        return ayuda;
    }

    public void setAyuda(String ayuda) {
        this.ayuda = ayuda;
    }

    public String getTeclaacceso() {
        return teclaacceso;
    }

    public void setTeclaacceso(String teclaacceso) {
        this.teclaacceso = teclaacceso;
    }

    public String getPantallaparametros() {
        return pantallaparametros;
    }

    public void setPantallaparametros(String pantallaparametros) {
        this.pantallaparametros = pantallaparametros;
    }

    public BigInteger getPantallalenta() {
        return pantallalenta;
    }

    public void setPantallalenta(BigInteger pantallalenta) {
        this.pantallalenta = pantallalenta;
    }

    public String getCheckradio() {
        return checkradio;
    }

    public void setCheckradio(String checkradio) {
        this.checkradio = checkradio;
    }

    public Short getInterno() {
        return interno;
    }

    public void setInterno(Short interno) {
        this.interno = interno;
    }

    public short getGuardarcerrar() {
        return guardarcerrar;
    }

    public void setGuardarcerrar(short guardarcerrar) {
        this.guardarcerrar = guardarcerrar;
    }

    public String getDescripcionvoz() {
        return descripcionvoz;
    }

    public void setDescripcionvoz(String descripcionvoz) {
        this.descripcionvoz = descripcionvoz;
    }

    @XmlTransient
    public List<Cambioprograma> getCambioprogramaList() {
        return cambioprogramaList;
    }

    public void setCambioprogramaList(List<Cambioprograma> cambioprogramaList) {
        this.cambioprogramaList = cambioprogramaList;
    }

    @XmlTransient
    public List<Objetovisual> getObjetovisualList() {
        return objetovisualList;
    }

    public void setObjetovisualList(List<Objetovisual> objetovisualList) {
        this.objetovisualList = objetovisualList;
    }

    @XmlTransient
    public List<Perfil> getPerfilList() {
        return perfilList;
    }

    public void setPerfilList(List<Perfil> perfilList) {
        this.perfilList = perfilList;
    }

    @XmlTransient
    public List<Pantallaini> getPantallainiList() {
        return pantallainiList;
    }

    public void setPantallainiList(List<Pantallaini> pantallainiList) {
        this.pantallainiList = pantallainiList;
    }

    @XmlTransient
    public List<Favorito> getFavoritoList() {
        return favoritoList;
    }

    public void setFavoritoList(List<Favorito> favoritoList) {
        this.favoritoList = favoritoList;
    }

    @XmlTransient
    public List<Componentemenu> getComponentemenuList() {
        return componentemenuList;
    }

    public void setComponentemenuList(List<Componentemenu> componentemenuList) {
        this.componentemenuList = componentemenuList;
    }

    public Componentemenu getPantallapadre() {
        return pantallapadre;
    }

    public void setPantallapadre(Componentemenu pantallapadre) {
        this.pantallapadre = pantallapadre;
    }

    @XmlTransient
    public List<Ordencomponente> getOrdencomponenteList() {
        return ordencomponenteList;
    }

    public void setOrdencomponenteList(List<Ordencomponente> ordencomponenteList) {
        this.ordencomponenteList = ordencomponenteList;
    }

    @XmlTransient
    public List<Ordencomponente> getOrdencomponenteList1() {
        return ordencomponenteList1;
    }

    public void setOrdencomponenteList1(List<Ordencomponente> ordencomponenteList1) {
        this.ordencomponenteList1 = ordencomponenteList1;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @XmlTransient
    public List<Macro> getMacroList() {
        return macroList;
    }

    public void setMacroList(List<Macro> macroList) {
        this.macroList = macroList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombre != null ? nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Componentemenu)) {
            return false;
        }
        Componentemenu other = (Componentemenu) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Componentemenu[ nombre=" + nombre + " ]";
    }
    
}
