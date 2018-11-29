package software.house.springyugi.SpringYugi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DebugController {
	
	@RequestMapping(value = "/debug")
	public String debug(Model model,HttpSession session) {
		model.addAttribute("sessionID", session.getId());
		return "Debug/debug";
	}
}
