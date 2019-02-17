package br.com.cin.locadora.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.cin.locadora.model.Cliente;
import br.com.cin.locadora.model.Filme;


@Repository
public interface FilmeRepository extends PagingAndSortingRepository<Filme, Integer> {
	@Query("select f from Filme f where upper(f.tituloPortugues) like %?1% ")
	List<Filme> findFilmeByName(String nome);
}
