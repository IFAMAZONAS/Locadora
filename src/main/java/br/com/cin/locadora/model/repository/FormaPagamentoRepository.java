package br.com.cin.locadora.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.cin.locadora.model.FormaPagamento;

public interface FormaPagamentoRepository extends PagingAndSortingRepository<FormaPagamento, Integer> {

	@Query("select fp from FormaPagamento fp where fp.descricao like %?1% ")
	List<FormaPagamento> findFormaPagByName(String descricao);
}
