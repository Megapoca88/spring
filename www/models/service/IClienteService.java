package sv.edu.udb.www.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sv.edu.udb.www.models.entity.Cliente;
import sv.edu.udb.www.models.entity.Factura;
import sv.edu.udb.www.models.entity.Producto;

public interface IClienteService {

    public List<Cliente> listar();
	
	public void insertar(Cliente cliente);
	
	public Cliente obtener(Long id);
	
	public Cliente clienteFactura(Long id);
	
	public void eliminar(Long id);
	
	public Page<Cliente> listar(Pageable pageable);
	
	public List<Producto> buscarNombre(String term);
	
	public void guardarFatura(Factura factura);
	
	public Producto buscarId(Long id);
	
	public Factura obtenerFacturaId(Long id);
	
	public void eliminarFactura(Long id);
	
	public Factura unionTablas(Long id);
}
