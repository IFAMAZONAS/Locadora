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
		public boolean salvarCliente(Cliente cliente) {
			if(cliente.getNome().equals("")) {
				return false;
			}else {
				clienteRepository.save(cliente);
				return true;
			}
			
			
			
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
		/***
		 * 
		 * @return
		 */
		public Iterable<Cliente> listarUsuarioAtivos(){
			Iterable<Cliente> listaTodos = this.clienteRepository.findAll();
			Iterable<Cliente> listaClientesAtivos = this.buscarAtivos(listaTodos);
			
			return listaClientesAtivos;
		}
        /***
         * 
         * @param listaTodos
         * @return
         */
		private Iterable<Cliente> buscarAtivos(Iterable<Cliente> listaTodos) {
			 List<Cliente> ativos = new ArrayList<>();
			 for(Cliente cliente : listaTodos) {
				  if(cliente.getStatus().getIdStatus()==1) {
					  ativos.add(cliente);
				  }
			 }
			return ativos;
		}
		
		public List<Cliente> findPessoaByName(String nome){
			
			if(nome.isEmpty()) {
				return new ArrayList<>();
			}
			List<Cliente> clintesAtivos = new ArrayList<Cliente>();
			
			for(Cliente cliente : this.clienteRepository.findPessoaByNameALL(nome)) {
				if(cliente.getStatus().getIdStatus()==1) {
					clintesAtivos.add(cliente);
				}
			}
			return clintesAtivos;
		}
}
