package sv.edu.udb.www.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import sv.edu.udb.www.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{
	@Query("select u from Usuario u where u.nombre like %?1%")
	public Usuario  findByUsername(String nombre);
}
