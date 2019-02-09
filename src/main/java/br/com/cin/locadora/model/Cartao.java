package br.com.cin.locadora.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author 2192525
 */
@Entity
@Table(name = "cartao")
@NamedQueries({
    @NamedQuery(name = "Cartao.findAll", query = "SELECT c FROM Cartao c")})
public class Cartao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cartao")
    private Integer idCartao;
    @Column(name = "operadora")
    private String operadora;
    @Column(name = "numero")
    private String numero;
    @Column(name = "data_autorizacao")
    @Temporal(TemporalType.DATE)
    private Date dataAutorizacao;
    @JoinColumn(name = "id_pagamento", referencedColumnName = "id_pagamento")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pagamento pagamento;

    public Cartao() {
    }

    public Cartao(Integer idCartao) {
        this.idCartao = idCartao;
    }

    public Integer getIdCartao() {
        return idCartao;
    }

    public void setIdCartao(Integer idCartao) {
        this.idCartao = idCartao;
    }

    public String getOperadora() {
        return operadora;
    }

    public void setOperadora(String operadora) {
        this.operadora = operadora;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getDataAutorizacao() {
        return dataAutorizacao;
    }

    public void setDataAutorizacao(Date dataAutorizacao) {
        this.dataAutorizacao = dataAutorizacao;
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
        hash += (idCartao != null ? idCartao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cartao)) {
            return false;
        }
        Cartao other = (Cartao) object;
        if ((this.idCartao == null && other.idCartao != null) || (this.idCartao != null && !this.idCartao.equals(other.idCartao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.Cartao[ idCartao=" + idCartao + " ]";
    }
    
}
