package software.house.springyugi.SpringYugi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
public class DeckController {

	@RequestMapping("/cadastrarDeck")
	public String cadastrar() {
		return "deck/formDeck";
	}

	@RequestMapping("/todosDeck")
	public String listar() {
		return "deck/listarDeck";
	}
}
