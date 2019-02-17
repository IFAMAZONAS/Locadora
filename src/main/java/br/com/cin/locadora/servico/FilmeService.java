package br.com.cin.locadora.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cin.locadora.model.Filme;
import br.com.cin.locadora.model.repository.ClienteRepository;
import br.com.cin.locadora.model.repository.FilmeRepository;

@Service
public class FilmeService {

	@Autowired
	private FilmeRepository filmeRepository;

		
	/***
	 * 
	 * 
	 * 
	 * @param filme
	 * @return
	 */
	public boolean salvar(Filme filme) {
		if (filme.getTituloOriginal().isEmpty()) {
			return false;
		} else {
			this.filmeRepository.save(filme);
			return true;
		}

	}
	
	

}
