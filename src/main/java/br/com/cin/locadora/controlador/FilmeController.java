package br.com.cin.locadora.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cin.locadora.controlador.util.Navegacao;
import br.com.cin.locadora.model.Cliente;
import br.com.cin.locadora.model.TipoMidia;
import br.com.cin.locadora.servico.TipoMidiaService;

@Controller
@RequestMapping(value="filme")
public class FilmeController {
	@Autowired
	TipoMidiaService tipoMidiaService;
	
	Iterable<TipoMidia> tipoMidiaLista;
	List<String> msg = new ArrayList<>();
	

	@RequestMapping(method = RequestMethod.GET, value = "**/cadastrofilme")
	@ResponseBody
	public ModelAndView form() {
		ModelAndView andView = new ModelAndView(Navegacao.CADASTRAR_FILME);
		this.tipoMidiaLista = this.tipoMidiaService.listarTodos();
		andView.addObject("msg", this.msg);
		andView.addObject("tipoMidia", this.tipoMidiaLista);
		return andView;
	}
}
