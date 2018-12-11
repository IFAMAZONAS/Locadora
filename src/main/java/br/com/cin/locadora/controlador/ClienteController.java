package br.com.cin.locadora.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cin.locadora.controlador.util.Navegacao;

@Controller()
@RequestMapping("/cliente")
public class ClienteController {
		
	@RequestMapping("/cadastro")
	public String form() {
		return Navegacao.CADASTRAR_CLIENTE;
	}
}
