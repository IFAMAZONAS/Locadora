package br.com.cin.locadora.servico;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.crypto.CipherOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cin.locadora.model.Cliente;
import br.com.cin.locadora.model.repository.ClienteRepository;

@Service
public class ClienteService {
		
		@Autowired
		ClienteRepository clienteRepository;
		
	
		/***
		 * 
		 * @param cliente
		 */
		public void salvarCliente(Cliente cliente) {
			clienteRepository.save(cliente);
			
			
		}
		
		/***
		 * 
		 * @param cliente
		 */
		public void removerCliente(Cliente cliente) {
			
		}
		/***
		 * 
		 * @return
		 */
		public Iterable<Cliente> listarTodos() {
			return this.clienteRepository.findAll();
		}
		/***
		 * 
		 * @return
		 */
		public Cliente buscarPorId(Integer id) {
			return this.clienteRepository.findById(id).get();
		}
		
		public void atualizar(Cliente cliente) {
			this.clienteRepository.save(cliente);
		}
}
