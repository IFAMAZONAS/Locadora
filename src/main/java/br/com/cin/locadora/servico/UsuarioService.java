package br.com.cin.locadora.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cin.locadora.model.Usuario;
import br.com.cin.locadora.model.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public void salvar(Usuario usuario) {
		this.usuarioRepository.save(usuario);
	}
	
	
}
