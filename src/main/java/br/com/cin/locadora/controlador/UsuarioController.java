package br.com.cin.locadora.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.jboss.jandex.TypeTarget.Usage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cin.locadora.controlador.util.Navegacao;
import br.com.cin.locadora.model.Cliente;
import br.com.cin.locadora.model.Dependente;
import br.com.cin.locadora.model.Funcao;
import br.com.cin.locadora.model.Role;
import br.com.cin.locadora.model.Usuario;
import br.com.cin.locadora.model.repository.ClienteRepository;
import br.com.cin.locadora.model.repository.FuncaoRepository;
import br.com.cin.locadora.model.repository.RoleRepository;
import br.com.cin.locadora.model.repository.UsuarioRepository;
import br.com.cin.locadora.servico.ClienteService;

@Controller
@RequestMapping(value="usuario")
public class UsuarioController {
	
		@Autowired
		private ClienteService clienteService;
		@Autowired
		private UsuarioRepository  usuarioRepository;
		@Autowired
		private RoleRepository roleRepository;
		@Autowired
		private FuncaoRepository funcaoRepository;
		List<String> msg;
		private List<String> msgErros;
		
		Iterable<Role> rolesUsuario;
		Iterable<Role> allRoles;
		Iterable<Funcao> funcoes;
		
		
		
		
		public UsuarioController() {
			this.msg = new ArrayList<String>();
			this.msgErros = new ArrayList<String>();
			
		}
	    
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
			List<Usuario> usuarios = new ArrayList<Usuario>();
			this.funcoes = this.listarFuncoes();
			andView.addObject("roles", rolesUsuario);
			andView.addObject("funcoes", funcoes);
			return andView;
		}
		
		@PostMapping("**/pesquisarusuario")
		public ModelAndView pesquisarUsuario(@RequestParam("usuariopesquisa") String login) {
			ModelAndView andView = new ModelAndView(Navegacao.CADASTRO_USUARIO);
			Iterable<Usuario> usuarios = this.usuarioRepository.ListarUsuariosPorLogin(login);
			this.funcoes = this.listarFuncoes();
			andView.addObject("usuarios", usuarios);
			andView.addObject("funcoes", funcoes);
	
			return andView;
		}
		
		
		/**
		 * 
		 * 
		 */
		@RequestMapping(value="**/salvarusuario")
		public ModelAndView cadastrarUsuario(@RequestParam("nome") String nome, @RequestParam("login")String login, @RequestParam("senha")String senha, @RequestParam("funcao") String funcao) {
			ModelAndView andView = new ModelAndView(Navegacao.CADASTRO_USUARIO);
			if(this.validateUsuario(login,nome, senha)) {				
				Usuario novoUsuario = new Usuario();	
				Funcao funcaoUsuario = this.funcaoRepository.findById(Integer.valueOf(funcao)).get();		
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				String password = encoder.encode(senha);			
				novoUsuario.setLogin(login);
				novoUsuario.setSenha(password);	
				novoUsuario.setNome(nome);
				novoUsuario.setIdFuncao(funcaoUsuario);	
				this.usuarioRepository.save(novoUsuario);
				List<Usuario> usuarios = new ArrayList<Usuario>();
				andView.addObject("usuarios", usuarios);
				msg.add("Operação realizada com sucesso!");
				andView.addObject("msg",msg);	
				this.msg = new ArrayList<String>();
				this.funcoes = this.listarFuncoes();
				andView.addObject("roles", rolesUsuario);
				andView.addObject("funcoes", funcoes);
				
				return andView;
			}else {
				List<Usuario> usuarios = new ArrayList<Usuario>();
				andView.addObject("usuarios", usuarios);
				andView.addObject("messagensErro",this.msgErros);
				this.funcoes = this.listarFuncoes();
				andView.addObject("funcoes", funcoes);
				this.msg = new ArrayList<String>();
				return andView;
			}
			
		
			
		}
		/****
		 * 
		 * @param idusuario
		 * @param httpSession
		 * @return
		 */
		@GetMapping("/cadastropermissoes/{idusuario}")
		public ModelAndView cadastroPermissao(@PathVariable("idusuario") Integer idusuario) {
			ModelAndView andView = new ModelAndView(Navegacao.CADASTRO_PERMISSOES);
			Usuario usuario = this.usuarioRepository.findById(Long.valueOf(idusuario)).get();
			this.rolesUsuario = (List<Role>) usuario.getAuthorities();
			this.allRoles = this.roleRepository.findAll();
			andView.addObject("rolesUsuario", rolesUsuario);
			andView.addObject("allRoles", allRoles);
			andView.addObject("usuario",usuario);	
			
			return andView;
		}
		
		@RequestMapping(value="**/adicionarpermissao")
		public ModelAndView adicionarPermissao(@RequestParam("idpermissao") String idPermissao, @RequestParam("idusuario") String idUsuario ) {
			ModelAndView andView = new ModelAndView(Navegacao.CADASTRO_PERMISSOES);	
			this.msg = new ArrayList<String>();
			if(this.validatePermissao(idPermissao, idUsuario)) {
				Usuario usuario = this.usuarioRepository.findById(Long.valueOf(idUsuario)).get();
				Role role = this.roleRepository.findById(Long.valueOf(idPermissao)).get();
				usuario.getRoles().add(role);
				this.rolesUsuario = (List<Role>) usuario.getAuthorities();
				this.allRoles = this.roleRepository.findAll();		
				this.usuarioRepository.save(usuario);
				this.msg.add("Operação realizada com sucesso!");
				andView.addObject("rolesUsuario", rolesUsuario);
				andView.addObject("allRoles", allRoles);
				andView.addObject("usuario",usuario);
				andView.addObject("msg", this.msg);
				return andView;
			}else {
				Usuario usuario = this.usuarioRepository.findById(Long.valueOf(idUsuario)).get();
				Role role = this.roleRepository.findById(Long.valueOf(idPermissao)).get();
				this.rolesUsuario = (List<Role>) usuario.getAuthorities();
				this.allRoles = this.roleRepository.findAll();
				andView.addObject("rolesUsuario", rolesUsuario);
				andView.addObject("allRoles", allRoles);
				andView.addObject("usuario",usuario);
				andView.addObject("messagensErro", this.msgErros);
				return andView;
				
			}
			
			
		}
		
		@RequestMapping(value="**/removerpermissao")
		public ModelAndView removerPermissao(@RequestParam("idpermissao") String idPermissao, @RequestParam("idusuario") String idUsuario) {
			this.msg = new ArrayList<String>();
			ModelAndView andView = new ModelAndView(Navegacao.CADASTRO_PERMISSOES);
			Usuario usuario = this.usuarioRepository.findById(Long.valueOf(idUsuario)).get();
			Role role = this.roleRepository.findById(Long.valueOf(idPermissao)).get();
			usuario.getRoles().remove(role);
			this.usuarioRepository.save(usuario);
			this.msg.add("A exlusão foi realizada realizada com sucesso!");
			this.rolesUsuario = (List<Role>) usuario.getAuthorities();
			this.allRoles = this.roleRepository.findAll();
			andView.addObject("rolesUsuario", rolesUsuario);
			andView.addObject("allRoles", allRoles);
			andView.addObject("usuario",usuario);
			andView.addObject("msg", this.msg);
			andView.addObject("messagensErro",this.msgErros);
			
			return andView;
		}
		
		
		/***
		 * 
		 * @param idPermissao
		 * @param idUsuario
		 * @return
		 */
		private boolean validatePermissao(String idPermissao, String idUsuario) {
			boolean retorno = true;
			Usuario usuario = this.usuarioRepository.findById(Long.valueOf(idUsuario)).get();
			Role role = this.roleRepository.findById(Long.valueOf(idPermissao)).get();
			this.msgErros = new ArrayList<String>();
			
			if(usuario.getRoles().contains(role)) {
				retorno = false;
				this.msgErros.add("Não foi possivel realizar a operação: Usuãrio já tem o papel:"+role.getNomeRole());
			}
			return retorno;
		}

		/***
		 * 
		 * @param login
		 * @param nome
		 * @param senha
		 * @return
		 */
		private boolean validateUsuario(String login, String nome, String senha) {
			boolean retorno = true;
			this.msgErros = new ArrayList<String>();
			
			if(login.isEmpty()) {
				this.msg.add("Campo login deve ser informado!");
				retorno = false;
			}
			
			if(senha.isEmpty()) {
				this.msgErros.add("Campo Senha Senha deve ser informado!");
				retorno = false;
			}
			
			if(nome.isEmpty()) {
				this.msgErros.add("Campo nome deve ser informado!");
				retorno = false;
			}
			
			
			if((!(this.usuarioRepository.findUserByLogin(login)==null)) && this.msgErros.isEmpty()) {
				this.msgErros.add("Já existe um usuário com o login informado!");
				retorno =  false;
			}
			return retorno;
			
		}
		/****
		 * 
		 * @return
		 */
		public Iterable<Funcao> listarFuncoes(){
			return this.funcaoRepository.findAll();
		}
}
