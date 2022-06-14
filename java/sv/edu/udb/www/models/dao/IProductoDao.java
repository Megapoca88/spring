package sv.edu.udb.www.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query; 
import org.springframework.data.repository.PagingAndSortingRepository; 
import sv.edu.udb.www.models.entity.Producto;

public interface IProductoDao extends PagingAndSortingRepository<Producto, Long>  {
	
	@Query("select p from Producto p where p.nombre like %?1%") 
	public List<Producto> buscarNombre(String term);
	
	

	 
}