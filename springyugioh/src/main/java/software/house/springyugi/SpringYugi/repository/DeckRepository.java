package software.house.springyugi.SpringYugi.repository;

import org.springframework.data.repository.CrudRepository;
import software.house.springyugi.SpringYugi.models.Deck;

public interface DeckRepository extends CrudRepository<Deck, Long>{
	
	Deck findByUsuario(Long deck_id);
}
