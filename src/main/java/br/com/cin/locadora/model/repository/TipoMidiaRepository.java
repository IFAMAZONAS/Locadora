package br.com.cin.locadora.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cin.locadora.model.TipoMidia;


@Repository
@Transactional
public interface TipoMidiaRepository extends PagingAndSortingRepository<TipoMidia, Integer> {

	@Query("select tm from TipoMidia tm where tm.descricao like %?1% ")
	List<TipoMidia> findTipoMidiaByName(String descricao);
}

