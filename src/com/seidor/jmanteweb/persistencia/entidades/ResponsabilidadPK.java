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
public class ResponsabilidadPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "CLIENTE")
    private String cliente;
    @Basic(optional = false)
    @Column(name = "USUARIO_MANTE")
    private String usuarioMante;

    public ResponsabilidadPK() {
    }

    public ResponsabilidadPK(String cliente, String usuarioMante) {
        this.cliente = cliente;
        this.usuarioMante = usuarioMante;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getUsuarioMante() {
        return usuarioMante;
    }

    public void setUsuarioMante(String usuarioMante) {
        this.usuarioMante = usuarioMante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cliente != null ? cliente.hashCode() : 0);
        hash += (usuarioMante != null ? usuarioMante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResponsabilidadPK)) {
            return false;
        }
        ResponsabilidadPK other = (ResponsabilidadPK) object;
        if ((this.cliente == null && other.cliente != null) || (this.cliente != null && !this.cliente.equals(other.cliente))) {
            return false;
        }
        if ((this.usuarioMante == null && other.usuarioMante != null) || (this.usuarioMante != null && !this.usuarioMante.equals(other.usuarioMante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.ResponsabilidadPK[ cliente=" + cliente + ", usuarioMante=" + usuarioMante + " ]";
    }
    
}
