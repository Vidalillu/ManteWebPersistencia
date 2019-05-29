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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByUsuario", query = "SELECT u FROM Usuario u WHERE u.usuario = :usuario")
    , @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password")
    , @NamedQuery(name = "Usuario.findByMail", query = "SELECT u FROM Usuario u WHERE u.mail = :mail")
    , @NamedQuery(name = "Usuario.findByJefemante", query = "SELECT u FROM Usuario u WHERE u.jefemante = :jefemante")
    , @NamedQuery(name = "Usuario.findByNumsec", query = "SELECT u FROM Usuario u WHERE u.numsec = :numsec")
    , @NamedQuery(name = "Usuario.findByTelefono", query = "SELECT u FROM Usuario u WHERE u.telefono = :telefono")
    , @NamedQuery(name = "Usuario.findByDireccionmac", query = "SELECT u FROM Usuario u WHERE u.direccionmac = :direccionmac")
    , @NamedQuery(name = "Usuario.findByVersion", query = "SELECT u FROM Usuario u WHERE u.version = :version")
    , @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    , @NamedQuery(name = "Usuario.findByTipo", query = "SELECT u FROM Usuario u WHERE u.tipo = :tipo")
    , @NamedQuery(name = "Usuario.findByIdseidor", query = "SELECT u FROM Usuario u WHERE u.idseidor = :idseidor")
    , @NamedQuery(name = "Usuario.findByPosbarravertical", query = "SELECT u FROM Usuario u WHERE u.posbarravertical = :posbarravertical")
    , @NamedQuery(name = "Usuario.findByUserlookandfeel", query = "SELECT u FROM Usuario u WHERE u.userlookandfeel = :userlookandfeel")
    , @NamedQuery(name = "Usuario.findByUsertheme", query = "SELECT u FROM Usuario u WHERE u.usertheme = :usertheme")
    , @NamedQuery(name = "Usuario.findByGuardiapalex", query = "SELECT u FROM Usuario u WHERE u.guardiapalex = :guardiapalex")
    , @NamedQuery(name = "Usuario.findByIdioma", query = "SELECT u FROM Usuario u WHERE u.idioma = :idioma")
    , @NamedQuery(name = "Usuario.findBySonido", query = "SELECT u FROM Usuario u WHERE u.sonido = :sonido")
    , @NamedQuery(name = "Usuario.findByResponsableguardia", query = "SELECT u FROM Usuario u WHERE u.responsableguardia = :responsableguardia")
    , @NamedQuery(name = "Usuario.findByColorseleccionado", query = "SELECT u FROM Usuario u WHERE u.colorseleccionado = :colorseleccionado")
    , @NamedQuery(name = "Usuario.findByConsultapartesdetalle", query = "SELECT u FROM Usuario u WHERE u.consultapartesdetalle = :consultapartesdetalle")
    , @NamedQuery(name = "Usuario.findByColorcabecera", query = "SELECT u FROM Usuario u WHERE u.colorcabecera = :colorcabecera")
    , @NamedQuery(name = "Usuario.findByColorpie", query = "SELECT u FROM Usuario u WHERE u.colorpie = :colorpie")
    , @NamedQuery(name = "Usuario.findByPosbarrahorizontal", query = "SELECT u FROM Usuario u WHERE u.posbarrahorizontal = :posbarrahorizontal")
    , @NamedQuery(name = "Usuario.findByBarrafija", query = "SELECT u FROM Usuario u WHERE u.barrafija = :barrafija")
    , @NamedQuery(name = "Usuario.findByColorfondopestanas", query = "SELECT u FROM Usuario u WHERE u.colorfondopestanas = :colorfondopestanas")
    , @NamedQuery(name = "Usuario.findByAyudatooltips", query = "SELECT u FROM Usuario u WHERE u.ayudatooltips = :ayudatooltips")
    , @NamedQuery(name = "Usuario.findByActivo", query = "SELECT u FROM Usuario u WHERE u.activo = :activo")
    , @NamedQuery(name = "Usuario.findByOrden", query = "SELECT u FROM Usuario u WHERE u.orden = :orden")
    , @NamedQuery(name = "Usuario.findByUsuarioserviciomante", query = "SELECT u FROM Usuario u WHERE u.usuarioserviciomante = :usuarioserviciomante")
    , @NamedQuery(name = "Usuario.findByZoom", query = "SELECT u FROM Usuario u WHERE u.zoom = :zoom")
    , @NamedQuery(name = "Usuario.findByPasswordsanteriores", query = "SELECT u FROM Usuario u WHERE u.passwordsanteriores = :passwordsanteriores")
    , @NamedQuery(name = "Usuario.findByInterno", query = "SELECT u FROM Usuario u WHERE u.interno = :interno")
    , @NamedQuery(name = "Usuario.findByFechacambiopassword", query = "SELECT u FROM Usuario u WHERE u.fechacambiopassword = :fechacambiopassword")
    , @NamedQuery(name = "Usuario.findByCambiopassword", query = "SELECT u FROM Usuario u WHERE u.cambiopassword = :cambiopassword")
    , @NamedQuery(name = "Usuario.findByIntentoserroneos", query = "SELECT u FROM Usuario u WHERE u.intentoserroneos = :intentoserroneos")
    , @NamedQuery(name = "Usuario.findByIdCliente", query = "SELECT u FROM Usuario u WHERE u.idCliente = :idCliente")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "USUARIO")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "MAIL")
    private String mail;
    @Basic(optional = false)
    @Column(name = "JEFEMANTE")
    private BigInteger jefemante;
    @Column(name = "NUMSEC")
    private BigInteger numsec;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "DIRECCIONMAC")
    private String direccionmac;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "IDSEIDOR")
    private BigInteger idseidor;
    @Basic(optional = false)
    @Column(name = "POSBARRAVERTICAL")
    private int posbarravertical;
    @Column(name = "USERLOOKANDFEEL")
    private String userlookandfeel;
    @Column(name = "USERTHEME")
    private String usertheme;
    @Basic(optional = false)
    @Column(name = "GUARDIAPALEX")
    private BigInteger guardiapalex;
    @Column(name = "IDIOMA")
    private String idioma;
    @Basic(optional = false)
    @Column(name = "SONIDO")
    private BigInteger sonido;
    @Basic(optional = false)
    @Column(name = "RESPONSABLEGUARDIA")
    private BigInteger responsableguardia;
    @Column(name = "COLORSELECCIONADO")
    private String colorseleccionado;
    @Column(name = "CONSULTAPARTESDETALLE")
    private BigInteger consultapartesdetalle;
    @Column(name = "COLORCABECERA")
    private String colorcabecera;
    @Column(name = "COLORPIE")
    private String colorpie;
    @Column(name = "POSBARRAHORIZONTAL")
    private Integer posbarrahorizontal;
    @Basic(optional = false)
    @Column(name = "BARRAFIJA")
    private BigInteger barrafija;
    @Column(name = "COLORFONDOPESTANAS")
    private String colorfondopestanas;
    @Basic(optional = false)
    @Column(name = "AYUDATOOLTIPS")
    private BigInteger ayudatooltips;
    @Basic(optional = false)
    @Column(name = "ACTIVO")
    private BigInteger activo;
    @Column(name = "ORDEN")
    private BigInteger orden;
    @Column(name = "USUARIOSERVICIOMANTE")
    private BigInteger usuarioserviciomante;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ZOOM")
    private BigDecimal zoom;
    @Column(name = "PASSWORDSANTERIORES")
    private String passwordsanteriores;
    @Basic(optional = false)
    @Column(name = "INTERNO")
    private short interno;
    @Column(name = "FECHACAMBIOPASSWORD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacambiopassword;
    @Basic(optional = false)
    @Column(name = "CAMBIOPASSWORD")
    private short cambiopassword;
    @Column(name = "INTENTOSERRONEOS")
    private Short intentoserroneos;
    @Column(name = "ID_CLIENTE")
    private Long idCliente;
    @OneToMany(mappedBy = "usuario")
    private List<Registroparte> registroparteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Cambioprograma> cambioprogramaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Responsabilidad> responsabilidadList;
    @OneToMany(mappedBy = "titular")
    private List<Guardiapalex> guardiapalexList;
    @OneToMany(mappedBy = "usuarioReal")
    private List<Guardiapalex> guardiapalexList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Pantallaini> pantallainiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioasignado")
    private List<UsuarioTarea> usuarioTareaList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
    private UsuarioRueda usuarioRueda;
    @OneToMany(mappedBy = "pareja")
    private List<UsuarioRueda> usuarioRuedaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioMante")
    private List<Presupuestos> presupuestosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Visualizaciongrid> visualizaciongridList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioaplicacion")
    private List<InfoModi> infoModiList;
    @OneToMany(mappedBy = "usuario")
    private List<Partes> partesList;
    @OneToMany(mappedBy = "usuario")
    private List<Tarea> tareaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario1")
    private List<Favorito> favoritoList;
    @OneToMany(mappedBy = "usuario")
    private List<RegistroRevision> registroRevisionList;
    @JoinColumn(name = "CLIENTE", referencedColumnName = "CLIENTE")
    @ManyToOne
    private Clientes cliente;
    @JoinColumn(name = "MENU", referencedColumnName = "NOMBRE")
    @ManyToOne
    private Componentemenu menu;
    @JoinColumn(name = "PERFIL", referencedColumnName = "PERFIL")
    @ManyToOne
    private Perfil perfil;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario1")
    private RuedaPalex ruedaPalex;

    public Usuario() {
    }

    public Usuario(String usuario) {
        this.usuario = usuario;
    }

    public Usuario(String usuario, String password, BigInteger jefemante, BigInteger version, String nombre, int posbarravertical, BigInteger guardiapalex, BigInteger sonido, BigInteger responsableguardia, BigInteger barrafija, BigInteger ayudatooltips, BigInteger activo, short interno, short cambiopassword) {
        this.usuario = usuario;
        this.password = password;
        this.jefemante = jefemante;
        this.version = version;
        this.nombre = nombre;
        this.posbarravertical = posbarravertical;
        this.guardiapalex = guardiapalex;
        this.sonido = sonido;
        this.responsableguardia = responsableguardia;
        this.barrafija = barrafija;
        this.ayudatooltips = ayudatooltips;
        this.activo = activo;
        this.interno = interno;
        this.cambiopassword = cambiopassword;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public BigInteger getJefemante() {
        return jefemante;
    }

    public void setJefemante(BigInteger jefemante) {
        this.jefemante = jefemante;
    }

    public BigInteger getNumsec() {
        return numsec;
    }

    public void setNumsec(BigInteger numsec) {
        this.numsec = numsec;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccionmac() {
        return direccionmac;
    }

    public void setDireccionmac(String direccionmac) {
        this.direccionmac = direccionmac;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigInteger getIdseidor() {
        return idseidor;
    }

    public void setIdseidor(BigInteger idseidor) {
        this.idseidor = idseidor;
    }

    public int getPosbarravertical() {
        return posbarravertical;
    }

    public void setPosbarravertical(int posbarravertical) {
        this.posbarravertical = posbarravertical;
    }

    public String getUserlookandfeel() {
        return userlookandfeel;
    }

    public void setUserlookandfeel(String userlookandfeel) {
        this.userlookandfeel = userlookandfeel;
    }

    public String getUsertheme() {
        return usertheme;
    }

    public void setUsertheme(String usertheme) {
        this.usertheme = usertheme;
    }

    public BigInteger getGuardiapalex() {
        return guardiapalex;
    }

    public void setGuardiapalex(BigInteger guardiapalex) {
        this.guardiapalex = guardiapalex;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public BigInteger getSonido() {
        return sonido;
    }

    public void setSonido(BigInteger sonido) {
        this.sonido = sonido;
    }

    public BigInteger getResponsableguardia() {
        return responsableguardia;
    }

    public void setResponsableguardia(BigInteger responsableguardia) {
        this.responsableguardia = responsableguardia;
    }

    public String getColorseleccionado() {
        return colorseleccionado;
    }

    public void setColorseleccionado(String colorseleccionado) {
        this.colorseleccionado = colorseleccionado;
    }

    public BigInteger getConsultapartesdetalle() {
        return consultapartesdetalle;
    }

    public void setConsultapartesdetalle(BigInteger consultapartesdetalle) {
        this.consultapartesdetalle = consultapartesdetalle;
    }

    public String getColorcabecera() {
        return colorcabecera;
    }

    public void setColorcabecera(String colorcabecera) {
        this.colorcabecera = colorcabecera;
    }

    public String getColorpie() {
        return colorpie;
    }

    public void setColorpie(String colorpie) {
        this.colorpie = colorpie;
    }

    public Integer getPosbarrahorizontal() {
        return posbarrahorizontal;
    }

    public void setPosbarrahorizontal(Integer posbarrahorizontal) {
        this.posbarrahorizontal = posbarrahorizontal;
    }

    public BigInteger getBarrafija() {
        return barrafija;
    }

    public void setBarrafija(BigInteger barrafija) {
        this.barrafija = barrafija;
    }

    public String getColorfondopestanas() {
        return colorfondopestanas;
    }

    public void setColorfondopestanas(String colorfondopestanas) {
        this.colorfondopestanas = colorfondopestanas;
    }

    public BigInteger getAyudatooltips() {
        return ayudatooltips;
    }

    public void setAyudatooltips(BigInteger ayudatooltips) {
        this.ayudatooltips = ayudatooltips;
    }

    public BigInteger getActivo() {
        return activo;
    }

    public void setActivo(BigInteger activo) {
        this.activo = activo;
    }

    public BigInteger getOrden() {
        return orden;
    }

    public void setOrden(BigInteger orden) {
        this.orden = orden;
    }

    public BigInteger getUsuarioserviciomante() {
        return usuarioserviciomante;
    }

    public void setUsuarioserviciomante(BigInteger usuarioserviciomante) {
        this.usuarioserviciomante = usuarioserviciomante;
    }

    public BigDecimal getZoom() {
        return zoom;
    }

    public void setZoom(BigDecimal zoom) {
        this.zoom = zoom;
    }

    public String getPasswordsanteriores() {
        return passwordsanteriores;
    }

    public void setPasswordsanteriores(String passwordsanteriores) {
        this.passwordsanteriores = passwordsanteriores;
    }

    public short getInterno() {
        return interno;
    }

    public void setInterno(short interno) {
        this.interno = interno;
    }

    public Date getFechacambiopassword() {
        return fechacambiopassword;
    }

    public void setFechacambiopassword(Date fechacambiopassword) {
        this.fechacambiopassword = fechacambiopassword;
    }

    public short getCambiopassword() {
        return cambiopassword;
    }

    public void setCambiopassword(short cambiopassword) {
        this.cambiopassword = cambiopassword;
    }

    public Short getIntentoserroneos() {
        return intentoserroneos;
    }

    public void setIntentoserroneos(Short intentoserroneos) {
        this.intentoserroneos = intentoserroneos;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    @XmlTransient
    public List<Registroparte> getRegistroparteList() {
        return registroparteList;
    }

    public void setRegistroparteList(List<Registroparte> registroparteList) {
        this.registroparteList = registroparteList;
    }

    @XmlTransient
    public List<Cambioprograma> getCambioprogramaList() {
        return cambioprogramaList;
    }

    public void setCambioprogramaList(List<Cambioprograma> cambioprogramaList) {
        this.cambioprogramaList = cambioprogramaList;
    }

    @XmlTransient
    public List<Responsabilidad> getResponsabilidadList() {
        return responsabilidadList;
    }

    public void setResponsabilidadList(List<Responsabilidad> responsabilidadList) {
        this.responsabilidadList = responsabilidadList;
    }

    @XmlTransient
    public List<Guardiapalex> getGuardiapalexList() {
        return guardiapalexList;
    }

    public void setGuardiapalexList(List<Guardiapalex> guardiapalexList) {
        this.guardiapalexList = guardiapalexList;
    }

    @XmlTransient
    public List<Guardiapalex> getGuardiapalexList1() {
        return guardiapalexList1;
    }

    public void setGuardiapalexList1(List<Guardiapalex> guardiapalexList1) {
        this.guardiapalexList1 = guardiapalexList1;
    }

    @XmlTransient
    public List<Pantallaini> getPantallainiList() {
        return pantallainiList;
    }

    public void setPantallainiList(List<Pantallaini> pantallainiList) {
        this.pantallainiList = pantallainiList;
    }

    @XmlTransient
    public List<UsuarioTarea> getUsuarioTareaList() {
        return usuarioTareaList;
    }

    public void setUsuarioTareaList(List<UsuarioTarea> usuarioTareaList) {
        this.usuarioTareaList = usuarioTareaList;
    }

    public UsuarioRueda getUsuarioRueda() {
        return usuarioRueda;
    }

    public void setUsuarioRueda(UsuarioRueda usuarioRueda) {
        this.usuarioRueda = usuarioRueda;
    }

    @XmlTransient
    public List<UsuarioRueda> getUsuarioRuedaList() {
        return usuarioRuedaList;
    }

    public void setUsuarioRuedaList(List<UsuarioRueda> usuarioRuedaList) {
        this.usuarioRuedaList = usuarioRuedaList;
    }

    @XmlTransient
    public List<Presupuestos> getPresupuestosList() {
        return presupuestosList;
    }

    public void setPresupuestosList(List<Presupuestos> presupuestosList) {
        this.presupuestosList = presupuestosList;
    }

    @XmlTransient
    public List<Visualizaciongrid> getVisualizaciongridList() {
        return visualizaciongridList;
    }

    public void setVisualizaciongridList(List<Visualizaciongrid> visualizaciongridList) {
        this.visualizaciongridList = visualizaciongridList;
    }

    @XmlTransient
    public List<InfoModi> getInfoModiList() {
        return infoModiList;
    }

    public void setInfoModiList(List<InfoModi> infoModiList) {
        this.infoModiList = infoModiList;
    }

    @XmlTransient
    public List<Partes> getPartesList() {
        return partesList;
    }

    public void setPartesList(List<Partes> partesList) {
        this.partesList = partesList;
    }

    @XmlTransient
    public List<Tarea> getTareaList() {
        return tareaList;
    }

    public void setTareaList(List<Tarea> tareaList) {
        this.tareaList = tareaList;
    }

    @XmlTransient
    public List<Favorito> getFavoritoList() {
        return favoritoList;
    }

    public void setFavoritoList(List<Favorito> favoritoList) {
        this.favoritoList = favoritoList;
    }

    @XmlTransient
    public List<RegistroRevision> getRegistroRevisionList() {
        return registroRevisionList;
    }

    public void setRegistroRevisionList(List<RegistroRevision> registroRevisionList) {
        this.registroRevisionList = registroRevisionList;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public Componentemenu getMenu() {
        return menu;
    }

    public void setMenu(Componentemenu menu) {
        this.menu = menu;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public RuedaPalex getRuedaPalex() {
        return ruedaPalex;
    }

    public void setRuedaPalex(RuedaPalex ruedaPalex) {
        this.ruedaPalex = ruedaPalex;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuario != null ? usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Usuario[ usuario=" + usuario + " ]";
    }
    
}
