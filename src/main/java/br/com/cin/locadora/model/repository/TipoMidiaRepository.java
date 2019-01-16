package br.com.cin.locadora.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cin.locadora.model.TipoMidia;


@Repository
@Transactional
public interface TipoMidiaRepository extends CrudRepository<TipoMidia, Integer> {

}
