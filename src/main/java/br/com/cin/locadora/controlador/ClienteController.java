package br.com.cin.locadora.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceRef;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import br.com.cin.locadora.controlador.util.Navegacao;
import br.com.cin.locadora.excption.QuantidadeDependenteException;
import br.com.cin.locadora.model.Cliente;
import br.com.cin.locadora.model.Dependente;
import br.com.cin.locadora.model.repository.ClienteRepository;
import br.com.cin.locadora.model.repository.DependenteRepository;
import br.com.cin.locadora.servico.ClienteService;



@Controller
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	ClienteRepository repository;
	@Autowired
	DependenteRepository dependenteRepository;

	@Autowired
	ClienteService clienteService;

	List<Dependente> listaDependentes;
	List<Cliente> clientesAtivos;

	List<String> messagensErro = new ArrayList<String>();

	public ClienteController() {
		this.listaDependentes = new ArrayList<Dependente>();
		this.clientesAtivos = new ArrayList<Cliente>();
	}
	
	@RequestMapping( "**/home")
	public void home(HttpServletResponse response, HttpServletRequest request) throws IOException {
		String context = request.getContextPath();
		response.sendRedirect(context+"/home");
		
	}

	

	@RequestMapping("**/cadastrocliente")
	public ModelAndView form() {
		ModelAndView andView = new ModelAndView("cliente/cadastrocliente");
		Iterable<Cliente> clientes = this.repository.findAll();
		andView.addObject("clientes",clientes);
		andView.addObject("cliente", new Cliente());
		return andView;
	}

	/****
	 * 
	 * @param nome
	 * @param email
	 * @param celular
	 * @param foneComercial
	 * @param foneResidencial
	 * @param sexo
	 * @param nascimento
	 * @param cpf
	 */
	@RequestMapping(value = "**/salvarcliente", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView salvar(@RequestParam("nome") String nome, @RequestParam("email") String email,
			@RequestParam("celular") String celular, @RequestParam("tel_comercial") String foneComercial,
			@RequestParam("tel_residencial") String foneResidencial, @RequestParam("sexo") String sexo,
			@RequestParam("cpf") String cpf, @RequestParam("endereco") String endereco,
			@RequestParam("local_trabaho") String localTrabalho, HttpSession session) {

		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		cliente.setEmail(email);
		cliente.setCpf(cpf);
		cliente.setFoneCelular(celular);
		cliente.setFoneComercial(foneComercial);
		cliente.setFoneResidencial(foneResidencial);
		cliente.setSexo(sexo);
		cliente.setCpf(cpf);
		cliente.setEndereco(endereco);
		cliente.setLocalTrabalho(localTrabalho);
		
		
		this.clienteService.salvarCliente(cliente);

		return this.form();

	}
	
	@PostMapping("**/pesquisarcliente")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {
		ModelAndView modelAndView = new ModelAndView("cliente/cadastrocliente");		
		Iterable<Cliente> clientes = this.repository.findPessoaByName(nomepesquisa);
		modelAndView.addObject("clientes",clientes );
		modelAndView.addObject("cliente", new Cliente());
		return modelAndView;
	}
	
	
	@GetMapping("/removercliente/{idcliente}")
	public ModelAndView excluir(@PathVariable("idcliente") Integer idcliente) {	
		this.repository.deleteById(idcliente);
		ModelAndView view = new ModelAndView(Navegacao.CADASTRAR_CLIENTE);
		view.addObject("clientes", this.repository.findAll());
		view.addObject("cliente", new Cliente());
		return this.form();
	}
	
	@RequestMapping(value="removerDependente", method = {RequestMethod.GET, RequestMethod.POST})
	public String removerDependente(@RequestParam("idDependente") String id, Model model,HttpSession session) {
		Cliente cliente = (Cliente) session.getAttribute("cliente");
		this.removerDependente(cliente, id);
		this.clienteService.atualizar(cliente);
		return "redirect:/cliente/form_dependente?id="+cliente.getId().toString();
		
	}

	private void removerDependente(Cliente cliente, String id) {
		int idDependente =Integer.valueOf(id);
		for (Dependente dependente : cliente.getDependentes()) {
			if(dependente.getIdDependente()==idDependente) {
				cliente.getDependentes().remove(dependente);
			}
		}
	}

	@GetMapping("/dependentes/{idcliente}")
	public ModelAndView cadastroDependente(@PathVariable("idcliente") Integer idcliente) {
		Cliente cliente = this.clienteService.buscarPorId(Integer.valueOf(idcliente.toString()));
		
		ModelAndView modelAndView = new ModelAndView(Navegacao.CADASTRO_DEPENDENTE);
		modelAndView.addObject("cliente", cliente);
		modelAndView.addObject("dependentes", this.dependenteRepository.getDependentes(idcliente));
		return modelAndView;
	}
	
	@PostMapping("**/addDependeteCliente/{idcliente}")
	public ModelAndView addDependente(Dependente dependente, 
									 @PathVariable("idcliente") Integer idCliente) {
		
		Cliente cliente = this.repository.findById(idCliente).get();
       
		ModelAndView modelAndView = new ModelAndView(Navegacao.CADASTRO_DEPENDENTE);
		dependente.setIdCliente(cliente);		
		this.dependenteRepository.save(dependente);		
		modelAndView.addObject("cliente", cliente);
		modelAndView.addObject("dependentes", this.dependenteRepository.getDependentes(idCliente));
		
		return modelAndView;
	}
	
	@GetMapping("**/removerdependenteCliente/{iddependente}")
	public ModelAndView removerDependente( 
									 @PathVariable("iddependente") Integer iddependente) {
		
		Cliente cliente = this.dependenteRepository.findById(iddependente).get().getIdCliente();
		ModelAndView modelAndView = new ModelAndView(Navegacao.CADASTRO_DEPENDENTE);
		this.dependenteRepository.deleteById(iddependente);			
		modelAndView.addObject("cliente", cliente);
		modelAndView.addObject("dependentes", this.dependenteRepository.getDependentes(cliente.getId()));	
		return modelAndView;
	}
	

	/****
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/listar")
	public String listarTodos(Model model) {
		Iterable<Cliente> clientes = this.clienteService.listarTodos();
		model.addAttribute("clientes", clientes);
		return Navegacao.LISTAGEM_CLIENTES;
	}

	@RequestMapping("/visualizar")
	public String visualizarCliente(@RequestParam("id") String id, Model model) {
		Integer codigo = Integer.valueOf(id);
		model.addAttribute("cliente", this.clienteService.buscarPorId(codigo));

		return Navegacao.VISUALIZAR_CLIENTE;
	}

	@RequestMapping("/teste")
	public String teste(HttpServletRequest httpServletRequest) {
		return "/cliente/teste";
	}

	public void setMessagensErro(List<String> messagensErro) {
		this.messagensErro = messagensErro;
	}

	public List<String> getMessagensErro() {
		return messagensErro;
	}

}
