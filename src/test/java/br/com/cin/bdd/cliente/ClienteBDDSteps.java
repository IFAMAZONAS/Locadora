package br.com.cin.bdd.cliente;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.cin.locadora.model.Cliente;
import br.com.cin.locadora.servico.ClienteService;
import cucumber.api.java.pt.Dado;


public class ClienteBDDSteps {
	@Autowired
	ClienteService clienteService = new ClienteService();
	/***
	 * Caso de Teste -TC -01 - Nome em branco do Cliente
	 * 
	 * Caso o nome do cliente esteja em branco, o metodo retornara False, tendo com resultado com esperado tambem False
	 * 
	 * */
	@Dado("^o nome do cliente em branco$")
	public void cadastroCliente() {
		Cliente novocCliente = new Cliente();
		novocCliente.setNome("");
		boolean esperado = false;		
		Assert.assertEquals(clienteService.salvarCliente(novocCliente),esperado);		
	}

}
