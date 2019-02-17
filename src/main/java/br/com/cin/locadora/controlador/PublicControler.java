package br.com.cin.locadora.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.cin.locadora.controlador.util.Navegacao;
import br.com.cin.locadora.model.repository.FilmeRepository;

@Controller
@RequestMapping(value = "public")
public class PublicControler {
		@Autowired
		FilmeRepository  repository;


		@GetMapping(value="**/entrarConsulta")
		public ModelAndView entrarConsultaPublica() {
			ModelAndView andView = new ModelAndView(Navegacao.CONSULTA_PUBLICA);
			andView.addObject("filmes",repository.findAll());
			
			return andView;
			
		}
}
