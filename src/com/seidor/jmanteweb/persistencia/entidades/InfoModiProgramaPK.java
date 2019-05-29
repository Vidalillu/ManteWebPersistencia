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
public class InfoModiProgramaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDMODI")
    private BigInteger idmodi;
    @Basic(optional = false)
    @Column(name = "PROGRAMA")
    private String programa;

    public InfoModiProgramaPK() {
    }

    public InfoModiProgramaPK(BigInteger idmodi, String programa) {
        this.idmodi = idmodi;
        this.programa = programa;
    }

    public BigInteger getIdmodi() {
        return idmodi;
    }

    public void setIdmodi(BigInteger idmodi) {
        this.idmodi = idmodi;
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
        hash += (idmodi != null ? idmodi.hashCode() : 0);
        hash += (programa != null ? programa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InfoModiProgramaPK)) {
            return false;
        }
        InfoModiProgramaPK other = (InfoModiProgramaPK) object;
        if ((this.idmodi == null && other.idmodi != null) || (this.idmodi != null && !this.idmodi.equals(other.idmodi))) {
            return false;
        }
        if ((this.programa == null && other.programa != null) || (this.programa != null && !this.programa.equals(other.programa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.InfoModiProgramaPK[ idmodi=" + idmodi + ", programa=" + programa + " ]";
    }
    
}
