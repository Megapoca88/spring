package sv.edu.udb.www.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sv.edu.udb.www.models.entity.Proveedor;
import sv.edu.udb.www.models.service.IProveedorService;
import sv.edu.udb.www.utils.paginador.Paginador;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;

@Controller
@SessionAttributes("proveedor")
public class ProveedorController {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IProveedorService proveedorService;
	
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/listarProveedor")
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Authentication usuario,
			HttpServletRequest request, Locale locale) {

		Pageable pagina = PageRequest.of(page, 5);
		Page<Proveedor> proveedor = proveedorService.findAll(pagina);

		Paginador<Proveedor> paginador = new Paginador<>("/listarProveedor", proveedor);

		model.addAttribute("titulo", messageSource.getMessage("text.proveedor.titulo", null, locale));
		model.addAttribute("proveedores", proveedor);
		model.addAttribute("page", paginador);

		return "proveedor/listar";

	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/ingresarProveedor")
	public String crear(Model model, Locale locale) {

		Proveedor proveedor = new Proveedor();

		model.addAttribute("proveedor", proveedor);
		model.addAttribute("titulo", messageSource.getMessage("text.proveedor.ingresar.titulo", null, locale));
		model.addAttribute("boton", messageSource.getMessage("text.proveedor.ingresar.botoncrear", null, locale));

		return "proveedor/ingresar";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/ingresarProveedor", method = RequestMethod.POST)
	public String guardar(@Valid Proveedor proveedor, BindingResult result, Model model, SessionStatus status,
			RedirectAttributes mensaje, Locale locale) {

		if (result.hasErrors()) {
			model.addAttribute("proveedor", proveedor);
			model.addAttribute("titulo", messageSource.getMessage("text.proveedor.ingresar.titulo", null, locale));
			model.addAttribute("boton", messageSource.getMessage("text.proveedor.ingresar.botoncrear", null, locale));
			return "proveedor/ingresar";
		}

		String update = (proveedor.getId() != null) ? messageSource.getMessage("text.proveedor.validaciones.modificado", null, locale)
				: messageSource.getMessage("text.proveedor.validaciones.agregado", null, locale);

		proveedorService.guardar(proveedor);

		status.setComplete();
		mensaje.addFlashAttribute("exito", update);
		return "redirect:listarProveedor";

	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/ingresarProveedor/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes mensaje, Locale locale) {

		Proveedor proveedor = null;

		if (id > 0) {

			proveedor = proveedorService.obtener(id);
			if (proveedor == null) {
				mensaje.addFlashAttribute("error", messageSource.getMessage("text.proveedor.validaciones.proveedornoencontrado", null, locale));
				return "redirect:/listarProveedor";
			}

		} else {
			mensaje.addFlashAttribute("error", messageSource.getMessage("text.proveedor.validaciones.proveedorid", null, locale));
			return "redirect:/listarProveedor";

		}

		model.addAttribute("proveedor", proveedor);
		model.addAttribute("titulo", messageSource.getMessage("text.proveedor.editar.titulo", null, locale));
		model.addAttribute("boton", messageSource.getMessage("text.proveedor.editar.boton.modificarproveedor", null, locale));

		return "proveedor/ingresar";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/listarProveedor/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes mensaje, Locale locale) {

		if (id > 0) { 
			try {
				proveedorService.eliminar(id);
			mensaje.addFlashAttribute("exito", messageSource.getMessage("text.proveedor.validaciones.alertaeliminar", null, locale));
			}
			catch (DataAccessException e) {
				mensaje.addFlashAttribute("error", "No se puede eliminar este Provedor, Por que existen productos facturados en la base");
			}	
		
		} 	
		return "redirect:/listarProveedor";
	}

}
