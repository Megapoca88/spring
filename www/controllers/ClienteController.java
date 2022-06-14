package sv.edu.udb.www.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.HttpHeaders;
import sv.edu.udb.www.models.entity.Cliente;
import sv.edu.udb.www.models.service.ICargaArchivoService;
import sv.edu.udb.www.models.service.IClienteService;
import sv.edu.udb.www.utils.paginador.Paginador;

import org.springframework.context.MessageSource;

@Controller
 
@SessionAttributes("cliente")
public class ClienteController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private ICargaArchivoService cargarService;
	
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = {"/listar","/"}, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Authentication usuario, HttpServletRequest request,
			Locale locale) {
		
		
		
		Pageable pagina = PageRequest.of(page, 5);
		Page<Cliente> cliente = clienteService.listar(pagina);

		Paginador<Cliente> paginador = new Paginador<>("/listar", cliente);
		model.addAttribute("titulo", messageSource.getMessage("text.cliente.titulo", null, locale));
		model.addAttribute("clientes", cliente);
		model.addAttribute("page", paginador);
		return "Clientes/listar";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/ingresar")
	public String crear(Model model, Locale locale) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", messageSource.getMessage("text.cliente.ingresar.titulo", null, locale));
		model.addAttribute("boton", messageSource.getMessage("text.cliente.ingresar.boton", null, locale));
		return "Clientes/ingresar";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/ingresar", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult resultado, Model model,
			@RequestParam("file") MultipartFile foto, SessionStatus status, RedirectAttributes mensaje, Locale locale) {

		if (resultado.hasErrors()) {
			model.addAttribute("cliente", cliente);
			model.addAttribute("titulo", messageSource.getMessage("text.cliente.ingresar.titulo", null, locale));
			model.addAttribute("boton",  messageSource.getMessage("text.cliente.ingresar.boton", null, locale));
			return "Clientes/ingresar";
		} else {
			// elimina la foto
			if (!foto.isEmpty()) {
				if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null
						&& cliente.getFoto().length() > 0) {
					cargarService.eliminar(cliente.getFoto());

				}

				String nombreUnico=null;
				try {
					  nombreUnico = cargarService.nombreUnico(foto);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cliente.setFoto(nombreUnico);
			}

			String update = (cliente.getId() != null) ? messageSource.getMessage("text.cliente.validacion.modificado", null, locale)
					:  messageSource.getMessage("text.cliente.validacion.agregado", null, locale);
			clienteService.insertar(cliente);
			status.setComplete();
			mensaje.addFlashAttribute("exito", update);
			return "redirect:/listar";

		}

	}

	@RequestMapping(value = "/ingresar/{id}")
	public String modificar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes mensaje, Locale locale) {

		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteService.obtener(id);
			if (cliente == null) {
				mensaje.addFlashAttribute("error", messageSource.getMessage("text.cliente.validacion.cliente.noencontrado", null, locale));
				return "redirect:/listar";
			}

		} else {
			mensaje.addFlashAttribute("error", messageSource.getMessage("text.cliente.validacion.clienteid", null, locale));
			return "redirect:/cliente/listar";

		}
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", messageSource.getMessage("text.cliente.editar.titulo", null, locale));
		model.addAttribute("boton", "Modificar cliente");
		return "Clientes/ingresar";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/listar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes mensaje, Locale locale) {

		if (id > 0) {
			Cliente cliente = clienteService.obtener(id);
			clienteService.eliminar(id);

			if (cargarService.eliminar(cliente.getFoto())) {
				mensaje.addFlashAttribute("exito", messageSource.getMessage("text.cliente.validacion.Eliminado", null, locale));
			}

		}

		return "redirect:/listar";

	}
// carga la lista detalles
	@Secured({"ROLE_USER","ROLE_ADMIN"}) 
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes mensaje, Locale locale) {
		Cliente cliente = clienteService.clienteFactura(id);
		if (cliente == null) {
			mensaje.addFlashAttribute("error", messageSource.getMessage("text.cliente.validacion.cliente.noencontrado", null, locale));
			return "redirect:/cliente/listar";
		} else {
			model.addAttribute("cliente", cliente);
			model.addAttribute("titulo", messageSource.getMessage("text.cliente.detalle.titulo", null, locale));
			
			return "Clientes/ver";
		}
	}

// metodo que carga la img
	@Secured({"ROLE_USER","ROLE_ADMIN"}) 
	@GetMapping(value = "/perfil/{archivo:.+}")
	public ResponseEntity<Resource> cargarImagen(@PathVariable String archivo) {
		Resource recurso = null;
		try {
			recurso = cargarService.cargar(archivo);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;archivo=\"" + recurso.getFilename() + "\"")
				.body(recurso);

	}
	
	 

}