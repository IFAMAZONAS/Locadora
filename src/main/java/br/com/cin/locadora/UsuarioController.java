package br.com.cin.locadora;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cin.locadora.controlador.util.Navegacao;
import br.com.cin.locadora.model.Cliente;
import br.com.cin.locadora.model.repository.ClienteRepository;
import br.com.cin.locadora.servico.ClienteService;

@Controller
@RequestMapping(value="usuario")
public class UsuarioController {
	
		@Autowired
		private ClienteService clienteService;
		/***
		 * 
		 * @return
		 */
		public String index() {
			return"";
		}
		
		@RequestMapping(method=RequestMethod.GET, value="**/cadastrousuario")
		@ResponseBody
		public ModelAndView forme() {
			ModelAndView andView = new ModelAndView(Navegacao.CADASTRO_USUARIO);
			List<Cliente> clientes =  (List<Cliente>) this.clienteService.listarTodos();
			andView.addObject("pessoas", clientes);		
			return andView;
		}
}
