package br.com.cin.locadora.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.cin.locadora.model.Locacao;
@Repository
public interface LocacaoRepository extends CrudRepository<Locacao, Integer> {

}
