package br.com.cin.locadora.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.cin.locadora.model.Filme;


@Repository
public interface FilmeRepository extends PagingAndSortingRepository<Filme, Integer> {

}
