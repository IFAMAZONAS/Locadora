package br.com.cin.locadora.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cin.locadora.controlador.util.Navegacao;
import br.com.cin.locadora.model.repository.ClienteRepository;

@Controller
@RequestMapping(value="/locacao")
public class LocacaoController {
	
	
	@Autowired
	private ClienteRepository clienterepository;
	
	
	@RequestMapping(method = RequestMethod.GET, value = "**/cadastrolocacao")
	@ResponseBody
	public ModelAndView forme() {
		ModelAndView andView = new ModelAndView(Navegacao.INICIAR_LOCACAO);	
		return andView;
	}
	
	

	@GetMapping("**/entrarpesquisa")
	public ModelAndView entrarBusca() {
		ModelAndView andView = new ModelAndView(Navegacao.BUSCAR_CLIENTE_LOCACAO);		
		return andView;
	}
	
	@GetMapping("**/listarclientes")
	public ModelAndView entrarClientes() {
		ModelAndView andView = new ModelAndView(Navegacao.LISTAR_CLIENTE_LOCACAO);		
		return andView;
	}

	
	
	@PostMapping("**/pesquisarcliente")
	public ModelAndView buscarPorNome(@RequestParam("nomepesquisa") String nomepesquisa) {
		ModelAndView andView = new ModelAndView(Navegacao.BUSCAR_CLIENTE_LOCACAO);		
		return andView;
	}

}
