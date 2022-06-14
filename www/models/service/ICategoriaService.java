package sv.edu.udb.www.models.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sv.edu.udb.www.models.entity.Categoria;

public interface ICategoriaService {

	public List<Categoria> listar();
	
	public void insertar(Categoria categoria);
	
	public Categoria obtener(Long id);
	
	public void eliminar(Long id);
	
	public Page<Categoria> listar(Pageable pageable);
	
	
}
