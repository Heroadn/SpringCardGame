package software.house.springyugi.SpringYugi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
public class CartaController {

	@RequestMapping("/cadastrarCarta")
	public String cadastrar() {
		return "carta/formCarta";
	}

	@RequestMapping("/todasCarta")
	public String listar() {
		return "carta/formCarta";
	}
}
