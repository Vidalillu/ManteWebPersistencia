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
public class OrdencomponentePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "MENU")
    private String menu;
    @Basic(optional = false)
    @Column(name = "COMPONENTE")
    private String componente;

    public OrdencomponentePK() {
    }

    public OrdencomponentePK(String menu, String componente) {
        this.menu = menu;
        this.componente = componente;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (menu != null ? menu.hashCode() : 0);
        hash += (componente != null ? componente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdencomponentePK)) {
            return false;
        }
        OrdencomponentePK other = (OrdencomponentePK) object;
        if ((this.menu == null && other.menu != null) || (this.menu != null && !this.menu.equals(other.menu))) {
            return false;
        }
        if ((this.componente == null && other.componente != null) || (this.componente != null && !this.componente.equals(other.componente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.OrdencomponentePK[ menu=" + menu + ", componente=" + componente + " ]";
    }
    
}
