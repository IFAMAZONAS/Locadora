package br.com.cin.locadora.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("filme")
public class FilmeController {
		
	@RequestMapping("/")
	public String index() {
		return "filme/index";
	}
}
