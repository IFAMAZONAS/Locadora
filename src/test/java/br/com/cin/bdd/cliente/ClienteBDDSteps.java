package br.com.cin.bdd.cliente;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.cin.locadora.model.Cliente;
import br.com.cin.locadora.servico.ClienteService;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Então;


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
	@Então("^eu devo enviar uma menssagem de Campo em Branco$")
	public void eu_devo_enviar_uma_menssagem_de_Campo_em_Branco() throws Throwable{
	    // Write code here that turns the phrase above into concrete actions
		Cliente novocCliente = new Cliente();
		novocCliente.setNome("fff");
		boolean esperado = false;		
		Assert.assertEquals("Campo em branco",clienteService.salvarCliente2(novocCliente),esperado);
	    
	}
}
