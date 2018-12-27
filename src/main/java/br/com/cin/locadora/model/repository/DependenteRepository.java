package br.com.cin.locadora.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.cin.locadora.model.Dependente;


public interface DependenteRepository extends CrudRepository<Dependente, Integer> {
		@Query("select d from Dependente d where d.idCliente.id = ?1")	
	   public List<Dependente> getDependentes(Integer pessoaid);
}
