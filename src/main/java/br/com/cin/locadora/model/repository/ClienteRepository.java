package br.com.cin.locadora.model.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.cin.locadora.model.Cliente;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Integer> {

	@Query("select p from Cliente p where upper(p.nome) like %?1% ")
	List<Cliente> findPessoaByNameALL(String nome);
	
	
	
	

}
