package br.com.cin.locadora.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.cin.locadora.controlador.util.Navegacao;
import br.com.cin.locadora.model.Filme;
import br.com.cin.locadora.model.repository.FilmeRepository;



@Controller
public class ControladorPrincipal {
	
	Page<Filme> page;
	@Autowired
	private FilmeRepository filmeRepository;
	
	@RequestMapping("/")
	public ModelAndView index(@PageableDefault(size = 5) Pageable pageable){
		ModelAndView andView = new ModelAndView("home");
		page = this.filmeRepository.findAll(pageable);
		andView.addObject("page", page);
		return andView;
	}
	
	@RequestMapping("home")
	public String home(){
		return "home";
	}
	
	@RequestMapping("/auto")
	public String autoComplete() {
		return "/cliente/autocomplete";
	}
	
	@RequestMapping("**/login")
	public String logar() {
		return "login";
	}
	
	@RequestMapping("**/logout")
	public String logout() {
		return "/";
	}
	
	@RequestMapping("**/inicio")
	public ModelAndView paginaIncial(@PageableDefault(size = 5) Pageable pageable) {
		
			ModelAndView andView = new ModelAndView("inicio");
			page = this.filmeRepository.findAll(pageable);
			andView.addObject("page", page);
			return andView;
		
		
	}
	
	@GetMapping("**/visualizar/{idFilme}")
	public ModelAndView visaoOublica(@PathVariable("idFilme") Integer idFilme, @PageableDefault(size = 5) Pageable pageable) {
		ModelAndView andView = new ModelAndView("/public");
		try {
			  Filme filme = this.filmeRepository.findById(idFilme).get();
			  
			  
			 andView.addObject("filme", filme);
		} catch (NumberFormatException e) {
			
		}
		return andView;
		
	}
	
}
