package br.com.cin.locadora.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author 2192525
 */
@Entity
@Table(name = "pagamento")
@NamedQueries({
    @NamedQuery(name = "Pagamento.findAll", query = "SELECT p FROM Pagamento p")})
public class Pagamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pagamento")
    private Integer idPagamento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "valor_desconto")
    private Double valorDesconto;
    @Column(name = "valor_multa")
    private Double valorMulta;
    @Column(name = "valor_total")
    private Double valorTotal;
    
    @JoinColumn(name = "id_forma_pagamento", referencedColumnName = "id_form_pagamento")
    @ManyToOne(fetch = FetchType.LAZY)
    private FormaPagamento formaPagamento;
    
    @JoinColumn(name = "id_locacao", referencedColumnName = "id_locacao")
    @ManyToOne(fetch = FetchType.LAZY)
    private Locacao locacao;

    @OneToMany(mappedBy = "pagamento",cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Cheque> chequeList = new ArrayList<Cheque>();
    
    @OneToMany(mappedBy = "pagamento", cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    private List<Cartao> cartaoList = new ArrayList<Cartao>();
    
    public Pagamento() {
    }

    public Pagamento(Integer idPagamento) {
        this.idPagamento = idPagamento;
    }

    public Integer getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Integer idPagamento) {
        this.idPagamento = idPagamento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(Double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public Double getValorMulta() {
        return valorMulta;
    }

    public void setValorMulta(Double valorMulta) {
        this.valorMulta = valorMulta;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Locacao getLocacao() {
        return locacao;
    }

    public void setLocacao(Locacao locacao) {
        this.locacao = locacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPagamento != null ? idPagamento.hashCode() : 0);
        return hash;
    }
    
    public void setCartaoList(List<Cartao> cartaoList) {
		this.cartaoList = cartaoList;
	}
    
    public List<Cartao> getCartaoList() {
		return cartaoList;
	}
    
    public void setChequeList(List<Cheque> chequeList) {
		this.chequeList = chequeList;
	}
    
    public List<Cheque> getChequeList() {
		return chequeList;
	}

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pagamento)) {
            return false;
        }
        Pagamento other = (Pagamento) object;
        if ((this.idPagamento == null && other.idPagamento != null) || (this.idPagamento != null && !this.idPagamento.equals(other.idPagamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.Pagamento[ idPagamento=" + idPagamento + " ]";
    }
    
}
