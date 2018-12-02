package software.house.springyugi.SpringYugi.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import software.house.springyugi.SpringYugi.models.Usuario;
import software.house.springyugi.SpringYugi.repository.UsuarioRepository;

@Controller
public class LoginController {

	@Autowired
	private UsuarioRepository ur;
	
	@RequestMapping(value = "Login/Cadastrar" , method = RequestMethod.GET)
	public String cadastrar() {
		return "redirect:../Usuario/Cadastrar";
	}
	
	@RequestMapping(value = "Login/Cadastrar" , method = RequestMethod.POST)
	public ModelAndView cadastrar(@ModelAttribute Usuario usuario, HttpServletRequest request) throws IOException { 
		ModelAndView mv = new ModelAndView();
		mv.addObject("usr",usuario);
		mv.setViewName("forward:../Usuario/CadastrarUsuario");
		return mv;
	}
	
	@RequestMapping(value = "Login/Entrar", method = RequestMethod.GET)
	public String login() {
		return "Login/Entrar";
	}
	
	@RequestMapping(value = "Login/Entrar", method = RequestMethod.POST)
	public String autorizar(Usuario usuario, HttpSession session) {
		if(ur.existsByEmail(usuario.getEmail())) {
			//Requisitando dados do usuario no banco de dados
			Usuario fromDb = ur.findByEmail(usuario.getEmail());
			
			//Verificando informaçoes
			if(fromDb.getSenha().equals(usuario.getSenha())) {
				session.setAttribute("id", fromDb.getId());
				session.setAttribute("username", fromDb.getNome());
				session.setAttribute("email", fromDb.getEmail());
				return "redirect:/";
			}else {
				return "redirect:/Login/Entrar?erro=1";
			}
			
		}else {
			return "redirect:/Login/Entrar?erro=1";
		}
	}
}
