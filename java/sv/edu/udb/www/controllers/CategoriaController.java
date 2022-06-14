package sv.edu.udb.www.controllers;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sv.edu.udb.www.models.entity.Categoria;
import sv.edu.udb.www.models.service.ICategoriaService;
import sv.edu.udb.www.utils.paginador.Paginador;


import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;

@Controller
@SessionAttributes("categoria")
public class CategoriaController {

	@Autowired
	private ICategoriaService categoriaService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(value = {"/listarCategoria"})
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Locale locale) {
		 
		Pageable pagina = PageRequest.of(page, 5);
		Page<Categoria> categoria = categoriaService.listar(pagina);

		Paginador<Categoria> paginador = new Paginador<>("/listarCategoria", categoria);
		model.addAttribute("titulo", messageSource.getMessage("text.categoria.titulo", null, locale));
       	model.addAttribute("categorias", categoria);
		model.addAttribute("page", paginador);
		return "Categoria/listar";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/ingresarCategoria")
	public String crear(Model model, Locale locale) {
		Categoria categoria = new Categoria();
		model.addAttribute("categoria",categoria);
		model.addAttribute("titulo", messageSource.getMessage("text.categoria.ingresar.titulo", null, locale));
		model.addAttribute("boton", messageSource.getMessage("text.categoria.ingresar.botoncrear", null, locale));
		return "Categoria/ingresar";
	}
	
	
	
	@Secured("ROLE_ADMIN")
	@PostMapping(value="/ingresarCategoria")
	public String guardar(@Valid Categoria categoria, BindingResult resultado, Model model,
			 SessionStatus status, RedirectAttributes mensaje, Locale locale) {

		if (resultado.hasErrors()) {
			model.addAttribute("categoria", categoria);
			model.addAttribute("titulo", messageSource.getMessage("text.categoria.ingresar.titulo", null, locale));
			model.addAttribute("boton", messageSource.getMessage("text.categoria.ingresar.botoncrear", null, locale));
			return "Categoria/ingresar";
		} else {
		 
			String update = (categoria.getId()!=null) ? messageSource.getMessage("text.categoria.validacion.modificado", null, locale)
					: messageSource.getMessage("text.categoria.validacion.agregado", null, locale);
			categoriaService.insertar(categoria);
			status.setComplete();
			mensaje.addFlashAttribute("exito", update);
			return "redirect:/listarCategoria";

		}

	}
	
	@GetMapping(value = "/ingresarCategoria/{id}")
	public String modificar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes mensaje, Locale locale) {

		Categoria categoria = null;
		if (id > 0) {
			categoria = categoriaService.obtener(id);
			if (categoria == null) {
				mensaje.addFlashAttribute("error", messageSource.getMessage("text.categoria.validacion.productonoencontrado", null, locale));
				return "redirect:/listarCategoria";
			}

		} else {
			mensaje.addFlashAttribute("error", messageSource.getMessage("text.categoria.validacion.productoid", null, locale));
			return "redirect:/listarCategoria";

		}
		model.addAttribute("categoria", categoria);
		model.addAttribute("titulo", messageSource.getMessage("text.categoria.editar.titulo", null, locale));
		model.addAttribute("boton", messageSource.getMessage("text.categoria.editar.boton.modificarcategoria", null, locale));
		return "Categoria/ingresar";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/listarCategoria/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes mensaje, Locale locale) {

		if (id > 0) { 
			try {
				
					categoriaService.eliminar(id); 
				mensaje.addFlashAttribute("exito", messageSource.getMessage("text.categoria.validacion.alertaeliminar", null, locale)); 
				
			}
			catch (DataAccessException e) {
				mensaje.addFlashAttribute("error", "No se puede eliminar esta Categoria, Por que existen Productos relacionados ");
			}	
		
		} 
		return "redirect:/listarCategoria";

	}
	
}
