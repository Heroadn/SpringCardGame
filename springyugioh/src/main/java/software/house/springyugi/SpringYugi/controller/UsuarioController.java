package software.house.springyugi.SpringYugi.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import software.house.springyugi.SpringYugi.models.Deck;
import software.house.springyugi.SpringYugi.models.Usuario;
import software.house.springyugi.SpringYugi.repository.UsuarioRepository;

@Controller
public class UsuarioController {
    		 
	@Autowired
	private UsuarioRepository ur;

	@RequestMapping(value="Usuario/Cadastrar" , method = RequestMethod.GET)
	public String formUsuario() {
		return "Usuario/cadastrar";
	}
	
	@RequestMapping(value="Usuario/Cadastrar", method = RequestMethod.POST)
	public ModelAndView formUsuario(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request,HttpSession session) {
		//ModelAndView
		ModelAndView mv = new ModelAndView();
		if(!ur.existsByEmail(usuario.getEmail())) {
	        //Requisitando imagem mandada pelo usuario
	        MultipartHttpServletRequest multiPartRequest = (MultipartHttpServletRequest) request;
	        MultipartFile file = multiPartRequest.getFile("file");
	        
	        //Upload da imagem do usuario
	        try {
	        	if(!file.isEmpty()) {
	        		//Salvando a imagem
	    			BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
	    			File destination =
	    					new File("src/main/resources/static/images/"+usuario.getNome()+usuario.getId()+".png");
	    			ImageIO.write(src,"png",destination);
	    			
	    			//Salvando o usuario
	    	        usuario.setImagem("../images/"+usuario.getNome()+usuario.getId()+".png");
	    	        usuario.setSalt("123");	
	    	        usuario.setPontos(0);
	    	        usuario.setDecks(new ArrayList<Deck>());
	    	        ur.save(usuario);
	    	        
	    	        //Criando sessao do Usuario
	    	        session.setAttribute("username", usuario.getNome());
	    	        session.setAttribute("email", usuario.getEmail());
	    	        
	    	        //Mandando Request para o DeckController
	    	        mv.addObject("usr",usuario);
	    	        mv.setViewName("redirect:../Deck/Cadastrar");
	    	        return mv;
	    		}else {
	    			mv.setViewName("Usuario/erroForm");
	    			return mv;
	    		}
	        } catch (Exception ex) {
	        	mv.setViewName("Usuario/erroForm");
	        	return mv;
	        }     
	   }else {
		   mv.setViewName("Usuario/erroForm");
       	   return mv;
	   } 
	}
	
	@RequestMapping("Usuario/Listar")
	public ModelAndView listarUsuarios() {
		ModelAndView mv = new ModelAndView("Usuario/listarUsuarios");
		Iterable<Usuario> usuarios = ur.findAll();
		mv.addObject("usuarios",usuarios);
		return mv;
	}
	
	@RequestMapping("Usuario/MeusDecks")
	public ModelAndView meusDecks(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		if(session.getAttribute("email") != null) {
			Usuario usuario = ur.findByEmail((String)session.getAttribute("email"));
			
			//Se o usuario tiver pelo menos um Deck
			if(!usuario.getDecks().isEmpty()) {
				Iterable<Deck> decks = usuario.getDecks();
				mv.addObject("decks",decks);
				mv.setViewName("Usuario/meusDecks");
			}else {
				mv.setViewName("redirect:../Deck/Cadastrar");
			}
		}else {
			mv.setViewName("redirect:/Login/Entrar");
		}
		return mv;
	}
}
