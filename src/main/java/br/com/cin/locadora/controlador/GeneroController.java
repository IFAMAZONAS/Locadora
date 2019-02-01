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
import br.com.cin.locadora.model.Fornecedor;
import br.com.cin.locadora.model.Genero;
import br.com.cin.locadora.model.repository.GeneroRepository;
import br.com.cin.locadora.servico.GeneroService;

@Controller
@RequestMapping(value="genero")
public class GeneroController {
	
	@Autowired
	GeneroRepository repository;
	
	@Autowired
	GeneroService generoservice;
	
	@Autowired
	GeneroRepository generoRepository;
	
	private List<String> msgErros;
	private List<String> msg;
	
	@RequestMapping( "**/home")
	public void home(HttpServletResponse response, HttpServletRequest request) throws IOException {
		String context = request.getContextPath();
		response.sendRedirect(context+"/home");
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "**/cadastrogenero")
	@ResponseBody
	public ModelAndView form(@PageableDefault(size = 5) Pageable pageable) {
		ModelAndView andView = new ModelAndView(Navegacao.CADASTRAR_GENERO);
		Iterable<Genero> generos  = this.repository.findAll();
		andView.addObject("generos",generos);
		andView.addObject("genero", new Genero());
		Page<Genero>page = this.generoRepository.findAll(pageable);
		andView.addObject("page", page);
		return andView;
	}	
	
	@RequestMapping(value = "**/salvargenero", method=RequestMethod.POST)
	public ModelAndView salvar(@RequestParam("descricao") String descricao,@RequestParam("idGenero") String id, @PageableDefault(size = 5) Pageable pageable) {
		Genero genero = new Genero();
		ModelAndView andView = new ModelAndView(Navegacao.CADASTRAR_GENERO);
		Iterable<Genero> generos = this.repository.findAll();
		
		
		if(validadeGenero(descricao)) {
			this.msg = new ArrayList<String>();
			try {
				int idGenero = Integer.valueOf(id);
				genero.setIdGenero(idGenero);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			genero.setDescricao(descricao);			
				
			
			this.generoservice.salvarGenero(genero);
			this.msg.add("Operação realizada com sucesso!");
			andView.addObject("generos",generos);
			andView.addObject("genero", new Genero());
			andView.addObject("mgs", this.msg);
			this.msg = new ArrayList<>();
			Page<Genero>page = this.generoRepository.findAll(pageable);
			andView.addObject("page", page);
			return andView;
		}else {
			andView.addObject("generos",generos);
			andView.addObject("genero", new Genero());
			andView.addObject("messagensErro", this.msgErros);
			
			return andView;
		}

	}
	
	private boolean validadeGenero(String descricao) {
		boolean retorno = true;
		this.msgErros = new ArrayList<>();
		if(descricao.isEmpty()) {
			this.msgErros.add("Campo Descrição deve ser informado!");
		}
	
		if(!this.msgErros.isEmpty()) {
			retorno = false;
		}
		return retorno;
	}	
	
	@PostMapping("**/pesquisargenero")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {
		ModelAndView modelAndView = new ModelAndView("genero/cadastrogenero");		
		Iterable<Genero> generos = this.repository.findGeneroByName(nomepesquisa);
		modelAndView.addObject("generos", generos);
		modelAndView.addObject("genero", new Genero());
		return modelAndView;
	}
	
	@GetMapping("/editargenero/{idgenero}")
	public ModelAndView editar(@PathVariable("idgenero") Integer idgenero, @PageableDefault(size = 5) Pageable pageable) {
		
		ModelAndView modelAndView = new ModelAndView(Navegacao.CADASTRAR_GENERO);
		/*modelAndView.addObject("fornecedor", fornecedor.get());*/
		modelAndView.addObject("genero", this.repository.findById(idgenero));
		Page<Genero>page = this.generoRepository.findAll(pageable);
		modelAndView.addObject("page", page);
		
		return modelAndView;
		
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
