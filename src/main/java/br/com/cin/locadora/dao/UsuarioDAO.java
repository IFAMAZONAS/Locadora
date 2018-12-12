package br.com.cin.locadora.dao;


import org.springframework.data.repository.CrudRepository;

import br.com.cin.locadora.dominio.Usuario;


public interface UsuarioDAO extends CrudRepository<Usuario, Long> {

}
