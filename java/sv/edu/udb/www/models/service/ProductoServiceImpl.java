package sv.edu.udb.www.models.service;

import java.util.List;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sv.edu.udb.www.models.dao.ICategoriaDao;
import sv.edu.udb.www.models.dao.IProductoDao;
import sv.edu.udb.www.models.dao.IProveedorDao;
import sv.edu.udb.www.models.entity.Categoria;
import sv.edu.udb.www.models.entity.Producto;
import sv.edu.udb.www.models.entity.Proveedor;
@Service
public class ProductoServiceImpl implements IProductoService{
	
	@Autowired
	IProductoDao productoDao;
	@Autowired
	ICategoriaDao categoriaDao;
	@Autowired
	IProveedorDao proveedorDao;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> listar() {
		 
		return (List<Producto>) productoDao.findAll();
	}

	@Override
	@Transactional
	public void insertar(Producto producto) {
		 productoDao.save(producto);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Producto obtener(Long id) {
		 
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		 
		 productoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Producto> listar(Pageable pageable) {
		 
		return productoDao.findAll(pageable);
	}

	@Override
	public Categoria buscar(Long id) {
		 
		return categoriaDao.findById(id).orElse(null);
	}

	@Override
	public Proveedor buscarP(Long id) {
		
		return proveedorDao.findById(id).orElse(null);
	}

	 
	
	

}
