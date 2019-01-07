package br.com.cin.locadora.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cin.locadora.controlador.util.Navegacao;
import br.com.cin.locadora.model.Cliente;

@Controller
@RequestMapping(value="filme")
public class FilmeController {
	
	List<String> msg = new ArrayList<>();

	@RequestMapping(method = RequestMethod.GET, value = "**/cadastrofilme")
	@ResponseBody
	public ModelAndView form() {
		ModelAndView andView = new ModelAndView(Navegacao.CADASTRAR_FILME);
		andView.addObject("msg", this.msg);
		return andView;
	}
}
