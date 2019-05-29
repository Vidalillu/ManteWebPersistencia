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
public class InfoProgramaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "CLIENTE")
    private String cliente;
    @Basic(optional = false)
    @Column(name = "PROGRAMA")
    private String programa;

    public InfoProgramaPK() {
    }

    public InfoProgramaPK(String cliente, String programa) {
        this.cliente = cliente;
        this.programa = programa;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cliente != null ? cliente.hashCode() : 0);
        hash += (programa != null ? programa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InfoProgramaPK)) {
            return false;
        }
        InfoProgramaPK other = (InfoProgramaPK) object;
        if ((this.cliente == null && other.cliente != null) || (this.cliente != null && !this.cliente.equals(other.cliente))) {
            return false;
        }
        if ((this.programa == null && other.programa != null) || (this.programa != null && !this.programa.equals(other.programa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.InfoProgramaPK[ cliente=" + cliente + ", programa=" + programa + " ]";
    }
    
}
