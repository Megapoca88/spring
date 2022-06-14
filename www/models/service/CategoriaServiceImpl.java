package sv.edu.udb.www.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sv.edu.udb.www.models.dao.ICategoriaDao;
import sv.edu.udb.www.models.entity.Categoria;

@Service
public class CategoriaServiceImpl implements ICategoriaService{

	@Autowired
	private ICategoriaDao categoriaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Categoria> listar() {
		 
		return (List<Categoria>) categoriaDao.findAll();
	}

	@Override
	@Transactional
	public void insertar(Categoria categoria) {
		 categoriaDao.save(categoria);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Categoria obtener(Long id) {
		 
		return categoriaDao.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Long id) {
		 
		categoriaDao.deleteById(id);
		
	}

	@Override
	public Page<Categoria> listar(Pageable pageable) {
		 
		return categoriaDao.findAll(pageable);
	}

}
