package sv.edu.udb.www.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sv.edu.udb.www.models.entity.Categoria;
import sv.edu.udb.www.models.entity.Producto;
import sv.edu.udb.www.models.entity.Proveedor;

public interface IProductoService {

	public List<Producto> listar();
	
	public void insertar (Producto producto);
	
	public Producto obtener(Long id);
	
	public void eliminar(Long id);
	
	public Page<Producto> listar(Pageable pageable);
	
	public Categoria buscar(Long id);
	
	public Proveedor buscarP(Long id);
	
	 
	
	
}
