package br.com.cin.locadora.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.cin.locadora.model.Cliente;
import br.com.cin.locadora.model.ValoresLocacao;

public interface ValorLocacaoRepository extends CrudRepository<ValoresLocacao, Integer> {
	@Query("select p from ValoresLocacao p where p.descricao like %?1% ")
	List<ValoresLocacao> findValoresByName(String descricao);
}

