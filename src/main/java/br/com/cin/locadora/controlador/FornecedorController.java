package br.com.cin.locadora.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.cin.locadora.controlador.util.Navegacao;
import br.com.cin.locadora.model.Fornecedor;
import br.com.cin.locadora.model.repository.FornecedorRepository;
import br.com.cin.locadora.servico.FornecedorService;

@Controller
@RequestMapping(value="/fornecedor")
public class FornecedorController {

	@Autowired
	FornecedorRepository repository;
	
	@Autowired
	FornecedorService fornecedorservice;
	@Autowired
	FornecedorRepository fornecedorRepository;
	
	private List<String> msgErros;
	private List<String> msg;
	
	@RequestMapping( "**/home")
	public void home(HttpServletResponse response, HttpServletRequest request) throws IOException {
		String context = request.getContextPath();
		response.sendRedirect(context+"/home");
		
	}
	
	@RequestMapping("**/cadastrofornecedor")
	public ModelAndView form(@PageableDefault(size = 5) Pageable pageable) {
		ModelAndView andView = new ModelAndView(Navegacao.CADASTRAR_FORNECEDOR);
		Iterable<Fornecedor> fornecedores = this.repository.findAll();
		andView.addObject("fornecedores",fornecedores);
		andView.addObject("fornecedor", new Fornecedor());
		Page<Fornecedor>page = this.fornecedorRepository.findAll(pageable);
		andView.addObject("page", page);
		return andView;
	}
	
	@RequestMapping(value = "**/salvarfornecedor", method=RequestMethod.POST)
	public ModelAndView salvar(Fornecedor fornecedor, @PageableDefault(size = 5) Pageable pageable) {
		//Fornecedor fornecedor = new Fornecedor();
		ModelAndView andView = new ModelAndView(Navegacao.CADASTRAR_FORNECEDOR);
		Iterable<Fornecedor> fornecedores = this.repository.findAll();
		
		
		if(validadeFornecedor(fornecedor.getCnpj(), fornecedor.getRazaosocial(), fornecedor.getTelefone(), fornecedor.getEndereco(),fornecedor.getId())) {
			this.msg = new ArrayList<String>();
			
			/**try {
				int idFornecedor = Integer.valueOf(fornecedor.getId());
				fornecedor.setId(idFornecedor);
				
			} catch (Exception e) {
				andView = new ModelAndView(Navegacao.CADASTRAR_FORNECEDOR);
				fornecedores = this.repository.findAll();
				andView.addObject("fornecedores",fornecedores);
				andView.addObject("fornecedor", new Fornecedor());
				Page<Fornecedor>page = this.fornecedorRepository.findAll(pageable);
				andView.addObject("page", page);
				return andView;
			}**/
			
			
			
				
			
			this.fornecedorservice.salvarFornecedor(fornecedor);
			this.msg.add("Operação realizada com sucesso!");
			andView.addObject("fornecedores",fornecedores);
			andView.addObject("fornecedor", new Fornecedor());
			andView.addObject("msg", this.msg);
			this.msg = new ArrayList<>();
			Page<Fornecedor>page = this.fornecedorRepository.findAll(pageable);
			andView.addObject("page", page);
			return andView;
		}else {
			andView.addObject("fornecedores",fornecedores);
			andView.addObject("fornecedor", new Fornecedor());
			andView.addObject("messagensErro", this.msgErros);
			
			Page<Fornecedor>page = this.fornecedorRepository.findAll(pageable);
			andView.addObject("page", page);
			
			return andView;
		}

	}
	
	private boolean validadeFornecedor(String cnpj, String razaosocial, String telefone, String endereco, Integer id) {
		boolean retorno = true;
		this.msgErros = new ArrayList<>();
		if(cnpj.isEmpty()) {
			this.msgErros.add("Campo Cnpj deve ser informado!");
		}
		
		if(telefone.isEmpty()) {
			this.msgErros.add("Campo telefone deve ser informado!");
		}
		
		if(razaosocial.isEmpty()) {
			this.msgErros.add("Campo razão social deve ser informado!");
		}
		
		if(endereco.isEmpty()) {
			this.msgErros.add("Campo endereço deve ser informado!");
		}
		
		Iterable<Fornecedor> forn = this.repository.findCNPJ(cnpj);
		for(Fornecedor f: forn) {
			if( f != null){
				this.msgErros.add("Este número de CNPJ já foi cadastrado!");
			 }
		}
	
		if(!this.msgErros.isEmpty()) {
			retorno = false;
		}
		return retorno;
	}

	@PostMapping("**/pesquisarfornecedor")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {
		ModelAndView modelAndView = new ModelAndView("fornecedor/cadastrofornecedor");		
		Iterable<Fornecedor> fornecedores = this.repository.findPessoaByName(nomepesquisa);
		modelAndView.addObject("fornecedores",fornecedores );
		modelAndView.addObject("fornecedor", new Fornecedor());
		return modelAndView;
	}
	

	@GetMapping("/editarfornecedor/{idfornecedor}")
	public ModelAndView editar(@PathVariable("idfornecedor") String idfornecedor, @PageableDefault(size = 5) Pageable pageable) throws NumberFormatException{
		
		 ModelAndView modelAndView = new ModelAndView(Navegacao.CADASTRAR_FORNECEDOR);
		 Integer idForn;
		 try {
			 	idForn = Integer.valueOf(idfornecedor);
			
				modelAndView.addObject("fornecedor", this.repository.findById(Integer.valueOf(idfornecedor)));
				Page<Fornecedor>page = this.fornecedorRepository.findAll(pageable);
				modelAndView.addObject("page", page);
				
				return modelAndView;
		} catch (NumberFormatException e) {
			modelAndView = new ModelAndView(Navegacao.LISTAGEM_FORNECEDORES);
			Page<Fornecedor>page = this.fornecedorRepository.findAll(pageable);
			modelAndView.addObject("page", page);
		}
	
		return modelAndView;
		
	}
	
	@GetMapping("**/visualizarfonecedor/{idFornecedor}")
	public ModelAndView visualizarFornecedor(@PathVariable("idFornecedor") Integer idFornecedor) {
		ModelAndView andView = new ModelAndView(Navegacao.VIEW_FORNECEDOR);
		Fornecedor fornecedor = this.repository.findById(idFornecedor).get();
		andView.addObject("fornecedor", fornecedor);
		return andView;
	}
	
	/**@GetMapping("/removerfornecedor/{idfornecedor}")
	public ModelAndView excluir(@PathVariable("idfornecedor") Integer idfornecedor) {	
		this.repository.deleteById(idfornecedor);
		ModelAndView view = new ModelAndView(Navegacao.CADASTRAR_FORNECEDOR);
		view.addObject("fornecedores", this.repository.findAll());
		view.addObject("fornecedor", new Fornecedor());
		return this.form();
	}**/
	
	@GetMapping(value="**/listarfornecedor")
	public ModelAndView getEmployees(@PageableDefault(size = 8) Pageable pageable ) {
			ModelAndView andView = new ModelAndView(Navegacao.LISTAGEM_FORNECEDORES);
	        Page<Fornecedor> page = repository.findAll(pageable);
	        Iterable<Fornecedor> fornecedores = this.repository.findAll();
	       
			andView.addObject("fornecedores",fornecedores);
			andView.addObject("fornecedor", new Fornecedor());
			andView.addObject("mgs", this.msg);
			andView.addObject("messagensErro", this.msgErros);
	        andView.addObject("page", page);
	        return andView;
	   }
	
	 

}
