package software.house.springyugi.SpringYugi.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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
	public String formUsuario(Usuario usuario,HttpServletRequest request) {
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
    	        ur.save(usuario);  
    		}
        	return "Usuario/sucessForm";
        } catch (Exception ex) {
        	return "Usuario/erroForm";
        }  
	}
	
	@RequestMapping("Usuario/listarUsuarios")
	public ModelAndView listarUsuarios() {
		ModelAndView mv = new ModelAndView("Usuario/listarUsuarios");
		Iterable<Usuario> usuarios = ur.findAll();
		mv.addObject("usuarios",usuarios);
		return mv;
	}
}
