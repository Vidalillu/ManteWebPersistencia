/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "EMPRESA_PROVEEDOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpresaProveedor.findAll", query = "SELECT e FROM EmpresaProveedor e")
    , @NamedQuery(name = "EmpresaProveedor.findByNombre", query = "SELECT e FROM EmpresaProveedor e WHERE e.empresaProveedorPK.nombre = :nombre")
    , @NamedQuery(name = "EmpresaProveedor.findByEnviarparte", query = "SELECT e FROM EmpresaProveedor e WHERE e.enviarparte = :enviarparte")
    , @NamedQuery(name = "EmpresaProveedor.findByVersion", query = "SELECT e FROM EmpresaProveedor e WHERE e.version = :version")
    , @NamedQuery(name = "EmpresaProveedor.findByProveedor", query = "SELECT e FROM EmpresaProveedor e WHERE e.empresaProveedorPK.proveedor = :proveedor")
    , @NamedQuery(name = "EmpresaProveedor.findByEnviarseguimiento", query = "SELECT e FROM EmpresaProveedor e WHERE e.enviarseguimiento = :enviarseguimiento")})
public class EmpresaProveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EmpresaProveedorPK empresaProveedorPK;
    @Basic(optional = false)
    @Column(name = "ENVIARPARTE")
    private BigInteger enviarparte;
    @Column(name = "VERSION")
    private BigInteger version;
    @Basic(optional = false)
    @Column(name = "ENVIARSEGUIMIENTO")
    private short enviarseguimiento;
    @JoinColumn(name = "NOMBRE", referencedColumnName = "NOMBRE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private EmpresaColaboradora empresaColaboradora;

    public EmpresaProveedor() {
    }

    public EmpresaProveedor(EmpresaProveedorPK empresaProveedorPK) {
        this.empresaProveedorPK = empresaProveedorPK;
    }

    public EmpresaProveedor(EmpresaProveedorPK empresaProveedorPK, BigInteger enviarparte, short enviarseguimiento) {
        this.empresaProveedorPK = empresaProveedorPK;
        this.enviarparte = enviarparte;
        this.enviarseguimiento = enviarseguimiento;
    }

    public EmpresaProveedor(String nombre, String proveedor) {
        this.empresaProveedorPK = new EmpresaProveedorPK(nombre, proveedor);
    }

    public EmpresaProveedorPK getEmpresaProveedorPK() {
        return empresaProveedorPK;
    }

    public void setEmpresaProveedorPK(EmpresaProveedorPK empresaProveedorPK) {
        this.empresaProveedorPK = empresaProveedorPK;
    }

    public BigInteger getEnviarparte() {
        return enviarparte;
    }

    public void setEnviarparte(BigInteger enviarparte) {
        this.enviarparte = enviarparte;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public short getEnviarseguimiento() {
        return enviarseguimiento;
    }

    public void setEnviarseguimiento(short enviarseguimiento) {
        this.enviarseguimiento = enviarseguimiento;
    }

    public EmpresaColaboradora getEmpresaColaboradora() {
        return empresaColaboradora;
    }

    public void setEmpresaColaboradora(EmpresaColaboradora empresaColaboradora) {
        this.empresaColaboradora = empresaColaboradora;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empresaProveedorPK != null ? empresaProveedorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpresaProveedor)) {
            return false;
        }
        EmpresaProveedor other = (EmpresaProveedor) object;
        if ((this.empresaProveedorPK == null && other.empresaProveedorPK != null) || (this.empresaProveedorPK != null && !this.empresaProveedorPK.equals(other.empresaProveedorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.EmpresaProveedor[ empresaProveedorPK=" + empresaProveedorPK + " ]";
    }
    
}
