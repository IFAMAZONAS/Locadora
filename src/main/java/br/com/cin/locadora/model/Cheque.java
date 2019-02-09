package br.com.cin.locadora.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author 2192525
 */
@Entity
@Table(name = "cheque")
@NamedQueries({
    @NamedQuery(name = "Cheque.findAll", query = "SELECT c FROM Cheque c")})
public class Cheque implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cheque")
    private Integer idCheque;
    @Column(name = "banco")
    private Integer banco;
    @Column(name = "agencia")
    private String agencia;
    @Column(name = "conta")
    private String conta;
    @Column(name = "numero_cheque")
    private String numeroCheque;
    @JoinColumn(name = "id_pagamento", referencedColumnName = "id_pagamento")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pagamento pagamento;

    public Cheque() {
    }

    public Cheque(Integer idCheque) {
        this.idCheque = idCheque;
    }

    public Integer getIdCheque() {
        return idCheque;
    }

    public void setIdCheque(Integer idCheque) {
        this.idCheque = idCheque;
    }

    public Integer getBanco() {
        return banco;
    }

    public void setBanco(Integer banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(String numeroCheque) {
        this.numeroCheque = numeroCheque;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCheque != null ? idCheque.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cheque)) {
            return false;
        }
        Cheque other = (Cheque) object;
        if ((this.idCheque == null && other.idCheque != null) || (this.idCheque != null && !this.idCheque.equals(other.idCheque))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.Cheque[ idCheque=" + idCheque + " ]";
    }
    
}

