package br.com.cin.locadora.controlador;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/fornecedor")
public class FornecedorController {

	@Autowired
	FornecedorRepository repository;
	
	@Autowired
	FornecedorService fornecedorservice;
	
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
	
	@RequestMapping(value = "salvarfornecedor", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView salvar(@RequestParam("cnpj") String cnpj, @RequestParam("razao_social") String razaosocial,
			@RequestParam("endereco") String endereco, @RequestParam("telefone") String telefone,
			@RequestParam("pessoa_contato") String pessoacontato, HttpSession session) {

		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setCnpj(cnpj);
		fornecedor.setRazaosocial(razaosocial);
		fornecedor.setEndereco(endereco);
		fornecedor.setTelefone(telefone);
		fornecedor.setPessoacontato(pessoacontato);
		
		
		this.fornecedorservice.salvarFornecedor(fornecedor);

		return this.form();

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

}
