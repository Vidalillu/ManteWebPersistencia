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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AMARTEL
 */
@Entity
@Table(name = "INFO_BD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InfoBd.findAll", query = "SELECT i FROM InfoBd i")
    , @NamedQuery(name = "InfoBd.findByVersionbd", query = "SELECT i FROM InfoBd i WHERE i.versionbd = :versionbd")
    , @NamedQuery(name = "InfoBd.findByUsuario", query = "SELECT i FROM InfoBd i WHERE i.usuario = :usuario")
    , @NamedQuery(name = "InfoBd.findByPassword", query = "SELECT i FROM InfoBd i WHERE i.password = :password")
    , @NamedQuery(name = "InfoBd.findByOrcl", query = "SELECT i FROM InfoBd i WHERE i.orcl = :orcl")
    , @NamedQuery(name = "InfoBd.findByVersion", query = "SELECT i FROM InfoBd i WHERE i.version = :version")
    , @NamedQuery(name = "InfoBd.findById", query = "SELECT i FROM InfoBd i WHERE i.id = :id")
    , @NamedQuery(name = "InfoBd.findByServidorseidor", query = "SELECT i FROM InfoBd i WHERE i.servidorseidor = :servidorseidor")
    , @NamedQuery(name = "InfoBd.findByNuestro", query = "SELECT i FROM InfoBd i WHERE i.nuestro = :nuestro")})
public class InfoBd implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "VERSIONBD")
    private String versionbd;
    @Column(name = "USUARIO")
    private String usuario;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "ORCL")
    private String orcl;
    @Column(name = "VERSION")
    private BigInteger version;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "SERVIDORSEIDOR")
    private String servidorseidor;
    @Column(name = "NUESTRO")
    private Short nuestro;
    @JoinColumn(name = "CLIENTE", referencedColumnName = "CLIENTE")
    @OneToOne(optional = false)
    private Clientes cliente;
    @JoinColumn(name = "TIPO_BD", referencedColumnName = "TIPO_BD")
    @ManyToOne
    private TipoBd tipoBd;

    public InfoBd() {
    }

    public InfoBd(BigDecimal id) {
        this.id = id;
    }

    public String getVersionbd() {
        return versionbd;
    }

    public void setVersionbd(String versionbd) {
        this.versionbd = versionbd;
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

    public String getOrcl() {
        return orcl;
    }

    public void setOrcl(String orcl) {
        this.orcl = orcl;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getServidorseidor() {
        return servidorseidor;
    }

    public void setServidorseidor(String servidorseidor) {
        this.servidorseidor = servidorseidor;
    }

    public Short getNuestro() {
        return nuestro;
    }

    public void setNuestro(Short nuestro) {
        this.nuestro = nuestro;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public TipoBd getTipoBd() {
        return tipoBd;
    }

    public void setTipoBd(TipoBd tipoBd) {
        this.tipoBd = tipoBd;
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
        if (!(object instanceof InfoBd)) {
            return false;
        }
        InfoBd other = (InfoBd) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.InfoBd[ id=" + id + " ]";
    }
    
}
