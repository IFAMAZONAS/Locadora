package br.com.cin.locadora.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.cin.locadora.model.Fornecedor;

public interface FornecedorRepository extends CrudRepository<Fornecedor, Integer> {

	@Query("select f from fornecedor f where f.razaosocial like %?1% ")
	List<Fornecedor> findPessoaByName(String razaosocial);
}
