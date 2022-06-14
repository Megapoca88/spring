package sv.edu.udb.www.models.service;

import java.util.List;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sv.edu.udb.www.models.dao.IClienteDao;
import sv.edu.udb.www.models.dao.IFacturaDao;
import sv.edu.udb.www.models.dao.IProductoDao;
import sv.edu.udb.www.models.entity.Cliente;
import sv.edu.udb.www.models.entity.Factura;
import sv.edu.udb.www.models.entity.Producto;

@Service
public class ClienteServiceImpl implements IClienteService {
	
	@Autowired
	private IClienteDao clienteDao;
	@Autowired
	private IProductoDao productoDao;
	@Autowired
	private IFacturaDao facturaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> listar() {
		
		return (List<Cliente>) clienteDao.findAll();
	}
	
	
	@Override
	@Transactional
	public void insertar(Cliente cliente) {
		clienteDao.save(cliente);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente obtener(Long id) {
	
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		clienteDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> listar(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}
	
	@Override
	public Cliente clienteFactura(Long id) {
	
		return clienteDao.clienteFactura(id);
	}
	


	@Override
	@Transactional(readOnly = true)
	public List<Producto> buscarNombre(String term) {
		// TODO Auto-generated method stub
		return productoDao.buscarNombre(term);
	}


	@Override
	@Transactional
	public void guardarFatura(Factura factura) {
		facturaDao.save(factura);
		
	}


	@Override
	@Transactional(readOnly = true)
	public Producto buscarId(Long id) {
		 
		return productoDao.findById(id).orElse(null);
	}


	@Override
	@Transactional(readOnly = true)
	public Factura obtenerFacturaId(Long id) { 
		return facturaDao.findById(id).orElse(null);
	}


	@Override
	@Transactional
	public void eliminarFactura(Long id) {
	 facturaDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Factura unionTablas(Long id) {
		
		return facturaDao.unionTablas(id);
	}




	
}
