package br.com.cin.locadora.dao;

import org.springframework.data.repository.CrudRepository;

import br.com.cin.locadora.dominio.Cliente;

public interface ClienteDAO extends CrudRepository<Cliente, Long> {

}
