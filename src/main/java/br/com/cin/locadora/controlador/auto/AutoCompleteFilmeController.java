package br.com.cin.locadora.controlador.auto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cin.locadora.controlador.util.Navegacao;
import br.com.cin.locadora.model.Cliente;
import br.com.cin.locadora.model.Filme;
import br.com.cin.locadora.model.repository.ClienteRepository;
import br.com.cin.locadora.model.repository.FilmeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller

public class AutoCompleteFilmeController {

	@Autowired
	ClienteRepository repository;
	@Autowired
	FilmeRepository filmeRepository;
	
	List<Filme> lista = new ArrayList<>();
	List<Filme> listaAux = new ArrayList<>();

	// http://localhost:8080
	// basic site
	@GetMapping("/auto2")
	public ModelAndView autocomplete(Model model) {
		model.addAttribute("title", "autocomplete countries example");
		ModelAndView andView = new ModelAndView(Navegacao.INICIAR_LOCACAO);
		andView.addObject("filmes", new ArrayList<>());

		return andView;
	}
	
	@RequestMapping(value = "**/buscar2", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView buscar(@RequestParam("nome") String nome,Model model) {
		System.err.println("NOME :::::"+ nome);
		model.addAttribute("title", "autocomplete countries example");
		
		ModelAndView andView = new ModelAndView(Navegacao.INICIAR_LOCACAO);
		this.lista = this.filmeRepository.findFilmeByName(nome);
		this.listaAux.add(this.lista.get(0));
		
		andView.addObject("filmes",this.listaAux );

		return andView;
	}

// http://localhost:8080/suggestion?searchstr=car

	/**
	 * the rest controller which is requested by the autocomplete input field
	 * instead of the countries here could also be made a DB request
	 */
	@RequestMapping(value = "/suggestion2", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public SuggestionWrapper autocompleteSuggestions(@RequestParam("searchstr") String searchstr) {
		System.out.println("searchstr: " + searchstr);

		ArrayList<Country> suggestions = new ArrayList<>();

		Iterable<Filme> locales = this.filmeRepository.findAll();

		for (Filme countryCode : locales) {

			Locale obj = new Locale("", countryCode.getTituloPortugues());
			// add all countries to the arraylist
			// if on the query string
			if (obj.getDisplayCountry().toLowerCase().contains(searchstr.toLowerCase())) {
				suggestions.add(new Country(obj.getDisplayCountry()));
			}
		}

		// truncate the list to the first n, max 20 elements
		int n = suggestions.size() > 20 ? 20 : suggestions.size();
		List<Country> sulb = new ArrayList<>(suggestions.subList(0, n));

		SuggestionWrapper sw = new SuggestionWrapper();
		sw.setSuggestions(sulb);
		return sw;
	}

}