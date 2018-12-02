package software.house.springyugi.SpringYugi;

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
import software.house.springyugi.SpringYugi.models.Usuario;
import software.house.springyugi.SpringYugi.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Test
	public void testSaveUsuario() {
		Usuario usuario = getUsuario();
		
		//Verificar se o id é incrementado apos sua criação
		assertNotNull(usuario.getId());
		
		//Verificando se o id salvo no banco nao é nulo
		usuarioRepository.save(usuario);
		assertNotNull(usuario.getId());
		
		//Verificando se nao retorna nulo depois de salvo a entidade
		Usuario fromDb = usuarioRepository.findById(usuario.getId()).orElse(null);
		assertNotNull(fromDb);
		
		//Verificando se o id mandado é igual ao recebido
		assertEquals(usuario.getId(), fromDb.getId());
		
		//Verificando se os nome de deck mandados sao iguais aos criados
		//assertEquals(usuario.getDecks().get(0).getNome(), fromDb.getDecks().get(0).getNome());
		
		//Verificando se a senha criada é igual a salva no banco de dados
		assertEquals(usuario.getSenha(),fromDb.getSenha());
		
		
		
		//Quantidade de registros na tabela
		long usuarioCount = usuarioRepository.count();
		assertEquals(usuarioCount, 1);
			
		//Deve retornar apenas um usuario
		Iterable<Usuario> usuarios = usuarioRepository.findAll();
		int count = 0;
		
		for(Usuario u : usuarios) {
			count++;
		}
	
		assertEquals(count, 1);
	}
	
	@Test
	public void usuarioSalvarDeck() {
		//Adicionando Usuario ao Deck
		Usuario usuario = getUsuario();
		Deck deck = getDeck();
		
		usuario.setDecks(new ArrayList<Deck>());
		usuario.getDecks().add(deck);
	
		usuarioRepository.save(usuario);
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
	
	private Deck getDeck() {
		Deck deck = new Deck();
		deck.setNome("Deck de Teste");
		deck.setDescricao("Deck generico");
		deck.setImagem("images");
		deck.getCartas().add(getCarta());
		deck.getCartas().add(getCarta());
		
		return deck;
	}
	
	private Carta getCarta() {
		Carta carta = new Carta();
		carta.setNome("Generica");
		carta.setDescricao("Carta de Teste");
		carta.setImagem("images");
		carta.setRaridade("Super Rara");
		return carta;
	}
}
