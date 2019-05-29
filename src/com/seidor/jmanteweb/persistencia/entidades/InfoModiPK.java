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
public class InfoModiPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDMODI")
    private BigInteger idmodi;
    @Basic(optional = false)
    @Column(name = "CLIENTE")
    private String cliente;

    public InfoModiPK() {
    }

    public InfoModiPK(BigInteger idmodi, String cliente) {
        this.idmodi = idmodi;
        this.cliente = cliente;
    }

    public BigInteger getIdmodi() {
        return idmodi;
    }

    public void setIdmodi(BigInteger idmodi) {
        this.idmodi = idmodi;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmodi != null ? idmodi.hashCode() : 0);
        hash += (cliente != null ? cliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InfoModiPK)) {
            return false;
        }
        InfoModiPK other = (InfoModiPK) object;
        if ((this.idmodi == null && other.idmodi != null) || (this.idmodi != null && !this.idmodi.equals(other.idmodi))) {
            return false;
        }
        if ((this.cliente == null && other.cliente != null) || (this.cliente != null && !this.cliente.equals(other.cliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.InfoModiPK[ idmodi=" + idmodi + ", cliente=" + cliente + " ]";
    }
    
}
