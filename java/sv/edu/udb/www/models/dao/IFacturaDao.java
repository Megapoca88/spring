package sv.edu.udb.www.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import sv.edu.udb.www.models.entity.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long>{

	@Query("select f from Factura f join fetch f.cliente c join fetch f.detalles d join fetch d.producto where f.id=?1")
	public Factura unionTablas (Long id);

	
}
