package br.com.cin.locadora;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cin.locadora.controlador.util.Navegacao;
import br.com.cin.locadora.model.Cliente;
import br.com.cin.locadora.model.Usuario;
import br.com.cin.locadora.model.repository.ClienteRepository;
import br.com.cin.locadora.model.repository.UsuarioRepository;
import br.com.cin.locadora.servico.ClienteService;

@Controller
@RequestMapping(value="usuario")
public class UsuarioController {
	
		@Autowired
		private ClienteService clienteService;
		@Autowired
		private UsuarioRepository  usuarioRepository;
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
			Cliente cliente = new Cliente();
			Iterable<Usuario> usuarios = this.usuarioRepository.findAll();
			andView.addObject("usuarios", usuarios);
			andView.addObject("pessoas", clientes);		
			return andView;
		}
		
		@RequestMapping(value="**/salvarusuario")
		public ModelAndView cadastrarUsuario(@RequestParam("nome") String nome, @RequestParam("login")String login, @RequestParam("senha")String senha,@RequestParam("teste")String teste) {
			ModelAndView andView = new ModelAndView(Navegacao.CADASTRO_USUARIO);
			List<Cliente> clientes =  (List<Cliente>) this.clienteService.listarTodos();
			Cliente cliente = new Cliente();
			Iterable<Usuario> usuarios = this.usuarioRepository.findAll();
			andView.addObject("usuarios", usuarios);
			andView.addObject("pessoas", clientes);	
			return andView;
		}
}
