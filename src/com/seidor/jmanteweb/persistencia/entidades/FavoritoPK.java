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
public class FavoritoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "USUARIO")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "COMPONENTEMENU")
    private String componentemenu;

    public FavoritoPK() {
    }

    public FavoritoPK(String usuario, String componentemenu) {
        this.usuario = usuario;
        this.componentemenu = componentemenu;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getComponentemenu() {
        return componentemenu;
    }

    public void setComponentemenu(String componentemenu) {
        this.componentemenu = componentemenu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuario != null ? usuario.hashCode() : 0);
        hash += (componentemenu != null ? componentemenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FavoritoPK)) {
            return false;
        }
        FavoritoPK other = (FavoritoPK) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        if ((this.componentemenu == null && other.componentemenu != null) || (this.componentemenu != null && !this.componentemenu.equals(other.componentemenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.FavoritoPK[ usuario=" + usuario + ", componentemenu=" + componentemenu + " ]";
    }
    
}
