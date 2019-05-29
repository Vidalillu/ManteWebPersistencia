/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.entidades;

import java.io.Serializable;
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
@Table(name = "CLIENTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c")
    , @NamedQuery(name = "Clientes.findByCliente", query = "SELECT c FROM Clientes c WHERE c.cliente = :cliente")
    , @NamedQuery(name = "Clientes.findByNombreAlternativo", query = "SELECT c FROM Clientes c WHERE c.nombreAlternativo = :nombreAlternativo")
    , @NamedQuery(name = "Clientes.findByDireccion", query = "SELECT c FROM Clientes c WHERE c.direccion = :direccion")
    , @NamedQuery(name = "Clientes.findByPoblacion", query = "SELECT c FROM Clientes c WHERE c.poblacion = :poblacion")
    , @NamedQuery(name = "Clientes.findByProvincia", query = "SELECT c FROM Clientes c WHERE c.provincia = :provincia")
    , @NamedQuery(name = "Clientes.findByPais", query = "SELECT c FROM Clientes c WHERE c.pais = :pais")
    , @NamedQuery(name = "Clientes.findByTelefono", query = "SELECT c FROM Clientes c WHERE c.telefono = :telefono")
    , @NamedQuery(name = "Clientes.findByEmail", query = "SELECT c FROM Clientes c WHERE c.email = :email")
    , @NamedQuery(name = "Clientes.findByComercial", query = "SELECT c FROM Clientes c WHERE c.comercial = :comercial")
    , @NamedQuery(name = "Clientes.findByObservaciones", query = "SELECT c FROM Clientes c WHERE c.observaciones = :observaciones")
    , @NamedQuery(name = "Clientes.findByRutaDoc", query = "SELECT c FROM Clientes c WHERE c.rutaDoc = :rutaDoc")
    , @NamedQuery(name = "Clientes.findByRutaLogo", query = "SELECT c FROM Clientes c WHERE c.rutaLogo = :rutaLogo")
    , @NamedQuery(name = "Clientes.findByResponsableet", query = "SELECT c FROM Clientes c WHERE c.responsableet = :responsableet")
    , @NamedQuery(name = "Clientes.findByVersion", query = "SELECT c FROM Clientes c WHERE c.version = :version")
    , @NamedQuery(name = "Clientes.findByAtender", query = "SELECT c FROM Clientes c WHERE c.atender = :atender")
    , @NamedQuery(name = "Clientes.findByErp", query = "SELECT c FROM Clientes c WHERE c.erp = :erp")
    , @NamedQuery(name = "Clientes.findByProyectoSapMante", query = "SELECT c FROM Clientes c WHERE c.proyectoSapMante = :proyectoSapMante")
    , @NamedQuery(name = "Clientes.findByProyectoSap", query = "SELECT c FROM Clientes c WHERE c.proyectoSap = :proyectoSap")
    , @NamedQuery(name = "Clientes.findByConsultarpalex", query = "SELECT c FROM Clientes c WHERE c.consultarpalex = :consultarpalex")
    , @NamedQuery(name = "Clientes.findByKm", query = "SELECT c FROM Clientes c WHERE c.km = :km")
    , @NamedQuery(name = "Clientes.findByLlegar", query = "SELECT c FROM Clientes c WHERE c.llegar = :llegar")
    , @NamedQuery(name = "Clientes.findByFacturargastos", query = "SELECT c FROM Clientes c WHERE c.facturargastos = :facturargastos")
    , @NamedQuery(name = "Clientes.findByFechacreacion", query = "SELECT c FROM Clientes c WHERE c.fechacreacion = :fechacreacion")
    , @NamedQuery(name = "Clientes.findByObsoleto", query = "SELECT c FROM Clientes c WHERE c.obsoleto = :obsoleto")
    , @NamedQuery(name = "Clientes.findByRutaBackup", query = "SELECT c FROM Clientes c WHERE c.rutaBackup = :rutaBackup")
    , @NamedQuery(name = "Clientes.findByRutaBackupAlt", query = "SELECT c FROM Clientes c WHERE c.rutaBackupAlt = :rutaBackupAlt")
    , @NamedQuery(name = "Clientes.findByRutaFuentes", query = "SELECT c FROM Clientes c WHERE c.rutaFuentes = :rutaFuentes")
    , @NamedQuery(name = "Clientes.findByFechaCodigo", query = "SELECT c FROM Clientes c WHERE c.fechaCodigo = :fechaCodigo")
    , @NamedQuery(name = "Clientes.findByEmpresaColaboradora", query = "SELECT c FROM Clientes c WHERE c.empresaColaboradora = :empresaColaboradora")
    , @NamedQuery(name = "Clientes.findByCodigoproveedor", query = "SELECT c FROM Clientes c WHERE c.codigoproveedor = :codigoproveedor")
    , @NamedQuery(name = "Clientes.findByRutaLocal", query = "SELECT c FROM Clientes c WHERE c.rutaLocal = :rutaLocal")
    , @NamedQuery(name = "Clientes.findByRevisionCodigo", query = "SELECT c FROM Clientes c WHERE c.revisionCodigo = :revisionCodigo")
    , @NamedQuery(name = "Clientes.findByUltimaRevision", query = "SELECT c FROM Clientes c WHERE c.ultimaRevision = :ultimaRevision")
    , @NamedQuery(name = "Clientes.findByFechaArranque", query = "SELECT c FROM Clientes c WHERE c.fechaArranque = :fechaArranque")
    , @NamedQuery(name = "Clientes.findByFechaSoporte", query = "SELECT c FROM Clientes c WHERE c.fechaSoporte = :fechaSoporte")
    , @NamedQuery(name = "Clientes.findByAtiendeSoporte", query = "SELECT c FROM Clientes c WHERE c.atiendeSoporte = :atiendeSoporte")
    , @NamedQuery(name = "Clientes.findByIdCliente", query = "SELECT c FROM Clientes c WHERE c.idCliente = :idCliente")})
public class Clientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CLIENTE")
    private String cliente;
    @Column(name = "NOMBRE_ALTERNATIVO")
    private String nombreAlternativo;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "POBLACION")
    private String poblacion;
    @Column(name = "PROVINCIA")
    private String provincia;
    @Column(name = "PAIS")
    private String pais;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "COMERCIAL")
    private String comercial;
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Column(name = "RUTA_DOC")
    private String rutaDoc;
    @Column(name = "RUTA_LOGO")
    private String rutaLogo;
    @Column(name = "RESPONSABLEET")
    private String responsableet;
    @Column(name = "VERSION")
    private BigInteger version;
    @Basic(optional = false)
    @Column(name = "ATENDER")
    private BigInteger atender;
    @Column(name = "ERP")
    private String erp;
    @Column(name = "PROYECTO_SAP_MANTE")
    private String proyectoSapMante;
    @Column(name = "PROYECTO_SAP")
    private String proyectoSap;
    @Basic(optional = false)
    @Column(name = "CONSULTARPALEX")
    private BigInteger consultarpalex;
    @Column(name = "KM")
    private Integer km;
    @Column(name = "LLEGAR")
    private String llegar;
    @Basic(optional = false)
    @Column(name = "FACTURARGASTOS")
    private String facturargastos;
    @Column(name = "FECHACREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    @Column(name = "OBSOLETO")
    private BigInteger obsoleto;
    @Column(name = "RUTA_BACKUP")
    private String rutaBackup;
    @Column(name = "RUTA_BACKUP_ALT")
    private String rutaBackupAlt;
    @Column(name = "RUTA_FUENTES")
    private String rutaFuentes;
    @Column(name = "FECHA_CODIGO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCodigo;
    @Column(name = "EMPRESA_COLABORADORA")
    private String empresaColaboradora;
    @Column(name = "CODIGOPROVEEDOR")
    private String codigoproveedor;
    @Column(name = "RUTA_LOCAL")
    private String rutaLocal;
    @Basic(optional = false)
    @Column(name = "REVISION_CODIGO")
    private BigInteger revisionCodigo;
    @Column(name = "ULTIMA_REVISION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaRevision;
    @Column(name = "FECHA_ARRANQUE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaArranque;
    @Column(name = "FECHA_SOPORTE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSoporte;
    @Column(name = "ATIENDE_SOPORTE")
    private BigInteger atiendeSoporte;
    @Basic(optional = false)
    @Column(name = "ID_CLIENTE")
    private long idCliente;
    @OneToOne(mappedBy = "cliente")
    private ConexionRemota conexionRemota;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientes")
    private List<Responsabilidad> responsabilidadList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Peticiones> peticionesList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cliente")
    private InfoBd infoBd;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientes")
    private List<InfoPrograma> infoProgramaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientes")
    private List<InfoModi> infoModiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Partes> partesList;
    @OneToMany(mappedBy = "cliente")
    private List<Tarea> tareaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<RegistroRevision> registroRevisionList;
    @OneToMany(mappedBy = "cliente")
    private List<Usuario> usuarioList;
    @JoinColumn(name = "ID_BONOMODIS_ACTIVO", referencedColumnName = "ID_PETICION")
    @ManyToOne
    private Bonomodis idBonomodisActivo;
    @JoinColumn(name = "TIPO_CLIENTE", referencedColumnName = "NOMBRE")
    @ManyToOne
    private EmpresaColaboradora tipoCliente;
    @JoinColumn(name = "ESTADO", referencedColumnName = "ESTADO")
    @ManyToOne
    private EstadosCliente estado;
    @JoinColumn(name = "ID_SERVICIOMANTE_ACTIVO", referencedColumnName = "ID_PETICION")
    @ManyToOne
    private ServiciosMante idServiciomanteActivo;
    @JoinColumn(name = "TARIFA", referencedColumnName = "TARIFA")
    @ManyToOne
    private Tarifa tarifa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Contactos> contactosList;

    public Clientes() {
    }

    public Clientes(String cliente) {
        this.cliente = cliente;
    }

    public Clientes(String cliente, BigInteger atender, BigInteger consultarpalex, String facturargastos, BigInteger revisionCodigo, long idCliente) {
        this.cliente = cliente;
        this.atender = atender;
        this.consultarpalex = consultarpalex;
        this.facturargastos = facturargastos;
        this.revisionCodigo = revisionCodigo;
        this.idCliente = idCliente;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getNombreAlternativo() {
        return nombreAlternativo;
    }

    public void setNombreAlternativo(String nombreAlternativo) {
        this.nombreAlternativo = nombreAlternativo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComercial() {
        return comercial;
    }

    public void setComercial(String comercial) {
        this.comercial = comercial;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getRutaDoc() {
        return rutaDoc;
    }

    public void setRutaDoc(String rutaDoc) {
        this.rutaDoc = rutaDoc;
    }

    public String getRutaLogo() {
        return rutaLogo;
    }

    public void setRutaLogo(String rutaLogo) {
        this.rutaLogo = rutaLogo;
    }

    public String getResponsableet() {
        return responsableet;
    }

    public void setResponsableet(String responsableet) {
        this.responsableet = responsableet;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public BigInteger getAtender() {
        return atender;
    }

    public void setAtender(BigInteger atender) {
        this.atender = atender;
    }

    public String getErp() {
        return erp;
    }

    public void setErp(String erp) {
        this.erp = erp;
    }

    public String getProyectoSapMante() {
        return proyectoSapMante;
    }

    public void setProyectoSapMante(String proyectoSapMante) {
        this.proyectoSapMante = proyectoSapMante;
    }

    public String getProyectoSap() {
        return proyectoSap;
    }

    public void setProyectoSap(String proyectoSap) {
        this.proyectoSap = proyectoSap;
    }

    public BigInteger getConsultarpalex() {
        return consultarpalex;
    }

    public void setConsultarpalex(BigInteger consultarpalex) {
        this.consultarpalex = consultarpalex;
    }

    public Integer getKm() {
        return km;
    }

    public void setKm(Integer km) {
        this.km = km;
    }

    public String getLlegar() {
        return llegar;
    }

    public void setLlegar(String llegar) {
        this.llegar = llegar;
    }

    public String getFacturargastos() {
        return facturargastos;
    }

    public void setFacturargastos(String facturargastos) {
        this.facturargastos = facturargastos;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public BigInteger getObsoleto() {
        return obsoleto;
    }

    public void setObsoleto(BigInteger obsoleto) {
        this.obsoleto = obsoleto;
    }

    public String getRutaBackup() {
        return rutaBackup;
    }

    public void setRutaBackup(String rutaBackup) {
        this.rutaBackup = rutaBackup;
    }

    public String getRutaBackupAlt() {
        return rutaBackupAlt;
    }

    public void setRutaBackupAlt(String rutaBackupAlt) {
        this.rutaBackupAlt = rutaBackupAlt;
    }

    public String getRutaFuentes() {
        return rutaFuentes;
    }

    public void setRutaFuentes(String rutaFuentes) {
        this.rutaFuentes = rutaFuentes;
    }

    public Date getFechaCodigo() {
        return fechaCodigo;
    }

    public void setFechaCodigo(Date fechaCodigo) {
        this.fechaCodigo = fechaCodigo;
    }

    public String getEmpresaColaboradora() {
        return empresaColaboradora;
    }

    public void setEmpresaColaboradora(String empresaColaboradora) {
        this.empresaColaboradora = empresaColaboradora;
    }

    public String getCodigoproveedor() {
        return codigoproveedor;
    }

    public void setCodigoproveedor(String codigoproveedor) {
        this.codigoproveedor = codigoproveedor;
    }

    public String getRutaLocal() {
        return rutaLocal;
    }

    public void setRutaLocal(String rutaLocal) {
        this.rutaLocal = rutaLocal;
    }

    public BigInteger getRevisionCodigo() {
        return revisionCodigo;
    }

    public void setRevisionCodigo(BigInteger revisionCodigo) {
        this.revisionCodigo = revisionCodigo;
    }

    public Date getUltimaRevision() {
        return ultimaRevision;
    }

    public void setUltimaRevision(Date ultimaRevision) {
        this.ultimaRevision = ultimaRevision;
    }

    public Date getFechaArranque() {
        return fechaArranque;
    }

    public void setFechaArranque(Date fechaArranque) {
        this.fechaArranque = fechaArranque;
    }

    public Date getFechaSoporte() {
        return fechaSoporte;
    }

    public void setFechaSoporte(Date fechaSoporte) {
        this.fechaSoporte = fechaSoporte;
    }

    public BigInteger getAtiendeSoporte() {
        return atiendeSoporte;
    }

    public void setAtiendeSoporte(BigInteger atiendeSoporte) {
        this.atiendeSoporte = atiendeSoporte;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public ConexionRemota getConexionRemota() {
        return conexionRemota;
    }

    public void setConexionRemota(ConexionRemota conexionRemota) {
        this.conexionRemota = conexionRemota;
    }

    @XmlTransient
    public List<Responsabilidad> getResponsabilidadList() {
        return responsabilidadList;
    }

    public void setResponsabilidadList(List<Responsabilidad> responsabilidadList) {
        this.responsabilidadList = responsabilidadList;
    }

    @XmlTransient
    public List<Peticiones> getPeticionesList() {
        return peticionesList;
    }

    public void setPeticionesList(List<Peticiones> peticionesList) {
        this.peticionesList = peticionesList;
    }

    public InfoBd getInfoBd() {
        return infoBd;
    }

    public void setInfoBd(InfoBd infoBd) {
        this.infoBd = infoBd;
    }

    @XmlTransient
    public List<InfoPrograma> getInfoProgramaList() {
        return infoProgramaList;
    }

    public void setInfoProgramaList(List<InfoPrograma> infoProgramaList) {
        this.infoProgramaList = infoProgramaList;
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
    public List<RegistroRevision> getRegistroRevisionList() {
        return registroRevisionList;
    }

    public void setRegistroRevisionList(List<RegistroRevision> registroRevisionList) {
        this.registroRevisionList = registroRevisionList;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public Bonomodis getIdBonomodisActivo() {
        return idBonomodisActivo;
    }

    public void setIdBonomodisActivo(Bonomodis idBonomodisActivo) {
        this.idBonomodisActivo = idBonomodisActivo;
    }

    public EmpresaColaboradora getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(EmpresaColaboradora tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public EstadosCliente getEstado() {
        return estado;
    }

    public void setEstado(EstadosCliente estado) {
        this.estado = estado;
    }

    public ServiciosMante getIdServiciomanteActivo() {
        return idServiciomanteActivo;
    }

    public void setIdServiciomanteActivo(ServiciosMante idServiciomanteActivo) {
        this.idServiciomanteActivo = idServiciomanteActivo;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    @XmlTransient
    public List<Contactos> getContactosList() {
        return contactosList;
    }

    public void setContactosList(List<Contactos> contactosList) {
        this.contactosList = contactosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cliente != null ? cliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.cliente == null && other.cliente != null) || (this.cliente != null && !this.cliente.equals(other.cliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Clientes[ cliente=" + cliente + " ]";
    }
    
}
