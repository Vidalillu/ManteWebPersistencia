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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "TAREA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tarea.findAll", query = "SELECT t FROM Tarea t")
    , @NamedQuery(name = "Tarea.findById", query = "SELECT t FROM Tarea t WHERE t.id = :id")
    , @NamedQuery(name = "Tarea.findByNombre", query = "SELECT t FROM Tarea t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "Tarea.findByDescripcion", query = "SELECT t FROM Tarea t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "Tarea.findByRepeticion", query = "SELECT t FROM Tarea t WHERE t.repeticion = :repeticion")
    , @NamedQuery(name = "Tarea.findByDiasrepeticion", query = "SELECT t FROM Tarea t WHERE t.diasrepeticion = :diasrepeticion")
    , @NamedQuery(name = "Tarea.findByFechaejecucion", query = "SELECT t FROM Tarea t WHERE t.fechaejecucion = :fechaejecucion")
    , @NamedQuery(name = "Tarea.findByRealizada", query = "SELECT t FROM Tarea t WHERE t.realizada = :realizada")
    , @NamedQuery(name = "Tarea.findByTipo", query = "SELECT t FROM Tarea t WHERE t.tipo = :tipo")
    , @NamedQuery(name = "Tarea.findByVersion", query = "SELECT t FROM Tarea t WHERE t.version = :version")
    , @NamedQuery(name = "Tarea.findByNotificado", query = "SELECT t FROM Tarea t WHERE t.notificado = :notificado")
    , @NamedQuery(name = "Tarea.findByComentarios", query = "SELECT t FROM Tarea t WHERE t.comentarios = :comentarios")
    , @NamedQuery(name = "Tarea.findByError", query = "SELECT t FROM Tarea t WHERE t.error = :error")
    , @NamedQuery(name = "Tarea.findByFechacreacion", query = "SELECT t FROM Tarea t WHERE t.fechacreacion = :fechacreacion")
    , @NamedQuery(name = "Tarea.findByAplicar", query = "SELECT t FROM Tarea t WHERE t.aplicar = :aplicar")
    , @NamedQuery(name = "Tarea.findByFechasigejec", query = "SELECT t FROM Tarea t WHERE t.fechasigejec = :fechasigejec")
    , @NamedQuery(name = "Tarea.findBySubtipo", query = "SELECT t FROM Tarea t WHERE t.subtipo = :subtipo")
    , @NamedQuery(name = "Tarea.findByPrioridad", query = "SELECT t FROM Tarea t WHERE t.prioridad = :prioridad")
    , @NamedQuery(name = "Tarea.findByUsuariocreacion", query = "SELECT t FROM Tarea t WHERE t.usuariocreacion = :usuariocreacion")
    , @NamedQuery(name = "Tarea.findByVerificada", query = "SELECT t FROM Tarea t WHERE t.verificada = :verificada")})
public class Tarea implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "REPETICION")
    private BigInteger repeticion;
    @Basic(optional = false)
    @Column(name = "DIASREPETICION")
    private int diasrepeticion;
    @Column(name = "FECHAEJECUCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaejecucion;
    @Basic(optional = false)
    @Column(name = "REALIZADA")
    private BigInteger realizada;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @Basic(optional = false)
    @Column(name = "NOTIFICADO")
    private BigInteger notificado;
    @Column(name = "COMENTARIOS")
    private String comentarios;
    @Basic(optional = false)
    @Column(name = "ERROR")
    private BigInteger error;
    @Column(name = "FECHACREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    @Basic(optional = false)
    @Column(name = "APLICAR")
    private BigInteger aplicar;
    @Lob
    @Column(name = "DATOSTAREA")
    private String datostarea;
    @Column(name = "FECHASIGEJEC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechasigejec;
    @Column(name = "SUBTIPO")
    private String subtipo;
    @Column(name = "PRIORIDAD")
    private BigInteger prioridad;
    @Column(name = "USUARIOCREACION")
    private String usuariocreacion;
    @Basic(optional = false)
    @Column(name = "VERIFICADA")
    private BigInteger verificada;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtarea")
    private List<UsuarioTarea> usuarioTareaList;
    @JoinColumn(name = "CLIENTE", referencedColumnName = "CLIENTE")
    @ManyToOne
    private Clientes cliente;
    @JoinColumn(name = "IDMODI", referencedColumnName = "IDMODI")
    @ManyToOne
    private Modis idmodi;
    @JoinColumn(name = "IDPARTE", referencedColumnName = "NUM_PARTE")
    @ManyToOne
    private Partes idparte;
    @JoinColumn(name = "USUARIO", referencedColumnName = "USUARIO")
    @ManyToOne
    private Usuario usuario;

    public Tarea() {
    }

    public Tarea(BigDecimal id) {
        this.id = id;
    }

    public Tarea(BigDecimal id, String nombre, BigInteger repeticion, int diasrepeticion, BigInteger realizada, String tipo, BigInteger version, BigInteger notificado, BigInteger error, BigInteger aplicar, BigInteger verificada) {
        this.id = id;
        this.nombre = nombre;
        this.repeticion = repeticion;
        this.diasrepeticion = diasrepeticion;
        this.realizada = realizada;
        this.tipo = tipo;
        this.version = version;
        this.notificado = notificado;
        this.error = error;
        this.aplicar = aplicar;
        this.verificada = verificada;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
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

    public BigInteger getRepeticion() {
        return repeticion;
    }

    public void setRepeticion(BigInteger repeticion) {
        this.repeticion = repeticion;
    }

    public int getDiasrepeticion() {
        return diasrepeticion;
    }

    public void setDiasrepeticion(int diasrepeticion) {
        this.diasrepeticion = diasrepeticion;
    }

    public Date getFechaejecucion() {
        return fechaejecucion;
    }

    public void setFechaejecucion(Date fechaejecucion) {
        this.fechaejecucion = fechaejecucion;
    }

    public BigInteger getRealizada() {
        return realizada;
    }

    public void setRealizada(BigInteger realizada) {
        this.realizada = realizada;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public BigInteger getNotificado() {
        return notificado;
    }

    public void setNotificado(BigInteger notificado) {
        this.notificado = notificado;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public BigInteger getError() {
        return error;
    }

    public void setError(BigInteger error) {
        this.error = error;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public BigInteger getAplicar() {
        return aplicar;
    }

    public void setAplicar(BigInteger aplicar) {
        this.aplicar = aplicar;
    }

    public String getDatostarea() {
        return datostarea;
    }

    public void setDatostarea(String datostarea) {
        this.datostarea = datostarea;
    }

    public Date getFechasigejec() {
        return fechasigejec;
    }

    public void setFechasigejec(Date fechasigejec) {
        this.fechasigejec = fechasigejec;
    }

    public String getSubtipo() {
        return subtipo;
    }

    public void setSubtipo(String subtipo) {
        this.subtipo = subtipo;
    }

    public BigInteger getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(BigInteger prioridad) {
        this.prioridad = prioridad;
    }

    public String getUsuariocreacion() {
        return usuariocreacion;
    }

    public void setUsuariocreacion(String usuariocreacion) {
        this.usuariocreacion = usuariocreacion;
    }

    public BigInteger getVerificada() {
        return verificada;
    }

    public void setVerificada(BigInteger verificada) {
        this.verificada = verificada;
    }

    @XmlTransient
    public List<UsuarioTarea> getUsuarioTareaList() {
        return usuarioTareaList;
    }

    public void setUsuarioTareaList(List<UsuarioTarea> usuarioTareaList) {
        this.usuarioTareaList = usuarioTareaList;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public Modis getIdmodi() {
        return idmodi;
    }

    public void setIdmodi(Modis idmodi) {
        this.idmodi = idmodi;
    }

    public Partes getIdparte() {
        return idparte;
    }

    public void setIdparte(Partes idparte) {
        this.idparte = idparte;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tarea)) {
            return false;
        }
        Tarea other = (Tarea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Tarea[ id=" + id + " ]";
    }
    
}
