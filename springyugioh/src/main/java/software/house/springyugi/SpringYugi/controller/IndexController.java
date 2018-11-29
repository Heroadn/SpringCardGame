package software.house.springyugi.SpringYugi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	@RequestMapping("/")
	public String index(HttpSession session) {
		return "Index/index";
	}
}
