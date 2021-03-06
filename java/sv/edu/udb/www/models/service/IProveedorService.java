package sv.edu.udb.www.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sv.edu.udb.www.models.entity.Proveedor;

public interface IProveedorService {

	public List<Proveedor> findAll();
	
	public Page<Proveedor> findAll(Pageable pageable);
	
	public void guardar(Proveedor proveedor);
	
	public Proveedor findOne(Long id);
	
	public void eliminar(Long id);
	
	public Proveedor obtener(Long id);
}
