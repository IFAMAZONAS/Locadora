package br.com.cin.locadora.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.cin.locadora.model.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Integer> {
	
	
	
}
