package br.com.cin.bdd.usuario;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.cin.locadora.model.Usuario;
import br.com.cin.locadora.servico.UsuarioService;
import cucumber.api.PendingException;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;

public class UsuarioBDDSteps {
	@Autowired
	UsuarioService usu = new UsuarioService();
	
	Usuario usuario = new Usuario();
	boolean esperado;
	/***
	 * Caso de Teste -BDD -#46 - Acesso as Funcionalidades
	 * 
	 * 
	 * 
	 * */
	@Dado("^que usuário possa acessar a tela de login do sistema$")
	public void que_usuário_possa_acessar_a_tela_de_login_do_sistema() throws Throwable {
	   System.out.println();
	}

	@Quando("^informar um \"([^\"]*)\" válido$")
	public void informar_um_válido(String login) throws Throwable {
	    usuario.setLogin(login);
		System.out.println("Usuario :"+login);	    
	}

	@Quando("^inserir uma \"([^\"]*)\" válida$")
	public void inserir_uma_válida(String senha) throws Throwable {
		usuario.setSenha(senha);
		 System.out.println("Senha :"+senha);	    
	}

	@Então("^o sistema deve permitir a autenticação do usuário$")
	public void o_sistema_deve_permitir_a_autenticação_do_usuário() throws Throwable {
		assertEquals(esperado = usu.validaUsuario(usuario),Boolean.TRUE);
		if(esperado)
			System.out.println("Validou o usuário");
		else 
			System.out.println("Invalido o usuário");
	}

	@Então("^o tipo de \"([^\"]*)\" do usuário deve ser exibido$")
	public void o_tipo_de_do_usuário_deve_ser_exibido(String mensagem) throws Throwable {
		if(esperado) 
			System.out.println("Mensagem : "+mensagem);	
		else
			System.out.println("Erro");
	}
	
}
