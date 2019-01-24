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
	
	private List<String> msgErros;
	private List<String> msg;
	
	@RequestMapping( "**/home")
	public void home(HttpServletResponse response, HttpServletRequest request) throws IOException {
		String context = request.getContextPath();
		response.sendRedirect(context+"/home");
		
	}
	
	@RequestMapping("**/cadastrofornecedor")
	public ModelAndView form() {
		ModelAndView andView = new ModelAndView("fornecedor/cadastrofornecedor");
		Iterable<Fornecedor> fornecedores = this.repository.findAll();
		andView.addObject("fornecedores",fornecedores);
		andView.addObject("fornecedor", new Fornecedor());
		return andView;
	}
	
	@RequestMapping(value = "**/salvarfornecedor", method=RequestMethod.POST)
	public ModelAndView salvar(@RequestParam("cnpj") String cnpj, @RequestParam("razaosocial") String razaosocial,
			@RequestParam("endereco") String endereco, @RequestParam("telefone") String telefone,
			@RequestParam("pessoacontato") String pessoacontato, @RequestParam("id") String id) {
		Fornecedor fornecedor = new Fornecedor();
		ModelAndView andView = new ModelAndView(Navegacao.CADASTRAR_FORNECEDOR);
		Iterable<Fornecedor> fornecedores = this.repository.findAll();
		
		
		if(validadeFornecedor(cnpj, razaosocial, telefone, endereco)) {
			this.msg = new ArrayList<String>();
			try {
				int idFornecedor = Integer.valueOf(id);
				fornecedor.setId(idFornecedor);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			fornecedor.setCnpj(cnpj);
			fornecedor.setRazaosocial(razaosocial);
			fornecedor.setEndereco(endereco);
			fornecedor.setTelefone(telefone);
			fornecedor.setPessoacontato(pessoacontato);
			
			this.fornecedorservice.salvarFornecedor(fornecedor);
			this.msg.add("Operação realizada com sucesso!");
			andView.addObject("fornecedores",fornecedores);
			andView.addObject("fornecedor", new Fornecedor());
			andView.addObject("mgs", this.msg);
			this.msg = new ArrayList<>();
			return andView;
		}else {
			andView.addObject("fornecedores",fornecedores);
			andView.addObject("fornecedor", new Fornecedor());
			andView.addObject("messagensErro", this.msgErros);
			
			return andView;
		}

	}
	
	private boolean validadeFornecedor(String cnpj, String razaosocial, String telefone, String endereco) {
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
	public ModelAndView editar(@PathVariable("idfornecedor") Integer idfornecedor) {
		
		
		ModelAndView modelAndView = new ModelAndView(Navegacao.CADASTRAR_FORNECEDOR);
		/*modelAndView.addObject("fornecedor", fornecedor.get());*/
		modelAndView.addObject("fornecedor", this.repository.findById(idfornecedor));
		
		return modelAndView;
		
	}
	
	@GetMapping("/removerfornecedor/{idfornecedor}")
	public ModelAndView excluir(@PathVariable("idfornecedor") Integer idfornecedor) {	
		this.repository.deleteById(idfornecedor);
		ModelAndView view = new ModelAndView(Navegacao.CADASTRAR_FORNECEDOR);
		view.addObject("fornecedores", this.repository.findAll());
		view.addObject("fornecedor", new Fornecedor());
		return this.form();
	}
	
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
