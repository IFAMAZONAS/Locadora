package br.com.cin.locadora.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class ControladorPrincipal {
	
	@RequestMapping("/")
	public String index(){
		return "index";
	}
	
	@RequestMapping("login")
	public String logar() {
		return "login";
	}
	
	@RequestMapping("home")
	public String home() {
		return "home";
	}
}
