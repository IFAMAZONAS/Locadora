package br.com.cin.locadora.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import br.com.cin.locadora.model.repository.FilmeRepository;
import br.com.cin.locadora.servico.FilmeService;
import br.com.cin.locadora.servico.FornecedorService;
import br.com.cin.locadora.servico.GeneroService;
import br.com.cin.locadora.servico.TipoMidiaService;
import cucumber.api.java.it.Date;

@Controller
@RequestMapping(value = "/filme")
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
	public Filme buscarPorId(@RequestParam("idFilme") String idFilme) {
		 return this.filmeRepository.findById(Integer.valueOf(idFilme)).get();
	}
	
	@GetMapping("/editarfilme/{idFilme}")
	public ModelAndView editar(@PathVariable("idFilme") Integer idFilme, @PageableDefault(size = 5) Pageable pageable) {
		ModelAndView andView = new ModelAndView(Navegacao.CADASTRAR_FILME);
		try {
			this.generoLista = this.generoService.listarTodos();
			this.fornecedorLista = this.fornecedorService.listarTodos();
			
			andView.addObject("msg", this.msg);
			andView.addObject("generos", generoLista);
			andView.addObject("tipoMidia", this.tipoMidiaLista);
			andView.addObject("fornecedores", this.fornecedorLista);
			page = this.filmeRepository.findAll(pageable);
			andView.addObject("page", page);
			
			andView.addObject("filme", this.filmeRepository.findById(Integer.valueOf(idFilme)));
		} catch (NumberFormatException e) {
			
		}
		return andView;
		
	}

	
	@RequestMapping(value = "**/salvarfilme", method = {RequestMethod.POST, RequestMethod.GET} )
	public ModelAndView salvarFilme(Filme filme, @PageableDefault(size = 5) Pageable pageable) {
			ModelAndView andView = new ModelAndView(Navegacao.CADASTRAR_FILME);
		if (this.validadeCliente(filme.getNumeroSerie(), filme.getTituloOriginal(), filme.getTituloPortugues(), filme.getPais(), filme.getAno(), filme.getDirecao(), filme.getElenco(),filme.getSinopse(),
				filme.getDuracao(), filme.getDataAquisicao(), filme.getIdFornecedor(), filme.getTipoMidia(), filme.getValor())) {
			
			
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
			filme = new Filme();
			andView.addObject("filme", new Filme());
			page = this.filmeRepository.findAll(pageable);
			andView.addObject("page", page);
		}

		return andView;
	}

	private boolean validadeCliente(String numeroSerie, String tituloOriginal, String tituloPortugues, String pais,
			Integer ano, String direcao, String elenco, String sinopse, String duracao, java.util.Date dataAquisicao,
			Fornecedor idFornecedor, TipoMidia tipoMidia, Double valor) {
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
			String ano, String direcao, String elenco, String sinopse, String duracao, Date dataAquisicao,
			String idFornecedor, String tipoMidia, Double valor) {
		Filme novoFilme = new Filme();
		novoFilme.setNumeroSerie(numeroSerie);
		novoFilme.setTipoMidia(this.obterTipoMidia(tipoMidia));
		novoFilme.setIdFornecedor(this.obterFornecedor(idFornecedor));
		novoFilme.setDuracao(duracao);
		novoFilme.setTituloOriginal(tituloOriginal);
		novoFilme.setTituloPortugues(tituloPortugues);
		novoFilme.setPais(pais);
		novoFilme.setAno(Integer.valueOf(ano));
		novoFilme.setDirecao(direcao);
		novoFilme.setSinopse(sinopse);

		return novoFilme;
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
