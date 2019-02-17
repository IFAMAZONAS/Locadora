package br.com.cin.locadora.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cin.locadora.model.TipoMidia;
import br.com.cin.locadora.model.repository.TipoMidiaRepository;

@Service
public class TipoMidiaService {
	
	@Autowired	
	TipoMidiaRepository midiaRepository;
	
	public Iterable<TipoMidia> listarTodos(){
		return this.midiaRepository.findAll();
	}

	/***
	 * 
	 * @param idTipoMidia
	 * @return
	 */
	public void salvarTipoMidia(TipoMidia tipoMidia) {
		midiaRepository.save(tipoMidia);
	}
	
	public TipoMidia buscarPorId(Integer idTipoMidia) {		
		return this.midiaRepository.findById(idTipoMidia).get();
	}

	public void atualizar(TipoMidia tipoMidia) {
		this.midiaRepository.save(tipoMidia);
	}
}
