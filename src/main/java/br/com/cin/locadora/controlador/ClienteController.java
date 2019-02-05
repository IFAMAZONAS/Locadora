package br.com.cin.locadora.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceRef;

import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import br.com.cin.locadora.controlador.util.Navegacao;

import br.com.cin.locadora.model.Cliente;
import br.com.cin.locadora.model.Dependente;
import br.com.cin.locadora.model.Usuario;
import br.com.cin.locadora.model.repository.ClienteRepository;
import br.com.cin.locadora.model.repository.DependenteRepository;
import br.com.cin.locadora.model.repository.StatusClienteRepository;
import br.com.cin.locadora.servico.ClienteService;

@Controller
@RequestMapping(value = "cliente")
public class ClienteController {

	@Autowired
	ClienteRepository repository;
	@Autowired
	DependenteRepository dependenteRepository;
	
	@Autowired
	StatusClienteRepository statusClienteRepository;

	@Autowired
	ClienteService clienteService;

	List<Dependente> listaDependentes;
	List<Cliente> clientesAtivos;
	List<String> msg = new ArrayList<>();
	//List<String> msgErros = new ArrayList<>();

	List<String> messagensErro = new ArrayList<String>();

	public ClienteController() {
		this.listaDependentes = new ArrayList<Dependente>();
		this.clientesAtivos = new ArrayList<Cliente>();
	}

	@RequestMapping(method = RequestMethod.GET, value = "home")
	@ResponseBody
	public ModelAndView home(HttpServletResponse response, HttpServletRequest request) throws IOException {
		ModelAndView andView = new ModelAndView("home");
		return andView;

	}

	// @RequestMapping("**/cadastrocliente")

	@RequestMapping(method = RequestMethod.GET, value = "**/cadastrocliente")
	@ResponseBody
	public ModelAndView form() {
		
		ModelAndView andView = new ModelAndView(Navegacao.CADASTRAR_CLIENTE);
		Iterable<Cliente> clientes = this.repository.findAll();
		andView.addObject("clientes", new ArrayList<>());
		andView.addObject("cliente", new Cliente());
		andView.addObject("msg", this.msg);
		andView.addObject("messagensErro", this.messagensErro);
		this.messagensErro = new ArrayList<>();
		this.msg = new ArrayList<>();
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
			@RequestParam("local_trabaho") String localTrabalho) {

		if (this.validateCliente(nome, email, celular, foneComercial, foneResidencial, cpf, endereco, localTrabalho)) {
			this.msg = new ArrayList<String>();
			Cliente cliente = new Cliente();
			cliente.setNome(nome.toUpperCase());
			cliente.setEmail(email);
			cliente.setCpf(cpf);
			cliente.setStatus(this.statusClienteRepository.findById(1).get());
			cliente.setFoneCelular(celular);
			cliente.setFoneComercial(foneComercial);
			cliente.setFoneResidencial(foneResidencial);
			cliente.setSexo(sexo);
			cliente.setCpf(cpf);
			cliente.setEndereco(endereco);
			cliente.setLocalTrabalho(localTrabalho);
			this.msg.add("Operação realizada com sucesso!");
			this.clienteService.salvarCliente(cliente);
			return this.form();
		} else {
			return this.form();
		}

	}
	
	
	@PostMapping("**/pesquisarcliente")
	public ModelAndView ListarPorNome(@RequestParam("nomepesquisa") String nomepesquisa) {
		ModelAndView modelAndView = new ModelAndView(Navegacao.LISTAGEM_CLIENTES);
		Iterable<Cliente> clientes = this.repository.findPessoaByName(nomepesquisa);
		List<Cliente> lista = (List<Cliente>) clientes;
		if(!ValidadorCliente.getInstance().validatePesquisa(lista)) {
			modelAndView.addObject("clientes", clientes);
			modelAndView.addObject("cliente", new Cliente());
			ValidadorCliente.getInstance().getMsgErros().add("Não foi encontrado resulatdo para os dados informados!");
			modelAndView.addObject("msg", ValidadorCliente.getInstance().getMsg());
			modelAndView.addObject("messagensErro", ValidadorCliente.getInstance().getMsgErros());
			return modelAndView;
		}else {
			modelAndView.addObject("clientes", clientes);
			modelAndView.addObject("cliente", new Cliente());
			modelAndView.addObject("msg", ValidadorCliente.getInstance().getMsg());
			modelAndView.addObject("messagensErro", ValidadorCliente.getInstance().getMsgErros());
			return modelAndView;
		}
		
	}

	@GetMapping("/removercliente/{idcliente}")
	public ModelAndView excluir(@PathVariable("idcliente") Integer idcliente) {
		this.repository.deleteById(idcliente);
		ModelAndView view = new ModelAndView(Navegacao.CADASTRAR_CLIENTE);
		view.addObject("clientes", this.repository.findAll());
		view.addObject("cliente", new Cliente());
		return this.form();
	}

	@RequestMapping(value = "removerDependente", method = { RequestMethod.GET, RequestMethod.POST })
	public String removerDependente(@RequestParam("idDependente") String id, Model model, HttpSession session) {
		Cliente cliente = (Cliente) session.getAttribute("cliente");
		this.removerDependente(cliente, id);
		this.clienteService.atualizar(cliente);
		return "redirect:/cliente/form_dependente?id=" + cliente.getId().toString();

	}

	private void removerDependente(Cliente cliente, String id) {
		int idDependente = Integer.valueOf(id);
		for (Dependente dependente : cliente.getDependentes()) {
			if (dependente.getIdDependente() == idDependente) {
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
	public ModelAndView addDependente(Dependente dependente, @PathVariable("idcliente") Integer idCliente) {

		Cliente cliente = this.repository.findById(idCliente).get();

		ModelAndView modelAndView = new ModelAndView(Navegacao.CADASTRO_DEPENDENTE);

		if (validadeQuantidadeDependente(cliente)) {
			if(validadeDependente(dependente)) {
				this.msg = new ArrayList<>();
				this.msg.add("Operação realizada com sucesso");
				dependente.setIdCliente(cliente);
				this.dependenteRepository.save(dependente);
				modelAndView.addObject("cliente", cliente);
				modelAndView.addObject("msg", this.msg);
				modelAndView.addObject("dependentes", this.dependenteRepository.getDependentes(idCliente));
				return modelAndView;
			}else {
				
				modelAndView.addObject("cliente", cliente);
				modelAndView.addObject("messagensErro", this.messagensErro);
				modelAndView.addObject("dependentes", this.dependenteRepository.getDependentes(idCliente));
				return modelAndView;
			}
		} else {

			modelAndView.addObject("messagensErro", this.messagensErro);
			modelAndView.addObject("cliente", cliente);
			modelAndView.addObject("dependentes", this.dependenteRepository.getDependentes(idCliente));
			return modelAndView;
		}

	}

	@GetMapping("**/removerdependenteCliente/{iddependente}")
	public ModelAndView removerDependente(@PathVariable("iddependente") Integer iddependente) {

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
	@RequestMapping("**/listarclientes")
	public ModelAndView listarTodos() {
		Iterable<Cliente> clientes = this.clienteService.listarTodos();
		ModelAndView andView = new ModelAndView(Navegacao.LISTAGEM_CLIENTES);
		List<Cliente> lista = (List<Cliente>) clientes;
		
		if(!ValidadorCliente.getInstance().validatePesquisa(lista)) {
			 ValidadorCliente.getInstance().getMsgErros().add("Não há items cadastrados");
			 andView.addObject("clientes", clientes);
			 andView.addObject("msg", ValidadorCliente.getInstance().getMsg());
			 andView.addObject("messagensErro", ValidadorCliente.getInstance().getMsgErros());
			 return andView;
		}else {
			andView.addObject("clientes", clientes);
			andView.addObject("msg", ValidadorCliente.getInstance().getMsg());
			andView.addObject("messagensErro", ValidadorCliente.getInstance().getMsgErros());
			return andView;
		}
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

	private boolean validateCliente(String nome, String email, String celular, String foneComercial,
			String foneResidencial, String cpf, String endereco, String localTrabalho) {
		this.messagensErro = new ArrayList<>();
		boolean retorno = true;

		if (nome.isEmpty()) {
			this.messagensErro.add("Campo Nome deve ser informado!");
		}

		if (email.isEmpty()) {
			this.messagensErro.add("Campo E-mail deve ser informado!");
		}

		if (celular.isEmpty()) {
			this.messagensErro.add("Campo Celular deve ser informado!");
		}

		if (foneResidencial.isEmpty()) {
			this.messagensErro.add("Campo Telefone Residencial deve ser informado!");
		}

		if (foneComercial.isEmpty()) {
			this.messagensErro.add("Campo Telefone comercial deve ser informado!");
		}

		if (cpf.isEmpty()) {
			this.messagensErro.add("Campo Cpf deve ser informado!");
		}

		if (endereco.isEmpty()) {
			this.messagensErro.add("Campo Endereço deve ser informado!");
		}

		if (localTrabalho.isEmpty()) {
			this.messagensErro.add("Campo Local de Trabalho deve ser informado!");
		}

		if (!this.messagensErro.isEmpty()) {
			retorno = false;
		}

		return retorno;
	}

	public boolean validadeQuantidadeDependente(Cliente usuario) {
		boolean retorno = true;
		this.messagensErro = new ArrayList<String>();

		if (usuario.getDependentes().size() >= 3) {
			this.messagensErro.add("Não é possível adicionar mais de três dependentes a um cliente");
			retorno = false;
		}
		return retorno;
	}

	public boolean validadeDependente(Dependente dependente) {

		boolean retorno = true;
		this.messagensErro = new ArrayList<>();

		if (dependente.getNome().isEmpty()) {
			this.messagensErro.add("Campo nome deve ser informado!");
			retorno = false;
		}

		if (dependente.getEmail().isEmpty()) {
			this.messagensErro.add("Campo e-mail deve ser informado!");
			retorno = false;
		}

		if (dependente.getSexo().isEmpty()) {
			this.messagensErro.add("Campo sexo deve ser informado!");
			retorno = false;
		}

		return retorno;
	}
	
	
	
}
