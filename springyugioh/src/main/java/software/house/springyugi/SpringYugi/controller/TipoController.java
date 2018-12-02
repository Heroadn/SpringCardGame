package software.house.springyugi.SpringYugi.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import software.house.springyugi.SpringYugi.models.Tipo;
import software.house.springyugi.SpringYugi.repository.TipoRespository;

@Controller
public class TipoController {
	
	@Autowired
	private TipoRespository tr;
	
	@RequestMapping(value = "/Tipo/Cadastrar", method = RequestMethod.GET)
	public String form() {
		return "Tipo/formTipo";
	}
	
	@RequestMapping(value = "/Tipo/Cadastrar" ,method = RequestMethod.POST)
	public String form(Tipo tipo,HttpServletRequest request) {
		//Requisitando imagem mandada pelo usuario
        MultipartHttpServletRequest multiPartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multiPartRequest.getFile("file");
        //Upload da imagem do usuario
        try {
        	if(!file.isEmpty()) {
        		//Salvando a imagem
    			BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
    			File destination =
    					new File("src/main/resources/static/images/"+tipo.getNome()+tipo.getId()+".png");
    			ImageIO.write(src,"png",destination);
    			
    			//Salvando o tipo
    			tipo.setImagem("../images/"+tipo.getNome()+tipo.getId()+".png");
    			tr.save(tipo); 
    		}
        	return "Tipo/sucessForm";
        }catch(Exception e) {
        	return "Tipo/erroForm";
        }
	}
	
	@RequestMapping("Tipo/Listar")
	public ModelAndView listarUsuarios() {
		ModelAndView mv = new ModelAndView("Tipo/listarTipos");
		Iterable<Tipo> tipos = tr.findAll();
		mv.addObject("tipos",tipos);
		return mv;
	}
}
