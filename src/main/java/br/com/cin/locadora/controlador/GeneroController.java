package br.com.cin.locadora.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cin.locadora.controlador.util.Navegacao;
import br.com.cin.locadora.model.Cliente;
import br.com.cin.locadora.model.Fornecedor;
import br.com.cin.locadora.model.Genero;
import br.com.cin.locadora.model.repository.GeneroRepository;
import br.com.cin.locadora.servico.GeneroService;

@Controller
@RequestMapping(value="genero")
public class GeneroController {
	
	@Autowired
	GeneroService generoService;
	
	@Autowired
	GeneroRepository repository;
	
	private List<String> msgErros;
	private List<String> msg;
	
	List<String> messagensErro = new ArrayList<String>();
	
	public void setMessagensErro(List<String> messagensErro) {
		this.messagensErro = messagensErro;
	}

	public List<String> getMessagensErro() {
		return messagensErro;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "home")
	@ResponseBody
	public ModelAndView home(HttpServletResponse response, HttpServletRequest request) throws IOException {
		ModelAndView andView = new ModelAndView("home");
		return andView;

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "**/cadastrogenero")
	@ResponseBody
	public ModelAndView form(@PageableDefault(size = 5) Pageable pageable) {
		ModelAndView andView = new ModelAndView(Navegacao.CADASTRAR_GENERO);
		Page<Genero> page = repository.findAll(pageable);	
		Iterable<Genero> generos = this.repository.findAll();
		andView.addObject("generos", new ArrayList<>());
		andView.addObject("genero", new Genero());
		andView.addObject("msg", this.msg);
		andView.addObject("messagensErro", this.messagensErro);
		andView.addObject("page", page);
		this.messagensErro = new ArrayList<>();
		this.msg = new ArrayList<>();
		return andView;
	}	
	
	@RequestMapping(value = "**/salvargenero", method=RequestMethod.POST)
	public ModelAndView salvar(Genero genero, @PageableDefault(size = 5) Pageable pageable) {
		ModelAndView modelAndView = new ModelAndView(Navegacao.CADASTRAR_GENERO);
		Page<Genero> page = repository.findAll(pageable);
	if (this.validadeGenero(genero.getDescricao())) {
		this.msg = new ArrayList<String>();
		this.msg.add("Operação realizada com sucesso!");
		modelAndView.addObject("msg", this.msg);
		this.msg = new ArrayList<>();
		this.generoService.salvarGenero(genero);
        Iterable<Genero> generos = this.repository.findAll();
        modelAndView.addObject("page", page);
        modelAndView.addObject("genero", new Genero());
		return modelAndView;
	} else {
		modelAndView.addObject("messagensErro", messagensErro);
		modelAndView.addObject("page", page);
		return modelAndView;
		
	}
	
	}
	
	private boolean validadeGenero(String descricao) {
		boolean retorno = true;
		this.messagensErro = new ArrayList<>();
		
		if(descricao.isEmpty()) {
			this.messagensErro.add("Campo Descrição deve ser informado!");
		}
	
		if(!this.messagensErro.isEmpty()) {
			retorno = false;
		}
		return retorno;
	}	
	
	@PostMapping("**/pesquisargenero")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {
		ModelAndView modelAndView = new ModelAndView("fornecedor/cadastrogenero");		
		Iterable<Genero> generos = this.repository.findGeneroByName(nomepesquisa);
		modelAndView.addObject("generos",generos);
		modelAndView.addObject("genero", new Genero());
		return modelAndView;
	}
	
	@GetMapping("/editargenero/{idgenero}")
	public ModelAndView editar(@PathVariable("idgenero") Integer idgenero, @PageableDefault(size = 5) Pageable pageable) {
		
		ModelAndView modelAndView = new ModelAndView(Navegacao.CADASTRAR_GENERO);
		/*modelAndView.addObject("fornecedor", fornecedor.get());*/
		modelAndView.addObject("genero", this.repository.findById(idgenero));
		Page<Genero>page = this.repository.findAll(pageable);
		modelAndView.addObject("page", page);
		return modelAndView;
		
	}	
	
	@GetMapping("/removergenero/{idgenero}")
	public ModelAndView excluir(@PathVariable("idgenero") Integer idgenero, @PageableDefault(size = 8) Pageable pageable) {
		this.repository.deleteById(idgenero);
		ModelAndView view = new ModelAndView(Navegacao.CADASTRAR_GENERO);
		Page<Genero> page = repository.findAll(pageable);
		view.addObject("generos", this.repository.findAll());
		view.addObject("genero", new Cliente());
		view.addObject("page", page);
		return view;
	}
	
	
	@GetMapping(value="**/listargenero")
	public ModelAndView getEmployees(@PageableDefault(size = 8) Pageable pageable ) {
			ModelAndView andView = new ModelAndView(Navegacao.LISTAGEM_GENERO);
	        Page<Genero> page = repository.findAll(pageable);
	        Iterable<Genero> generos = this.repository.findAll();
	       
			andView.addObject("generos",generos);
			andView.addObject("genero", new Genero());
			andView.addObject("mgs", this.msg);
			andView.addObject("messagensErro", this.msgErros);
	        andView.addObject("page", page);
	        return andView;
	   }
	
}
