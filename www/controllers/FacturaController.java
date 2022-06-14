package sv.edu.udb.www.controllers;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sv.edu.udb.www.models.entity.Cliente;
import sv.edu.udb.www.models.entity.DetalleFactura;
import sv.edu.udb.www.models.entity.Factura;
import sv.edu.udb.www.models.entity.Producto;
import sv.edu.udb.www.models.service.IClienteService;
import sv.edu.udb.www.models.service.IProductoService;
import org.springframework.context.MessageSource;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/factura")//la ruta de la url
@SessionAttributes("factura")
public class FacturaController {

	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("/ver/{id}")//aca se sigue con la url
	public String ver(@PathVariable(value="id") Long id, Model model, RedirectAttributes mensaje, Locale locale)
	{
		Factura factura = clienteService.unionTablas(id);     
		if (factura == null)
		{
			mensaje.addFlashAttribute("error", "La factura no existe en la base de datos");
			return "redirect:/listar";
		}
		model.addAttribute("factura", factura);
		model.addAttribute("titulo", messageSource.getMessage("text.factura.ver.titulo", null, locale).concat(" ").concat(factura.getDescripcion()));
		return "factura/ver";
	}
	 
	@GetMapping("/ingresar/{clienteId}")
	public String crear (@PathVariable(value="clienteId") long clienteId, Model model, RedirectAttributes mensaje, Locale locale)	{
		
		Cliente cliente = clienteService.obtener(clienteId);
		
		if(cliente == null) {
			mensaje.addFlashAttribute("error","El cliente no existe en la base de datos");
			return "redirect:/listar";
		}
		
		else {
			Factura factura = new Factura();
			factura.setCliente(cliente);
			model.addAttribute("factura",factura);
			model.addAttribute("titulo", messageSource.getMessage("text.factura.titulo", null, locale));
		}
		return "factura/ingresar";
	}
	
	@GetMapping(value="/cargar-productos/{term}",produces = {"application/json"})
	//@ResponseBody suprime el cargar una vista de thymeleaf y en vez de eso tomara el resultado de clienteService.buscarNombre(term);
	public @ResponseBody List<Producto> cargar (@PathVariable String term)
	{
		return clienteService.buscarNombre(term);
	}
	
	@PostMapping("/ingresar")
	public String guardar(@Valid Factura factura, BindingResult errores ,@RequestParam(name="item_id[]", required =false) Long[] id,
			@RequestParam(name="cantidad[]",required = false)Integer [] cantidad,
			RedirectAttributes mensaje,SessionStatus estado  , Model model)
	{
		Producto producto= new Producto();
		if(errores.hasErrors())
		{
			model.addAttribute("titulo","Crear Factura");
			return "factura/ingresar";
			
		}
		
		else if (id==null || id.length==0)
		{
			model.addAttribute("titulo","Crear Factura");
			model.addAttribute("error","La factura tiene que tener productos agregados");
			return "factura/ingresar";
		}
		
		 
		else {
		for (int i =0; i<id.length;i++)
		{
			 producto = clienteService.buscarId(id[i]);
			 int total = producto.getCantidad();
			
			 
			 
			DetalleFactura detalle = new DetalleFactura();
			detalle.setCantidad(cantidad[i]);
			detalle.setProducto(producto);
			factura.agregarFactura(detalle);
			
			
			 
			 if (total>cantidad[i]){
				 int to = total-cantidad[i];
				 producto.setCantidad(to);
				 productoService.insertar(producto);
			}
			
			else  if (total==cantidad[i]){
				 int to = total-cantidad[i];
				 producto.setCantidad(to);
				 productoService.insertar(producto);
			}
			 
			 else  if (total==0)
			{
				model.addAttribute("titulo","Crear Factura");
				model.addAttribute("error","Ya no existe productos en la base de datos");
				return "factura/ingresar";
			}
			

			 else  if (cantidad[i]<0){
				model.addAttribute("titulo","Crear Factura");
				model.addAttribute("error","No puede ingresar numeros negativos en la cantidad");
				estado.isComplete();
				return "factura/ingresar";
			} 
			 
			 else if (total<cantidad[i]){

				 	model.addAttribute("titulo","Crear Factura");
					model.addAttribute("error","No hay suficiente stock en nuestro inventario. Actualmente tenemos : " + total);
					return "redirect:/factura/ingresar/" + factura.getCliente().getId() ;
			}
			
			
		}
		
		
		 
		clienteService.guardarFatura(factura);
		estado.setComplete();
		mensaje.addFlashAttribute("exito","Factura creada con exito ");
		
		
		return "redirect:/ver/"+factura.getCliente().getId();
		}
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes mensaje)
	{
		Factura factura = clienteService.obtenerFacturaId(id);
		if (factura != null)
		{
			clienteService.eliminarFactura(id);
			mensaje.addFlashAttribute("exito","Factura eliminada con exito");
			return "redirect:/ver/"+factura.getCliente().getId();
		}
		mensaje.addFlashAttribute("error","La factura no exite en la base de datos");
		return "redirect:/listar";
	}
	
}