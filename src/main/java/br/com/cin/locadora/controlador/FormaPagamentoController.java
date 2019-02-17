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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cin.locadora.controlador.util.Navegacao;
import br.com.cin.locadora.model.FormaPagamento;
import br.com.cin.locadora.model.repository.FormaPagamentoRepository;
import br.com.cin.locadora.servico.FormaPagamentoService;

@Controller
@RequestMapping(value="formapagamento")
public class FormaPagamentoController {

	@Autowired
	FormaPagamentoService formaPagamentoService;
	
	@Autowired
	FormaPagamentoRepository repository;
	
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
	
	@RequestMapping(method = RequestMethod.GET, value = "**/cadastroformapagamento")
	@ResponseBody
	public ModelAndView form(@PageableDefault(size = 5) Pageable pageable) {
		ModelAndView andView = new ModelAndView(Navegacao.CADASTRAR_FORMA_PAGAMENTO);
		Page<FormaPagamento> page = repository.findAll(pageable);	
		Iterable<FormaPagamento> formapagamentos = this.repository.findAll();
		andView.addObject("formapagamentos", new ArrayList<>());
		andView.addObject("formaPagamento", new FormaPagamento());
		andView.addObject("msg", this.msg);
		andView.addObject("messagensErro", this.messagensErro);
		andView.addObject("page", page);
		this.messagensErro = new ArrayList<>();
		this.msg = new ArrayList<>();
		return andView;
	}
	
	@RequestMapping(value = "**/salvarformapagamento", method=RequestMethod.POST)
	public ModelAndView salvar(FormaPagamento formapagamento, @PageableDefault(size = 5) Pageable pageable) {
		ModelAndView modelAndView = new ModelAndView(Navegacao.CADASTRAR_FORMA_PAGAMENTO);
		Page<FormaPagamento> page; 
	if (this.validadeFormaPagamento(formapagamento.getDescricao())) {
		this.msg = new ArrayList<String>();
		this.msg.add("Operação realizada com sucesso!");
		modelAndView.addObject("msg", this.msg);
		this.msg = new ArrayList<>();
		this.formaPagamentoService.salvarFormaPagamento(formapagamento);
		page = repository.findAll(pageable);
        modelAndView.addObject("page", page);
        modelAndView.addObject("formaPagamento", new FormaPagamento());
		return modelAndView;
	} else {
		modelAndView.addObject("messagensErro", messagensErro);
		page = repository.findAll(pageable);
		modelAndView.addObject("page", page);
		return modelAndView;
		
	}
	
	}
	
	private boolean validadeFormaPagamento(String descricao) {
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
	
	@GetMapping("/editarformapagamento/{idFormaPagamento}")
	public ModelAndView editar(@PathVariable("idFormaPagamento") Integer idFormaPagamento, @PageableDefault(size = 5) Pageable pageable) {
		
		ModelAndView modelAndView = new ModelAndView(Navegacao.CADASTRAR_FORMA_PAGAMENTO);
		/*modelAndView.addObject("fornecedor", fornecedor.get());*/
		modelAndView.addObject("formaPagamento", this.repository.findById(idFormaPagamento));
		Page<FormaPagamento>page = this.repository.findAll(pageable);
		modelAndView.addObject("page", page);
		return modelAndView;
		
	}
	
}
