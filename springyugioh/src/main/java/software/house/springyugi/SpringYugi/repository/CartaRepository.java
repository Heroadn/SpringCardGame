package software.house.springyugi.SpringYugi.repository;

import org.springframework.data.repository.CrudRepository;

import software.house.springyugi.SpringYugi.models.Carta;

public interface CartaRepository extends CrudRepository<Carta, Long>{

		Carta findById(long codigo);
		
		Carta findByNome(String nome);
}
