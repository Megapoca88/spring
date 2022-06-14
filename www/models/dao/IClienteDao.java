package sv.edu.udb.www.models.dao;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import sv.edu.udb.www.models.entity.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> { 
	// metodos para el paginador y para hacer el crud 
	
	
	@Query("select c from Cliente c  left join fetch c.facturas f where c.id=?1")
	public Cliente clienteFactura(Long id);
}
