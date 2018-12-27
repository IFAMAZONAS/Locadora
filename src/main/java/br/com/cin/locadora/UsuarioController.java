package br.com.cin.locadora;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
		/***
		 * 
		 * @return
		 */
		public String index() {
			return"";
		}
}
