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
import br.com.cin.locadora.model.TipoMidia;
import br.com.cin.locadora.model.repository.TipoMidiaRepository;
import br.com.cin.locadora.servico.TipoMidiaService;

@Controller
public class TipoMidiaController {

	@Autowired
	TipoMidiaService tipoMidiaService;
	
	@Autowired
	TipoMidiaRepository midiaRepository;
	
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
	
	@RequestMapping(method = RequestMethod.GET, value = "**/cadastrotipomidia")
	@ResponseBody
	public ModelAndView form(@PageableDefault(size = 5) Pageable pageable) {
		ModelAndView andView = new ModelAndView(Navegacao.CADASTRAR_TIPO_MIDIA);
		Page<TipoMidia> page = midiaRepository.findAll(pageable);	
		Iterable<TipoMidia> tipoMidias = this.midiaRepository.findAll();
		andView.addObject("tipoMidias", new ArrayList<>());
		andView.addObject("tipoMidia", new TipoMidia());
		andView.addObject("msg", this.msg);
		andView.addObject("messagensErro", this.messagensErro);
		andView.addObject("page", page);
		this.messagensErro = new ArrayList<>();
		this.msg = new ArrayList<>();
		return andView;
	}
	
	@RequestMapping(value = "**/salvartipomidia", method=RequestMethod.POST)
	public ModelAndView salvar(TipoMidia tipoMidia, @PageableDefault(size = 5) Pageable pageable) {
		ModelAndView modelAndView = new ModelAndView(Navegacao.CADASTRAR_TIPO_MIDIA);
		Page<TipoMidia> page; 
		if (this.validadeTipoMidia(tipoMidia.getDescricao(), tipoMidia.getValor())) {
			this.msg = new ArrayList<String>();
			this.msg.add("Operação realizada com sucesso!");
			modelAndView.addObject("msg", this.msg);
			this.msg = new ArrayList<>();
			this.tipoMidiaService.salvarTipoMidia(tipoMidia);
			page = midiaRepository.findAll(pageable);
	        modelAndView.addObject("page", page);
	        modelAndView.addObject("tipoMidia", new TipoMidia());
			return modelAndView;
		} else {
			modelAndView.addObject("messagensErro", messagensErro);
			page = midiaRepository.findAll(pageable);
			modelAndView.addObject("page", page);
			return modelAndView;
			
		}
	
	}
	
	private boolean validadeTipoMidia(String descricao, Double valor) {
		boolean retorno = true;
		this.messagensErro = new ArrayList<>();
		
		if(descricao.isEmpty()) {
			this.messagensErro.add("Campo Descrição deve ser informado!");
		}
		
		if(valor==null) {
			this.messagensErro.add("Campo Valor deve ser informado!");
		}
	
		if(!this.messagensErro.isEmpty()) {
			retorno = false;
		}
		return retorno;
	}	
	
	@GetMapping("**/editartipomidia/{idTipoMidia}")
	public ModelAndView editar(@PathVariable("idTipoMidia") Integer idTipoMidia, @PageableDefault(size = 5) Pageable pageable) {
		
		ModelAndView modelAndView = new ModelAndView(Navegacao.CADASTRAR_TIPO_MIDIA);
		/*modelAndView.addObject("fornecedor", fornecedor.get());*/
		modelAndView.addObject("tipoMidia", this.midiaRepository.findById(idTipoMidia));
		Page<TipoMidia>page = this.midiaRepository.findAll(pageable);
		modelAndView.addObject("page", page);
		return modelAndView;
		
	}	
}
