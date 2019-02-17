package br.com.cin.tdd.cliente;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cin.locadora.model.Cliente;
import br.com.cin.locadora.model.Dependente;
import br.com.cin.locadora.model.Usuario;
import br.com.cin.locadora.servico.ClienteService;
import br.com.cin.locadora.servico.DependenteService;
import br.com.cin.locadora.servico.UsuarioService;



@RunWith(SpringRunner.class)
public class ClienteTDD {

		ClienteService clienteService = new ClienteService();
		
		DependenteService dependenteService = new DependenteService();
		/*****
		 * Teste para validar ao Adicionar mais 3 Dependentes
		 */
		/*@Test
		public void cadastroCliente() {
				Cliente novocCliente = new Cliente();
				novocCliente.setNome("Cliente_1");
				boolean esperado = false;
				
				Assert.assertEquals(clienteService.salvarCliente(novocCliente),esperado);
		}*/
		/****
		 * 
		 */
		@Test
		public void addTresMaisTresDependentes() {
			 
			 Cliente cliente = new Cliente();
			 cliente.setDependentes(new ArrayList<Dependente>());
			 
			 Dependente d1 = new Dependente();
			 Dependente d2 = new Dependente();
			 Dependente d3 = new Dependente();
			 Dependente d4 = new Dependente();
			 d1.setIdCliente(cliente);
			 d2.setIdCliente(cliente);
			 d3.setIdCliente(cliente);
			 d4.setIdCliente(cliente);
			 
			 cliente.getDependentes().add(d1);
			 cliente.getDependentes().add(d2);
			 cliente.getDependentes().add(d3);
			 //cliente.getDependentes().add(d4);
			 
			 d1.setNome("Dependente1");
			 d2.setNome("Dependente2");
			 d3.setNome("Dependente3");
			 d4.setNome("Dependente4");
			 
			 boolean esperado = false;
			 
			 Assert.assertEquals(this.dependenteService.cadastro(d4), esperado);
			
		}
		
		@Test
		public void desativaClienteDependentes() {
			//Desativou retorna =True, senao False
			boolean esperado = true;
			 
			Cliente clienteDesativa = new Cliente();
			
		    clienteDesativa.setId(1);
			
			clienteDesativa.setDependentes(new ArrayList<Dependente>());
			 
			 Dependente d1 = new Dependente();
			 Dependente d2 = new Dependente();
			 Dependente d3 = new Dependente();
			 
			 d1.setIdCliente(clienteDesativa);
			 d2.setIdCliente(clienteDesativa);
			 d3.setIdCliente(clienteDesativa);
			 
			 clienteDesativa.getDependentes().add(d1);
			 clienteDesativa.getDependentes().add(d2);
			 clienteDesativa.getDependentes().add(d3);
			 
			 
			 assertEquals(this.clienteService.desativarCliente(clienteDesativa), esperado);
			
		}
	  
}
