package br.com.cin.locadora.servico;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.crypto.CipherOutputStream;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cin.locadora.model.Cliente;
import br.com.cin.locadora.model.Dependente;
import br.com.cin.locadora.model.StatusCliente;
import br.com.cin.locadora.model.repository.ClienteRepository;
import br.com.cin.locadora.model.repository.DependenteRepository;
import br.com.cin.locadora.model.repository.StatusClienteRepository;

@Service
public class ClienteService {
		
		@Autowired
		ClienteRepository clienteRepository;
		@Autowired
		StatusClienteRepository statusClienteRepository;
		@Autowired
		DependenteRepository dependenteRepository;
				
	    static Integer ATIVO =1;
	    static Integer INATIVO =2;
		
	
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
		public boolean salvarCliente2(Cliente cliente) {
			if(cliente.getNome().equals("")) {
				return true;
			}else {
				//clienteRepository.save(cliente);
				return false;
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
		
		/****
		 * Recece um cliente o desativa e desativa todos os seus dependetes se houver
		 * @param cliente
		 * @return
		 */
		public boolean desativarCliente(Cliente cliente) {			
			boolean retorno = false;
			
			if(!(cliente==null)) {
				List<Dependente> depedeList = cliente.getDependentes();
				
				if(!depedeList.isEmpty()) {
					for (Dependente dependente: depedeList) {
						dependente.setAtivo(Boolean.FALSE);
						retorno = Boolean.TRUE;
					}
				}				
				//cliente.setStatus(this.statusClienteRepository.findById(INATIVO).get());
				StatusCliente statuscli = new StatusCliente();
				statuscli.setIdStatus(INATIVO);
				statuscli.setDescricao("INATIVO");
				cliente.setStatus(statuscli);
				
				retorno = Boolean.TRUE;			
			}			
			return retorno;			
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
				if(cliente.getStatus().getIdStatus()==ATIVO) {
					clintesAtivos.add(cliente);
				}
			}
			return clintesAtivos;
		}
		/****
		 * 
		 * @param dependentes
		 */
		public void destativarDepedentes(List<Dependente> dependentes) {
			if(!dependentes.isEmpty()) {
				for (Dependente dependente : dependentes) {
					dependente.setAtivo(false);
					this.dependenteRepository.save(dependente);
				}
			}
		}
		/****
		 * 
		 * @param dependentes
		 */
		public void reativarDepedentes(List<Dependente> dependentes) {
			if(!dependentes.isEmpty()) {
				for (Dependente dependente : dependentes) {
					dependente.setAtivo(true);
					this.dependenteRepository.save(dependente);
				}
			}
		}

		public List<Cliente> listarClientesInativos() {
			 List<Cliente> listaTodos = (List<Cliente>) this.clienteRepository.findAll();
			 List<Cliente> ativos = new ArrayList<>();
			 for(Cliente cliente : listaTodos) {
				  if(cliente.getStatus().getIdStatus()==INATIVO) {
					  ativos.add(cliente);
				  }
			 }
			return ativos;
		}
}
