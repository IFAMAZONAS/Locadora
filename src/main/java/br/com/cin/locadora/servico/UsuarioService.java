package br.com.cin.locadora.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cin.locadora.model.Usuario;
import br.com.cin.locadora.model.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public boolean salvar(Usuario usuario) {
		if(!usuario.getLogin().isEmpty() ||usuario.getPassword().isEmpty()){
			this.usuarioRepository.save(usuario);
			return true;
		}else {
			return false;
		}
		
	}
	/*****
	 * 
	 * @param usuario
	 * @return
	 */
	public boolean excluirUsuario(Usuario usuario) {
		if(usuario!=null) {
			this.usuarioRepository.delete(usuario);
			return true;
		}else {
			return false;
		}
	}
	
	
}
