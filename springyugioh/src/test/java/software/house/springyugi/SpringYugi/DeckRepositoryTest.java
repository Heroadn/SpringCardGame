package software.house.springyugi.SpringYugi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import software.house.springyugi.SpringYugi.models.Carta;
import software.house.springyugi.SpringYugi.models.Deck;
import software.house.springyugi.SpringYugi.models.Tipo;
import software.house.springyugi.SpringYugi.models.Usuario;
import software.house.springyugi.SpringYugi.repository.DeckRepository;
import software.house.springyugi.SpringYugi.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DeckRepositoryTest {

	@Autowired
	private DeckRepository deckRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Test
	public void isDeckEmpty() {
		Iterable<Deck> decks = deckRepository.findAll();
		assertThat(decks).isEmpty();
	}
	
	@Test
	public void saveDeck() {
		
		//Usuario
		Usuario usuario = getUsuario();
		usuarioRepository.save(usuario);
		
		//Deck
		Deck deck =	getDeck();
		deckRepository.save(deck);
		
		Usuario fromDBUsuario = usuarioRepository.findById(usuario.getId()).orElse(null);
		Deck fromDBDeck = deckRepository.findById(deck.getId()).orElse(null);
		
		//Atualizando
		fromDBUsuario.getDecks().add(fromDBDeck);
		fromDBDeck.setUsuario(fromDBUsuario);
		usuarioRepository.save(fromDBUsuario);
		deckRepository.save(fromDBDeck);
		
		//Peguado o deck inserindo do Banco de dados
		Deck fromDb = deckRepository.findById(deck.getId()).orElse(null);
		assertNotNull(fromDb);

		//Verficado se as informaçoes mandadas sao iguais as recebidas
		assertThat(deck.getId()).isEqualTo(fromDb.getId());
		
		//Atualizando Nome
		fromDb.setNome("Nome Atualizado");
		deckRepository.save(fromDb);
		
		//Verificando se o nome foi atualizado
		Deck atualizado = deckRepository.findById(fromDb.getId()).orElse(null);
		assertThat(fromDb.getId()).isEqualTo(atualizado.getId());
		
		//Contagem de decks
		long deckCount = deckRepository.count();
		assertEquals(deckCount, 1);
		
		//Verificando contagem de registros
		Iterable<Deck> decks = deckRepository.findAll();
		int count = 0;
		
		for(Deck d : decks) {
			count++;
		}
		
		assertEquals(count, 1);
		
		Iterable<Usuario>  usuarios = usuarioRepository.findAll();
		for(Usuario user : usuarios) {
			System.out.println(user.getDecks().get(0).getNome());
		}
	}
	
	@Test
	public void linkUserDeck() {
		
	}
	
	private Deck getDeck() {
		Deck deck = new Deck();
		deck.setNome("Deck de Teste");
		deck.setDescricao("Deck generico");
		deck.setImagem("images");	
		deck.getCartas().add(getCarta());
		return deck;
	}
	
	public Usuario getUsuario() {
		Usuario usuario = new Usuario();
			usuario.setNome("Generico");
			usuario.setEmail("Teste");
			usuario.setImagem("Imagem");
			usuario.setSenha("Senha");
			usuario.setSalt("Salt");
			usuario.setPontos(1000);
		return usuario;
	}
	
	private Carta getCarta() {
		Carta carta = new Carta();
		carta.setNome("Generica");
		carta.setDescricao("Carta de Teste");
		carta.setImagem("images");
		carta.setRaridade("Super Rara");
		carta.setTipo(getTipo());
		return carta;
	}
	
	private Tipo getTipo() {
		Tipo tipo = new Tipo();
		tipo.setNome("Fogo");
		tipo.setDescricao("Descriçao generico");
		tipo.setImagem("Image/images");
		return tipo;
	}
	
}
