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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AMARTEL
 */
@Entity
@Table(name = "SECUENCIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Secuencias.findAll", query = "SELECT s FROM Secuencias s")
    , @NamedQuery(name = "Secuencias.findBySecuencia", query = "SELECT s FROM Secuencias s WHERE s.secuencia = :secuencia")
    , @NamedQuery(name = "Secuencias.findByValorMin", query = "SELECT s FROM Secuencias s WHERE s.valorMin = :valorMin")
    , @NamedQuery(name = "Secuencias.findByValorMax", query = "SELECT s FROM Secuencias s WHERE s.valorMax = :valorMax")
    , @NamedQuery(name = "Secuencias.findByValorActual", query = "SELECT s FROM Secuencias s WHERE s.valorActual = :valorActual")
    , @NamedQuery(name = "Secuencias.findByIncremento", query = "SELECT s FROM Secuencias s WHERE s.incremento = :incremento")})
public class Secuencias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SECUENCIA")
    private String secuencia;
    @Column(name = "VALOR_MIN")
    private BigInteger valorMin;
    @Column(name = "VALOR_MAX")
    private BigInteger valorMax;
    @Column(name = "VALOR_ACTUAL")
    private BigInteger valorActual;
    @Basic(optional = false)
    @Column(name = "INCREMENTO")
    private int incremento;

    public Secuencias() {
    }

    public Secuencias(String secuencia) {
        this.secuencia = secuencia;
    }

    public Secuencias(String secuencia, int incremento) {
        this.secuencia = secuencia;
        this.incremento = incremento;
    }

    public String getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(String secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getValorMin() {
        return valorMin;
    }

    public void setValorMin(BigInteger valorMin) {
        this.valorMin = valorMin;
    }

    public BigInteger getValorMax() {
        return valorMax;
    }

    public void setValorMax(BigInteger valorMax) {
        this.valorMax = valorMax;
    }

    public BigInteger getValorActual() {
        return valorActual;
    }

    public void setValorActual(BigInteger valorActual) {
        this.valorActual = valorActual;
    }

    public int getIncremento() {
        return incremento;
    }

    public void setIncremento(int incremento) {
        this.incremento = incremento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secuencia != null ? secuencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Secuencias)) {
            return false;
        }
        Secuencias other = (Secuencias) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seidor.jmanteweb.persistencia.entidades.Secuencias[ secuencia=" + secuencia + " ]";
    }
    
}
