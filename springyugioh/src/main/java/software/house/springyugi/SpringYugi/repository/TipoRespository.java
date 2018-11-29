package software.house.springyugi.SpringYugi.repository;

import org.springframework.data.repository.CrudRepository;
import software.house.springyugi.SpringYugi.models.Tipo;

public interface TipoRespository extends CrudRepository<Tipo, Long>{

	Tipo findById(long codigo);
	
	Tipo findByNome(String nome);
}
