package br.com.cin.locadora.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.cin.locadora.model.Fornecedor;

public interface FornecedorRepository extends PagingAndSortingRepository<Fornecedor, Integer> {

	@Query("select f from fornecedor f where f.razaosocial like %?1% ")
	List<Fornecedor> findPessoaByName(String razaosocial);
	
	@Query("select f from fornecedor f where f.cnpj = ?1 ")
	List<Fornecedor> findCNPJ(String cnpj);
}
