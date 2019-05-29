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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AMARTEL
 */
@Entity
@Table(name = "PARTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partes.findAll", query = "SELECT p FROM Partes p")
    , @NamedQuery(name = "Partes.findByNumParte", query = "SELECT p FROM Partes p WHERE p.numParte = :numParte")
    , @NamedQuery(name = "Partes.findByTitulo", query = "SELECT p FROM Partes p WHERE p.titulo = :titulo")
    , @NamedQuery(name = "Partes.findByFechaSolucion", query = "SELECT p FROM Partes p WHERE p.fechaSolucion = :fechaSolucion")
    , @NamedQuery(name = "Partes.findByTiempo", query = "SELECT p FROM Partes p WHERE p.tiempo = :tiempo")
    , @NamedQuery(name = "Partes.findByProblemaNuestro", query = "SELECT p FROM Partes p WHERE p.problemaNuestro = :problemaNuestro")
    , @NamedQuery(name = "Partes.findByVersion", query = "SELECT p FROM Partes p WHERE p.version = :version")
    , @NamedQuery(name = "Partes.findByPendiente", query = "SELECT p FROM Partes p WHERE p.pendiente = :pendiente")
    , @NamedQuery(name = "Partes.findByProdisei", query = "SELECT p FROM Partes p WHERE p.prodisei = :prodisei")
    , @NamedQuery(name = "Partes.findByAbierto", query = "SELECT p FROM Partes p WHERE p.abierto = :abierto")
    , @NamedQuery(name = "Partes.findByFechaCreacion", query = "SELECT p FROM Partes p WHERE p.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "Partes.findByFechaLlamada", query = "SELECT p FROM Partes p WHERE p.fechaLlamada = :fechaLlamada")
    , @NamedQuery(name = "Partes.findByPersonaLlamada", query = "SELECT p FROM Partes p WHERE p.personaLlamada = :personaLlamada")
    , @NamedQuery(name = "Partes.findByFechaAtencion", query = "SELECT p FROM Partes p WHERE p.fechaAtencion = :fechaAtencion")
    , @NamedQuery(name = "Partes.findByNotificado", query = "SELECT p FROM Partes p WHERE p.notificado = :notificado")
    , @NamedQuery(name = "Partes.findByUsuarioCreacion", query = "SELECT p FROM Partes p WHERE p.usuarioCreacion = :usuarioCreacion")
    , @NamedQuery(name = "Partes.findByTelefonoLlamada", query = "SELECT p FROM Partes p WHERE p.telefonoLlamada = :telefonoLlamada")
    , @NamedQuery(name = "Partes.findByMinutosprevistos", query = "SELECT p FROM Partes p WHERE p.minutosprevistos = :minutosprevistos")
    , @NamedQuery(name = "Partes.findByRespondecli", query = "SELECT p FROM Partes p WHERE p.respondecli = :respondecli")
    , @NamedQuery(name = "Partes.findByFechaNotificado", query = "SELECT p FROM Partes p WHERE p.fechaNotificado = :fechaNotificado")
    , @NamedQuery(name = "Partes.findByFechaAceptacion", query = "SELECT p FROM Partes p WHERE p.fechaAceptacion = :fechaAceptacion")
    , @NamedQuery(name = "Partes.findByUsuarioAyuda", query = "SELECT p FROM Partes p WHERE p.usuarioAyuda = :usuarioAyuda")
    , @NamedQuery(name = "Partes.findByTiempoAyuda", query = "SELECT p FROM Partes p WHERE p.tiempoAyuda = :tiempoAyuda")})
public class Partes implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @TableGenerator(name = "SEQ_PARTE", table = "SECUENCIAS", pkColumnName = "SECUENCIA", valueColumnName = "VALOR_ACTUAL", pkColumnValue = "SEC_PARTE", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_PARTE")
    @Column(name = "NUM_PARTE")
    private int numParte;
    @Column(name = "TITULO")
    private String titulo;
    @Column(name = "FECHA_SOLUCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSolucion;
    @Column(name = "TIEMPO")
    private BigInteger tiempo;
    @Column(name = "PROBLEMA_NUESTRO")
    private BigInteger problemaNuestro;
    @Column(name = "VERSION")
    private BigInteger version;
    @Basic(optional = false)
    @Column(name = "PENDIENTE")
    private BigInteger pendiente;
    @Column(name = "PRODISEI")
    private String prodisei;
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "ABIERTO")
    private BigInteger abierto;
    @Basic(optional = false)
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @Column(name = "FECHA_LLAMADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLlamada;
    @Column(name = "PERSONA_LLAMADA")
    private String personaLlamada;
    @Lob
    @Column(name = "DATOSTECNICOS")
    private String datostecnicos;
    @Column(name = "FECHA_ATENCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAtencion;
    @Column(name = "NOTIFICADO")
    private BigInteger notificado;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "TELEFONO_LLAMADA")
    private String telefonoLlamada;
    @Column(name = "MINUTOSPREVISTOS")
    private Short minutosprevistos;
    @Basic(optional = false)
    @Column(name = "RESPONDECLI")
    private boolean respondecli;
    @Column(name = "FECHA_NOTIFICADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNotificado;
    @Column(name = "FECHA_ACEPTACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAceptacion;
    @Column(name = "USUARIO_AYUDA")
    private String usuarioAyuda;
    @Column(name = "TIEMPO_AYUDA")
    private BigInteger tiempoAyuda;
    @OneToMany(mappedBy = "numParte")
    private List<Registroparte> registroparteList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "partes")
    private IncidenciaParte incidenciaParte;
    @JoinColumn(name = "CLIENTE", referencedColumnName = "CLIENTE")
    @ManyToOne(optional = false)
    private Clientes cliente;
    @JoinColumn(name = "GUARDIAPALEX", referencedColumnName = "IDGUARDIA")
    @ManyToOne
    private Guardiapalex guardiapalex;
    @JoinColumn(name = "IMPORTANCIA", referencedColumnName = "PRIORIDAD")
    @ManyToOne
    private PrioridadParte importancia;
    @JoinColumn(name = "PROGRAMA", referencedColumnName = "PROGRAMA")
    @ManyToOne
    private Programas programa;
    @JoinColumn(name = "TIPO", referencedColumnName = "CODIGO")
    @ManyToOne
    private TiposParte tipo;
    @JoinColumn(name = "USUARIO", referencedColumnName = "USUARIO")
    @ManyToOne
    private Usuario usuario;
    @OneToMany(mappedBy = "idparte")
    private List<Tarea> tareaList;

    public Partes() {
    }

    public Partes(int numParte) {
        this.numParte = numParte;
    }

    public Partes(int numParte, BigInteger pendiente, BigInteger abierto, Date fechaCreacion, Date fechaLlamada, boolean respondecli) {
        this.numParte = numParte;
        this.pendiente = pendiente;
        this.abierto = abierto;
        this.fechaCreacion = fechaCreacion;
        this.fechaLlamada = fechaLlamada;
        this.respondecli = respondecli;
    }

    public int getNumParte() {
        return numParte;
    }

    public void setNumParte(int numParte) {
        this.numParte = numParte;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaSolucion() {
        return fechaSolucion;
    }

    public void setFechaSolucion(Date fechaSolucion) {
        this.fechaSolucion = fechaSolucion;
    }

    public BigInteger getTiempo() {
        return tiempo;
    }

    public void setTiempo(BigInteger tiempo) {
        this.tiempo = tiempo;
    }

    public BigInteger getProblemaNuestro() {
        return problemaNuestro;
    }

    public void setProblemaNuestro(BigInteger problemaNuestro) {
        this.problemaNuestro = problemaNuestro;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public BigInteger getPendiente() {
        return pendiente;
    }

    public void setPendiente(BigInteger pendiente) {
        this.pendiente = pendiente;
    }

    public String getProdisei() {
        return prodisei;
    }

    public void setProdisei(String prodisei) {
        this.prodisei = prodisei;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigInteger getAbierto() {
        return abierto;
    }

    public void setAbierto(BigInteger abierto) {
        this.abierto = abierto;
    }

    public String statusCorrecto() {
        if (abierto.equals(BigInteger.ONE)) {
            return "Abierto";
        } else {
            return "Cerrado";
        }
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaLlamada() {
        return fechaLlamada;
    }

    public void setFechaLlamada(Date fechaLlamada) {
        this.fechaLlamada = fechaLlamada;
    }

    public String getPersonaLlamada() {
        return personaLlamada;
    }

    public void setPersonaLlamada(String personaLlamada) {
        this.personaLlamada = personaLlamada;
    }

    public String getDatostecnicos() {
        return datostecnicos;
    }

    public void setDatostecnicos(String datostecnicos) {
        this.datostecnicos = datostecnicos;
    }

    public Date getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(Date fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public BigInteger getNotificado() {
        return notificado;
    }

    public void setNotificado(BigInteger notificado) {
        this.notificado = notificado;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getTelefonoLlamada() {
        return telefonoLlamada;
    }

    public void setTelefonoLlamada(String telefonoLlamada) {
        this.telefonoLlamada = telefonoLlamada;
    }

    public Short getMinutosprevistos() {
        return minutosprevistos;
    }

    public void setMinutosprevistos(Short minutosprevistos) {
        this.minutosprevistos = minutosprevistos;
    }

    public boolean getRespondecli() {
        return respondecli;
    }

    public void setRespondecli(boolean respondecli) {
        this.respondecli = respondecli;
    }

    public Date getFechaNotificado() {
        return fechaNotificado;
    }

    public void setFechaNotificado(Date fechaNotificado) {
        this.fechaNotificado = fechaNotificado;
    }

    public Date getFechaAceptacion() {
        return fechaAceptacion;
    }

    public void setFechaAceptacion(Date fechaAceptacion) {
        this.fechaAceptacion = fechaAceptacion;
    }

    public String getUsuarioAyuda() {
        return usuarioAyuda;
    }

    public void setUsuarioAyuda(String usuarioAyuda) {
        this.usuarioAyuda = usuarioAyuda;
    }

    public BigInteger getTiempoAyuda() {
        return tiempoAyuda;
    }

    public void setTiempoAyuda(BigInteger tiempoAyuda) {
        this.tiempoAyuda = tiempoAyuda;
    }

    @XmlTransient
    public List<Registroparte> getRegistroparteList() {
        return registroparteList;
    }

    public void setRegistroparteList(List<Registroparte> registroparteList) {
        this.registroparteList = registroparteList;
    }

    public IncidenciaParte getIncidenciaParte() {
        return incidenciaParte;
    }

    public void setIncidenciaParte(IncidenciaParte incidenciaParte) {
        this.incidenciaParte = incidenciaParte;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public Guardiapalex getGuardiapalex() {
        return guardiapalex;
    }

    public void setGuardiapalex(Guardiapalex guardiapalex) {
        this.guardiapalex = guardiapalex;
    }

    public PrioridadParte getImportancia() {
        return importancia;
    }

    public void setImportancia(PrioridadParte importancia) {
        this.importancia = importancia;
    }

    public Programas getPrograma() {
        return programa;
    }

    public void setPrograma(Programas programa) {
        this.programa = programa;
    }

    public TiposParte getTipo() {
        return tipo;
    }

    public void setTipo(TiposParte tipo) {
        this.tipo = tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        if (numParte != 0) {
            hash = numParte;
        }
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partes)) {
            return false;
        }
        Partes other = (Partes) object;
        if ((this.numParte == 0 && other.numParte != 0) || (this.numParte != 0 && this.numParte != other.numParte)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Partes[ numParte=" + numParte + " ]";
    }

}
