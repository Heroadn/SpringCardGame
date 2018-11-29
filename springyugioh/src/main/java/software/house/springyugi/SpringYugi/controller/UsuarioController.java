package software.house.springyugi.SpringYugi.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import software.house.springyugi.SpringYugi.models.Usuario;
import software.house.springyugi.SpringYugi.repository.UsuarioRepository;

@Controller
public class UsuarioController {
    		 
	@Autowired
	private UsuarioRepository ur;

	@RequestMapping(value="Usuario/CadastrarUsuario" , method = RequestMethod.GET)
	public String formUsuario() {
		return "Usuario/formUsuario";
	}
	
	@RequestMapping(value="Usuario/CadastrarUsuario" , method = RequestMethod.POST)
	public ModelAndView formUsuario(Usuario usuario,HttpServletRequest request) {
		//Model And View
        ModelAndView mv = new ModelAndView();
        String msg = "";
        
        //Requisitando imagem mandada pelo usuario
        MultipartHttpServletRequest multiPartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multiPartRequest.getFile("file");

        //Upload da imagem do usuario
        try {
        	if(!file.isEmpty()) {
    			BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
    			File destination = new File("src/main/resources/Usuario/imagem/"+file.getName());
    			ImageIO.write(src,"png",destination);
    		}else {msg = "Erro: Imagem vazia";}
        } catch (Exception ex) {
        	msg = "Erro:"+ex;
        }
        
        if(msg == "") {
        	//Salvando o usuario
            usuario.setImagem("IMAGE");
            usuario.setSalt("123");	
            usuario.setPontos(0);
            ur.save(usuario);
            mv.setViewName("Usuario/sucessForm");
        }else {
        	mv.setViewName("Usuario/erroForm");
        	mv.addObject(msg);
        }
        
        return mv;
	}
	
	@RequestMapping("Usuario/listarUsuarios")
	public ModelAndView listarUsuarios() {
		ModelAndView mv = new ModelAndView("Usuario/listarUsuarios");
		Iterable<Usuario> usuarios = ur.findAll();
		mv.addObject("usuarios",usuarios);
		return mv;
	}
}
