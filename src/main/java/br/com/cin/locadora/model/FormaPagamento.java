package br.com.cin.locadora.model;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "forma_pagamento")
public class FormaPagamento {

		private static final long serialVersionUID = 1L;
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Basic(optional = false)
	    @Column(name = "id_form_pagamento")
	    private Integer idFormaPagamento;
	    @Column(name = "descricao")
	    private String descricao;
	    
	    
		public Integer getIdFormaPagamento() {
			return idFormaPagamento;
		}
		public void setIdFormaPagamento(Integer idFormaPagamento) {
			this.idFormaPagamento = idFormaPagamento;
		}
		public String getDescricao() {
			return descricao;
		}
		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
	    
	    
	
}
