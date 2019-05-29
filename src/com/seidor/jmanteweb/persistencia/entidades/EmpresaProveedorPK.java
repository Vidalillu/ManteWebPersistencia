/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author AMARTEL
 */
@Embeddable
public class EmpresaProveedorPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "PROVEEDOR")
    private String proveedor;

    public EmpresaProveedorPK() {
    }

    public EmpresaProveedorPK(String nombre, String proveedor) {
        this.nombre = nombre;
        this.proveedor = proveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombre != null ? nombre.hashCode() : 0);
        hash += (proveedor != null ? proveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpresaProveedorPK)) {
            return false;
        }
        EmpresaProveedorPK other = (EmpresaProveedorPK) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        if ((this.proveedor == null && other.proveedor != null) || (this.proveedor != null && !this.proveedor.equals(other.proveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.EmpresaProveedorPK[ nombre=" + nombre + ", proveedor=" + proveedor + " ]";
    }
    
}
