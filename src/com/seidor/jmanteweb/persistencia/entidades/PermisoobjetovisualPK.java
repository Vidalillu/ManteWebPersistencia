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
import javax.persistence.Embeddable;

/**
 *
 * @author AMARTEL
 */
@Embeddable
public class PermisoobjetovisualPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "OBJETOVISUAL")
    private BigInteger objetovisual;
    @Basic(optional = false)
    @Column(name = "PERFIL")
    private String perfil;

    public PermisoobjetovisualPK() {
    }

    public PermisoobjetovisualPK(BigInteger objetovisual, String perfil) {
        this.objetovisual = objetovisual;
        this.perfil = perfil;
    }

    public BigInteger getObjetovisual() {
        return objetovisual;
    }

    public void setObjetovisual(BigInteger objetovisual) {
        this.objetovisual = objetovisual;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (objetovisual != null ? objetovisual.hashCode() : 0);
        hash += (perfil != null ? perfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PermisoobjetovisualPK)) {
            return false;
        }
        PermisoobjetovisualPK other = (PermisoobjetovisualPK) object;
        if ((this.objetovisual == null && other.objetovisual != null) || (this.objetovisual != null && !this.objetovisual.equals(other.objetovisual))) {
            return false;
        }
        if ((this.perfil == null && other.perfil != null) || (this.perfil != null && !this.perfil.equals(other.perfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.PermisoobjetovisualPK[ objetovisual=" + objetovisual + ", perfil=" + perfil + " ]";
    }
    
}
