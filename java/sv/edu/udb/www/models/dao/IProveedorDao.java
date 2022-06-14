package sv.edu.udb.www.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import sv.edu.udb.www.models.entity.Proveedor;

public interface IProveedorDao extends PagingAndSortingRepository<Proveedor, Long> {

}
