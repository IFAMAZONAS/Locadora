package br.com.cin.locadora.controlador;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cin.locadora.controlador.util.Navegacao;
import br.com.cin.locadora.model.Cliente;
import br.com.cin.locadora.model.Filme;
import br.com.cin.locadora.model.Fornecedor;
import br.com.cin.locadora.model.Genero;
import br.com.cin.locadora.model.TipoMidia;
import br.com.cin.locadora.model.ValoresLocacao;
import br.com.cin.locadora.model.repository.FilmeRepository;
import br.com.cin.locadora.model.repository.ValorLocacaoRepository;
import br.com.cin.locadora.servico.FilmeService;
import br.com.cin.locadora.servico.FornecedorService;
import br.com.cin.locadora.servico.GeneroService;
import br.com.cin.locadora.servico.TipoMidiaService;
import cucumber.api.java.it.Date;

@Controller
@RequestMapping(value = "filme")
public class FilmeController {
	@Autowired
	TipoMidiaService tipoMidiaService;

	@Autowired
	GeneroService generoService;

	@Autowired
	private FilmeService filmeService;
	@Autowired
	private FilmeRepository filmeRepository;
	@Autowired
	private FornecedorService fornecedorService;
	
	@Autowired
	private ValorLocacaoRepository valorLocacaoRepository;

	Iterable<TipoMidia> tipoMidiaLista;
	Iterable<Fornecedor> fornecedorLista;
	Iterable<Genero> generoLista;
	List<String> msg = new ArrayList<>();
	List<String> messagensErro = new ArrayList<String>();

	Page<Filme> page;
	private Filme objeto;

	@RequestMapping(method = RequestMethod.GET, value = "**/cadastrofilme")
	@ResponseBody
	public ModelAndView form(@PageableDefault(size = 5) Pageable pageable) {
		ModelAndView andView = new ModelAndView(Navegacao.CADASTRAR_FILME);
		this.tipoMidiaLista = this.tipoMidiaService.listarTodos();
		this.generoLista = this.generoService.listarTodos();
		this.fornecedorLista = this.fornecedorService.listarTodos();
		
		andView.addObject("msg", this.msg);
		andView.addObject("generos", generoLista);
		andView.addObject("tipoMidia", this.tipoMidiaLista);
		andView.addObject("fornecedores", this.fornecedorLista);
		andView.addObject("filme", new Filme());
		page = this.filmeRepository.findAll(pageable);
		andView.addObject("page", page);
		return andView;
	}
	
	@GetMapping("**/buscarporid")
	@ResponseBody
	public Filme buscarPorId(Integer idFilme) {
		 return this.filmeRepository.findById(Integer.valueOf(idFilme)).get();
	}
	
	@GetMapping("**/visualizar/{idFilme}")
	public ModelAndView buscar(@PathVariable("idFilme") Integer idFilme, @PageableDefault(size = 5) Pageable pageable) {
		ModelAndView andView = new ModelAndView(Navegacao.VIEW_FILME);
		try {
			  Filme filme = this.filmeRepository.findById(idFilme).get();
			  
			  
			 andView.addObject("filme", filme);
		} catch (NumberFormatException e) {
			
		}
		return andView;
		
	}
	
	@GetMapping("**/modal/{idFilme}")
	public ModelAndView modal(@PathVariable("idFilme") Integer idFilme) {
		 ModelAndView andView = new ModelAndView(Navegacao.MODAL_FILME);
		  Filme filme = this.filmeRepository.findById(idFilme).get();		  
		  TipoMidia midia = filme.getTipoMidia();	
		  
		  andView.addObject("filme", filme);
		  andView.addObject("midia", midia);
		  andView.addObject("valores", this.valorLocacaoRepository.findById(filme.getValor().getId()).get());
		  andView.addObject("fornecedor", this.fornecedorService.buscarPorId(filme.getIdFornecedor().getId()));
		 
		 return andView;
	}

	
	@GetMapping("/editarfilme/{idFilme}")
	public ModelAndView editar(@PathVariable("idFilme") Integer idFilme, @PageableDefault(size = 5) Pageable pageable) {
		ModelAndView andView = new ModelAndView(Navegacao.ALTERAR_FILME);
		try {
			this.generoLista = this.generoService.listarTodos();
			this.fornecedorLista = this.fornecedorService.listarTodos();
					
			andView.addObject("filme", this.filmeRepository.findById(Integer.valueOf(idFilme)));
		} catch (NumberFormatException e) {
			
		}
		return andView;
		
	}

	
	@RequestMapping(value = "**/salvarfilme", method = {RequestMethod.POST, RequestMethod.GET} )
	public ModelAndView salvarFilme(@RequestParam("numeroSerie") String numeroSerie,
			@RequestParam("tituloPortugues") String tituloPortugues,
			@RequestParam("toriginal") String tituloOriginal,
			@RequestParam("elenco") String elenco,
			@RequestParam("pais") String pais,
			@RequestParam("ano") String ano,
			@RequestParam("direcao") String direcao,
			@RequestParam("duracao") String duracao,
			@RequestParam("dataaquisicao") String dataquisicao,
			@RequestParam("genero") String genero,
			
			@RequestParam("fornecedor") String fornecedor,
			@RequestParam("midia") String midia,
			@RequestParam("sinopse") String sinopse,
			@PageableDefault(size = 5) Pageable pageable) {
		
		System.err.println("Data Aquisição:"+dataquisicao);
			ModelAndView andView = new ModelAndView(Navegacao.CADASTRAR_FILME);
		if (this.validadeCliente(numeroSerie, tituloPortugues, tituloOriginal, pais, ano, direcao, elenco,sinopse,
				duracao, dataquisicao, fornecedor, midia)) {
			
			
			
			 Filme filme = this.montarObjeto(numeroSerie, 
					 tituloOriginal, tituloPortugues, 
					 pais, ano, direcao, elenco, sinopse, duracao, dataquisicao, fornecedor, midia,genero);
			this.filmeService.salvar(filme);
			this.msg.add("Operação realizada com sucesso!");
			this.tipoMidiaLista = this.tipoMidiaService.listarTodos();
			this.generoLista = this.generoService.listarTodos();
			this.fornecedorLista = this.fornecedorService.listarTodos();
			andView.addObject("fornecedores", this.fornecedorLista);
			andView.addObject("msg", this.msg);
			andView.addObject("generos", generoLista);
			andView.addObject("tipoMidia", this.tipoMidiaLista);
			andView.addObject("filme", new Filme());
			this.msg = new ArrayList<>();
			page = this.filmeRepository.findAll(pageable);
			andView.addObject("page", page);
		} else {
			andView.addObject("messagensErro", this.messagensErro);
			this.tipoMidiaLista = this.tipoMidiaService.listarTodos();
			this.fornecedorLista = this.fornecedorService.listarTodos();
			this.generoLista = this.generoService.listarTodos();
			andView.addObject("msg", this.msg);
			andView.addObject("generos", generoLista);
			andView.addObject("tipoMidia", this.tipoMidiaLista);
			andView.addObject("fornecedores", this.fornecedorLista);
			this.messagensErro = new ArrayList<>();
			
			andView.addObject("filme", new Filme());
			page = this.filmeRepository.findAll(pageable);
			andView.addObject("page", page);
		}

		return andView;
	}

	private boolean validadeCliente(String numeroSerie, String tituloOriginal, String tituloPortugues, String pais,
			String ano, String direcao, String elenco, String sinopse, String duracao, String dataAquisicao,
			String fornecedor, String midia) {
		boolean retorno = true;
		this.messagensErro = new ArrayList<>();
		if (tituloOriginal.isEmpty()) {
			this.messagensErro.add("Campo titulo original é obrigatório");
			retorno = false;
		}
		if (tituloPortugues.isEmpty()) {
			this.messagensErro.add("Campo titulo Portugues é obrigatório");
			retorno = false;
		}
		
		if (direcao.isEmpty()) {
			this.messagensErro.add("Campo direção  é obrigatório");
			retorno = false;
		}
		return retorno;
	}

	/*****
	 * 
	 * @param tituloOriginal
	 * @param tituloPortugues
	 * @param pais
	 * @param ano
	 * @param direcao
	 * @param elenco
	 * @param sinopse
	 * @param duracao
	 * @param categoria
	 * @param dataAquisicao
	 * @param idFornecedor
	 * @param tipoMidia
	 * @param valor
	 * @return
	 */
	
	/****
	 * 
	 * @param tituloOriginal
	 * @param tituloPortugues
	 * @param pais
	 * @param ano
	 * @param direcao
	 * @param elenco
	 * @param sinopse
	 * @param duracao
	 * @param categoria
	 * @param dataAquisicao
	 * @param idFornecedor
	 * @param tipoMidia
	 * @param valor
	 * @return
	 */
	private Filme montarObjeto(String numeroSerie, String tituloOriginal, String tituloPortugues, String pais,
			String ano, String direcao, String elenco, String sinopse, String duracao, String dataAquisicao,
			String idFornecedor, String tipoMidia, String genero) {
		Filme novoFilme = new Filme();
		novoFilme.setNumeroSerie(numeroSerie);
		novoFilme.setTipoMidia(this.obterTipoMidia(tipoMidia));
		novoFilme.setIdFornecedor(this.obterFornecedor(idFornecedor));
		novoFilme.setIdGenero(this.obrterGenero(genero));
		novoFilme.setDuracao(duracao);
		novoFilme.setTituloOriginal(tituloOriginal);
		novoFilme.setTituloPortugues(tituloPortugues);
		novoFilme.setPais(pais);
		novoFilme.setAno(Integer.valueOf(ano));
		novoFilme.setDirecao(direcao);
		novoFilme.setSinopse(sinopse);
		novoFilme.setDataAquisicao(new java.util.Date());
		
	
		
		novoFilme.setValor(this.definirValor(novoFilme.getTipoMidia()));

		return novoFilme;
	}
	

	private Genero obrterGenero(String genero) {
		return this.generoService.buscarPorId(Integer.valueOf(genero));
	}

	private ValoresLocacao definirValor(TipoMidia tipoMidia) {
		
		ValoresLocacao valorLocacao = this.valorLocacaoRepository.findValoresByName(tipoMidia.getDescricao()).get(0);
		return valorLocacao ;
	}

	private Fornecedor obterFornecedor(String idFornecedor) {
		Fornecedor fornecedor = this.fornecedorService.buscarPorId(Integer.valueOf(idFornecedor));
		return fornecedor;
	}

	private TipoMidia obterTipoMidia(String tipoMidia) {
		Integer idTipoMidia = Integer.valueOf(tipoMidia);
		TipoMidia midia = this.tipoMidiaService.buscarPorId(idTipoMidia);
		return midia;
	}

	@GetMapping(value = "**/listarfilmes")
	
	public ModelAndView getEmployees(@PageableDefault(size = 10) Pageable pageable) {
		ModelAndView andView = new ModelAndView(Navegacao.LISTAGEM_FILMES);
		 page = this.filmeRepository.findAll(pageable);
		andView.addObject("page", page);
		return andView;
	}
	
	
	

}
