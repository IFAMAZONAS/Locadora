package br.com.cin.locadora.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class ControladorPrincipal {
	
	@RequestMapping("/")
	public String index(){
		return "home";
	}
	
	@RequestMapping("**/login")
	public String logar() {
		return "login";
	}
	
	@RequestMapping("**/logout")
	public String logout() {
		return "logout";
	}
	
	
}
