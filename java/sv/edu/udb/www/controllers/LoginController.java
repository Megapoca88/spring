package sv.edu.udb.www.controllers;

import java.security.Principal;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;


@Controller
@RequestMapping("/sistema")
public class LoginController {
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping("/login")
	public String login(@RequestParam(name = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model, Principal usuario,
			RedirectAttributes mensaje, Locale locale) { 
		if (usuario != null) {
			mensaje.addFlashAttribute("exito", messageSource.getMessage("text.login.validacion.sesionyainiciada", null, locale));
			return "redirect:/listar/";
		}
		if (error != null) {
			model.addAttribute("error", messageSource.getMessage("text.login.validacion.datosincorrectos", null, locale));
		}

		if (logout != null) {
			model.addAttribute("exito", messageSource.getMessage("text.login.validacion.cerrarsesion", null, locale));
		}

		return "/login";
	}
	
	 

}
