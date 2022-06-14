package sv.edu.udb.www.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sv.edu.udb.www.models.dao.IProveedorDao;
import sv.edu.udb.www.models.entity.Proveedor;

@Service
public class ProveedorServiceImpl implements IProveedorService{

	@Autowired
	private IProveedorDao proveedorDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Proveedor> findAll() {
		// TODO Auto-generated method stub
		return (List<Proveedor>) proveedorDao.findAll();
	}

	@Override
	@Transactional
	public void guardar(Proveedor proveedor) {
		// TODO Auto-generated method stub
		proveedorDao.save(proveedor);
	}

	@Override
	@Transactional(readOnly=true)
	public Proveedor findOne(Long id) {
		// TODO Auto-generated method stub
		return proveedorDao.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Long id) {
		// TODO Auto-generated method stub
		
		proveedorDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Proveedor> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return proveedorDao.findAll(pageable);
	}

	@Override
	public Proveedor obtener(Long id) {
		// TODO Auto-generated method stub
		return proveedorDao.findById(id).orElse(null);
	}

}
