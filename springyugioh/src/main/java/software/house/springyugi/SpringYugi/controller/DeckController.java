package software.house.springyugi.SpringYugi.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import software.house.springyugi.SpringYugi.models.Deck;
import software.house.springyugi.SpringYugi.models.Usuario;
import software.house.springyugi.SpringYugi.repository.DeckRepository;
import software.house.springyugi.SpringYugi.repository.UsuarioRepository;

@Controller 
public class DeckController {
	
	@Autowired
	private DeckRepository dr;
	
	@Autowired
	private UsuarioRepository ur;
	
	private static final String RES = "src/main/resources/static/images/";

	@RequestMapping(value = "Deck/")
	public String containerDeCartas(HttpSession session) {
		//Requisitando dados do usuario no banco de dados
		Usuario usuario = ur.findByEmail((String)session.getAttribute("email"));
		
		if(usuario.getDecks().isEmpty() && session.getAttribute("email") != null) {
			//Adicionado id do usario ao deck
			Deck deck = new Deck();
			deck.setNome("Container");
			deck.setDescricao("Container de Cartas");
			deck.setImagem("../images/default.png");
			deck.setUsuario(usuario);
			dr.save(deck);
			
			//Atualizando Usuario
			usuario.addDeck(deck);
			ur.save(usuario);
		}
		return "redirect:../Deck/Cadastrar";
	}
	
	@RequestMapping(value = "Deck/Cadastrar", method = RequestMethod.GET)
	public String cadastrar(HttpSession session) {
		if(session.getAttribute("email") != null) {
			Usuario usuario = ur.findByEmail((String)session.getAttribute("email"));
			
			if(!usuario.isDeckEmpty()) {
				return "Deck/cadastrar";
			}else{
				return "redirect:../Deck/";
			}
		}else {
			return "redirect:../Login/Entrar";
		}
	}
	
	@RequestMapping(value = "Deck/Cadastrar" , method = RequestMethod.POST)
	public String cadastrar(Deck deck,HttpServletRequest request,HttpSession session) throws IOException {
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
		
        /*Verificando se o usuario está logado*/
        if(session.getAttribute("email") != null) {
        	Usuario usuario = ur.findByEmail((String)session.getAttribute("email"));
        	
			if(!file.isEmpty()) {//Se a imagem mandada nao estiver vazia
				BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
				ImageIO.write(src,"png", new File("RES"+deck.getNome()+usuario.getNome()+".png"));
				
				//Adicionando Deck ao BD e as imagens ao modelo
				deck.setImagem("../images/"+deck.getNome()+usuario.getNome()+".png");
				deck.setUsuario(usuario);
				dr.save(deck);
				
				//Adicionando Deck ao usuario
				usuario.addDeck(deck);
				ur.save(usuario);
				return "Deck/Cadastrar";
			}else {
				return "redirect:../Deck/Cadastrar?erro=1";
			}
		}else {
			return "redirect:../Login/Entrar";
		}
	}

	@RequestMapping("Deck/Listar")
	public ModelAndView listar(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		if(session.getAttribute("email") != null) {
			mv.setViewName("Deck/listarDecks");
			
			Usuario fromDb = ur.findByEmail((String)session.getAttribute("email"));
			Iterable<Deck> decks = fromDb.getDecks();
			mv.addObject("decks",decks);
		}else {
			mv.setViewName("redirect:../Deck/Cadastrar");
		}
		return mv;
	}
	
	@RequestMapping("Deck/SelecionarDeck")
	public ModelAndView selecionarDeck(HttpSession session) {
		ModelAndView mv = new ModelAndView();
			Iterable<Deck> decks = dr.findAll();
			mv.addObject("decks",decks);
			mv.setViewName("Deck/selecionarDeck");
		return mv;
	}
}
