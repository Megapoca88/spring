package sv.edu.udb.www.controllers;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
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
import sv.edu.udb.www.models.entity.Producto;
import sv.edu.udb.www.models.entity.Proveedor;
import sv.edu.udb.www.models.service.ICategoriaService;
import sv.edu.udb.www.models.service.IProductoService;
import sv.edu.udb.www.models.service.IProveedorService;
import sv.edu.udb.www.utils.paginador.Paginador;



@Controller
@SessionAttributes("producto")
public class ProductoController {

	@Autowired
	private IProductoService productoService;
	@Autowired
	private ICategoriaService categoriaService;
	@Autowired
	private IProveedorService proveedorService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping(value = { "/listarProducto" })
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Locale locale) {

		Pageable pagina = PageRequest.of(page, 5);
		Page<Producto> Producto = productoService.listar(pagina);

		Paginador<Producto> paginador = new Paginador<>("/listarProducto", Producto);
		model.addAttribute("titulo", messageSource.getMessage("text.producto.titulo", null, locale));
		model.addAttribute("productos", Producto);
		model.addAttribute("page", paginador);
		return "Producto/listar";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/ingresarProducto")
	public String crear(Model model, Locale locale) {
		Producto producto = new Producto();
		List<Categoria> categoria = categoriaService.listar();
		List<Proveedor> proveedor = proveedorService.findAll();

		model.addAttribute("proveedor", proveedor);
		model.addAttribute("categoria", categoria);
		model.addAttribute("producto", producto);
		model.addAttribute("titulo", messageSource.getMessage("text.producto.ingresar.titulo", null, locale));
		model.addAttribute("boton", messageSource.getMessage("text.producto.ingresar.botoncrear", null, locale));
		return "Producto/ingresar";
	}

	@Secured("ROLE_ADMIN")
	@PostMapping(value = "/ingresarProducto")
	public String guardar(@Valid Producto producto, BindingResult resultado,
			@RequestParam(name = "cat[]", required = false) Long[] cat,
			@RequestParam(name = "pro[]", required = false) Long[] pro, Model model, SessionStatus status,
			RedirectAttributes mensaje, Locale locale) {

		if (resultado.hasErrors()) {
			List<Categoria> categoria = categoriaService.listar();
			List<Proveedor> proveedor = proveedorService.findAll();

			model.addAttribute("proveedor", proveedor);
			model.addAttribute("categoria", categoria);
			model.addAttribute("producto", producto);
			model.addAttribute("titulo", messageSource.getMessage("text.producto.ingresar.titulo", null, locale));
			model.addAttribute("boton", messageSource.getMessage("text.producto.ingresar.botoncrear", null, locale));
			return "Producto/ingresar";
		} else {

			String update = (producto.getId() != null) ?  messageSource.getMessage("text.producto.validaciones.modificado", null, locale)
					: messageSource.getMessage("text.producto.validaciones.agregado", null, locale);

			for (int i = 0; i < cat.length; i++) {
				Categoria categoria = productoService.buscar(cat[i]);

				producto.setCategoria(categoria);
			}
			for (int i = 0; i < pro.length; i++) {
				Proveedor proveedor = productoService.buscarP(pro[i]);
				producto.setProveedor(proveedor);
			}

			productoService.insertar(producto);
			status.setComplete();
			mensaje.addFlashAttribute("exito", update);
			return "redirect:/listarProducto";

		}

	}

	@GetMapping(value = "/ingresarProducto/{id}")
	public String modificar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes mensaje, Locale locale) {

		Producto producto = null;
		if (id > 0) {
			producto = productoService.obtener(id);
			if (producto == null) {
				mensaje.addFlashAttribute("error", messageSource.getMessage("text.producto.validacion.productonoencontrado", null, locale));
				return "redirect:/listarProducto";
			}

		} else {
			mensaje.addFlashAttribute("error",  messageSource.getMessage("text.producto.validacion.productoid", null, locale));
			return "redirect:/listarProducto";

		}
		List<Categoria> categoria = categoriaService.listar();
		List<Proveedor> proveedor = proveedorService.findAll();

		model.addAttribute("proveedor", proveedor);
		model.addAttribute("categoria", categoria);
		model.addAttribute("producto", producto);
		model.addAttribute("titulo", messageSource.getMessage("text.producto.editar.titulo", null, locale));
		model.addAttribute("boton", messageSource.getMessage("text.producto.editar.boton.modificar", null, locale));
		return "Producto/ingresar";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/listarProducto/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes mensaje, Locale locale) {

		if (id > 0) { 
			try {
				
			productoService.eliminar(id);
			mensaje.addFlashAttribute("exito", messageSource.getMessage("text.producto.validacion.alertaeliminar", null, locale));
			}
			catch (DataAccessException e) {
				mensaje.addFlashAttribute("error", "No se puede eliminar este producto, Por que existen clientes facturados con este Producto");
			}	
		
		} 
		return "redirect:/listarProducto";

	}

}
