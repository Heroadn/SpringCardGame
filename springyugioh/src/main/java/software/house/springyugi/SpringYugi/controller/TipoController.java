package software.house.springyugi.SpringYugi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import software.house.springyugi.SpringYugi.models.Tipo;
import software.house.springyugi.SpringYugi.repository.TipoRespository;

@Controller
public class TipoController {
	
	@Autowired
	private TipoRespository tr;
	
	@RequestMapping(value = "/Tipo/CadastrarTipo", method = RequestMethod.GET)
	public String form() {
		return "Tipo/formTipo";
	}
	
	@RequestMapping(value = "/Tipo/CadastrarTipo" ,method = RequestMethod.POST)
	public String form(Tipo tipo) {
		tr.save(tipo);
		return "Tipo/sucessForm";
	}
}
