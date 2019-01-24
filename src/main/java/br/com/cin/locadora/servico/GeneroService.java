package br.com.cin.locadora.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cin.locadora.model.Genero;
import br.com.cin.locadora.model.repository.GeneroRepository;

@Service
public class GeneroService {
		
	@Autowired
	private GeneroRepository generoRepository;
	
	
	/****
	 * 
	 * @return
	 */
	public Iterable<Genero> listarTodos(){
		return this.generoRepository.findAll();
	}
}
