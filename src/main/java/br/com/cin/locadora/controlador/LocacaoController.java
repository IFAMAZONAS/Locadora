package br.com.cin.locadora.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cin.locadora.controlador.auto.Country;
import br.com.cin.locadora.controlador.auto.SuggestionWrapper;
import br.com.cin.locadora.controlador.util.Navegacao;
import br.com.cin.locadora.model.Cliente;
import br.com.cin.locadora.model.Filme;
import br.com.cin.locadora.model.Locacao;
import br.com.cin.locadora.model.LocacaoFilme;
import br.com.cin.locadora.model.repository.ClienteRepository;
import br.com.cin.locadora.model.repository.FilmeRepository;
import br.com.cin.locadora.servico.LocacaoService;

@Controller
@RequestMapping(value = "/locacao")
public class LocacaoController {

	@Autowired
	private ClienteRepository clienterepository;

	@Autowired
	ClienteRepository repository;
	@Autowired
	FilmeRepository filmeRepository;
	List<String> msg = new ArrayList<>();
	List<String> messagensErro = new ArrayList<String>();

	List<Filme> lista = new ArrayList<>();
	List<Filme> listaAux = new ArrayList<>();
	
	Cliente clienteLocacao;	
	LocacaoFilme locacaoFilme;
	Locacao locacao;
	
	@Autowired
	LocacaoService locacaoService;

	HashMap<Filme, Double> listaFilmes = new HashMap<>();

	private Double valorTemporario = 0.0;

	@RequestMapping(method = RequestMethod.GET, value = "**/cadastrolocacao")
	@ResponseBody
	public ModelAndView forme() {
		ModelAndView andView = new ModelAndView(Navegacao.INICIAR_LOCACAO);
		this.listaAux = new ArrayList<>();
		andView.addObject("filmes", new ArrayList<>());
		this.valorTemporario = 0.0;
		andView.addObject("valorLocacao", this.valorTemporario);
		andView.addObject("cliente", new Cliente());
		return andView;
	}

	@GetMapping("**/entrarpesquisa")
	public ModelAndView entrarBusca() {
		ModelAndView andView = new ModelAndView(Navegacao.BUSCAR_CLIENTE_LOCACAO);
		return andView;
	}

	@RequestMapping(value="**/listarclientes", method=RequestMethod.GET)
	public ModelAndView entrarClientes( @RequestParam("pesquisa") String nome) {
		ModelAndView andView = new ModelAndView(Navegacao.LISTAR_CLIENTE_LOCACAO);
		Iterable<Cliente> clientesPesquisa = this.clienterepository.findPessoaByName(nome.toUpperCase());
		andView.addObject("clientes", clientesPesquisa);
		
		return andView;
	}
	
	@GetMapping("**/buscarClienteLocacao/{idcliente}")
	public ModelAndView buscarClienteLocacao(@PathVariable("idcliente") Integer idCliente) {
		ModelAndView andView = new ModelAndView(Navegacao.INICIAR_LOCACAO);
		this.clienteLocacao = this.clienterepository.findById(idCliente).get();
		andView.addObject("valorLocacao", this.valorTemporario);
		andView.addObject("filmes", this.listaAux);
		andView.addObject("cliente", this.clienteLocacao);
		System.out.println("Cliente:"+this.clienteLocacao.getCpf());
		return andView;
		
	}
	

	@PostMapping("**/pesquisarcliente")
	public ModelAndView buscarPorNome(@RequestParam("nomepesquisa") String nomepesquisa) {
		ModelAndView andView = new ModelAndView(Navegacao.BUSCAR_CLIENTE_LOCACAO);
		return andView;
	}

	@GetMapping("/removerFilmeLocacao/{idcliente}")
	public ModelAndView excluir(@PathVariable("idcliente") Integer idcliente) {
		// this.repository.deleteById(idcliente);
		ModelAndView andView = new ModelAndView(Navegacao.INICIAR_LOCACAO);
		this.remover(idcliente);
		andView.addObject("valorLocacao", this.valorTemporario);
		andView.addObject("filmes", this.listaAux);
		andView.addObject("cliente", this.clienteLocacao);
		return andView;
	}

	private void remover(Integer idcliente) {

		for (Filme filme : this.listaAux) {

			if (filme.getIdFilme() == idcliente) {
				this.listaAux.remove(filme);
				this.valorTemporario = this.valorTemporario - filme.getValor().getValor();
				break;
			}

		}
	}

	@RequestMapping(value = "**/buscar", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView buscar(@RequestParam("nome") String nome, Model model) {
		this.messagensErro = new ArrayList<>();
		System.err.println("NOME :::::" + nome);
		model.addAttribute("title", "autocomplete countries example");

		ModelAndView andView = new ModelAndView(Navegacao.INICIAR_LOCACAO);

		if (!nome.isEmpty()) {

			
				this.lista = this.filmeRepository.findFilmeByName(nome.toUpperCase());
				this.listaAux.add(this.lista.get(0));

				this.valorTemporario = this.valorTemporario + lista.get(0).getValor().getValor();

				andView.addObject("filmes", this.listaAux);
				andView.addObject("valorLocacao", this.valorTemporario);
				andView.addObject("cliente", this.clienteLocacao);
			

		} else {
			this.messagensErro.add("Por Favor, adicione o nome do filme na busca");
			andView.addObject("filmes", this.listaAux);
			andView.addObject("messagensErro", this.messagensErro);
			andView.addObject("valorLocacao", this.valorTemporario);
			andView.addObject("cliente", this.clienteLocacao);
		}

		return andView;
	}

	
// http://localhost:8080/suggestion?searchstr=car

	/**
	 * the rest controller which is requested by the autocomplete input field
	 * instead of the countries here could also be made a DB request
	 */
	@RequestMapping(value = "**/suggestion", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public SuggestionWrapper autocompleteSuggestions(@RequestParam("searchstr") String searchstr) {
		System.out.println("searchstr: " + searchstr);

		ArrayList<Country> suggestions = new ArrayList<>();

		Iterable<Filme> locales = this.filmeRepository.findAll();

		for (Filme countryCode : locales) {

			Locale obj = new Locale("", countryCode.getTituloPortugues());
			// add all countries to the arraylist
			// if on the query string
			if (obj.getDisplayCountry().toLowerCase().contains(searchstr.toLowerCase())) {
				suggestions.add(new Country(obj.getDisplayCountry()));
			}
		}

		// truncate the list to the first n, max 20 elements
		int n = suggestions.size() > 20 ? 20 : suggestions.size();
		List<Country> sulb = new ArrayList<>(suggestions.subList(0, n));

		SuggestionWrapper sw = new SuggestionWrapper();
		sw.setSuggestions(sulb);
		return sw;
	}
	/****
	 * 
	 */
	public void iniciarLocacao() {
		 ModelAndView andView = new ModelAndView();
		
		 this.locacao = new Locacao();
		 Cliente cliente = this.clienterepository.findById(195).get();
		 
		 
	}
	/***
	 * 
	 * @param locacao
	 * @param listaAux2
	 */
	private void adicionarFilmesLocacao(Locacao locacao, List<Filme> listaAux2) {
		 
	}

}
