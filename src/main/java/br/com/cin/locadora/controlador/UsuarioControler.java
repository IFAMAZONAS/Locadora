package br.com.cin.locadora.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.cin.locadora.model.Usuario;
import br.com.cin.locadora.servico.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioControler  {
	
		private Usuario usuario;
		@Autowired
		private UsuarioService usuarioService;

		public UsuarioControler() {
			this.usuario = new Usuario();
		}
		
		public String login(@RequestParam("")String usuario, @RequestParam("senha") String senha, Model model) {
			return "";
		}
		
		public String buscarUsuario() {
			return "";
		}
		
		@RequestMapping("form")
		public String form() {
			return "/usuario/form";
		}
		
		@RequestMapping(value="salvar",method = { RequestMethod.GET, RequestMethod.POST })
		public String salvar(@RequestParam("nome")String nome, @RequestParam("login") String login, @RequestParam("senha") String senha, Model model) {
			
			this.usuario = new Usuario();
			this.usuario.setNome(nome);
			this.usuario.setLogin(login);
			this.usuario.setSenha(senha);
			this.usuarioService.salvar(usuario);
			return"/usuario/form";
		}
}
