package software.house.springyugi.SpringYugi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import software.house.springyugi.SpringYugi.models.Deck;
import software.house.springyugi.SpringYugi.models.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
	
	boolean existsByEmail(String Email);

	Usuario findByEmail(String email);
}
