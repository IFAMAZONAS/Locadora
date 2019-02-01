package br.com.cin.locadora.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.cin.locadora.model.Genero;

public interface GeneroRepository extends PagingAndSortingRepository<Genero, Integer> {
	
	@Query("select g from Genero g where g.descricao like %?1% ")
	List<Genero> findGeneroByName(String descricao);
}
