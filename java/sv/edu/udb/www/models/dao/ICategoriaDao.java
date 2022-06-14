package sv.edu.udb.www.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import sv.edu.udb.www.models.entity.Categoria; 

public interface ICategoriaDao  extends PagingAndSortingRepository<Categoria, Long>{

}
