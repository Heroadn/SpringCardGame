package software.house.springyugi.SpringYugi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import software.house.springyugi.SpringYugi.models.Carta;
import software.house.springyugi.SpringYugi.models.Tipo;
import software.house.springyugi.SpringYugi.repository.CartaRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CartaRepositoryTest {

	@Autowired
	private CartaRepository cartaRepository;
	
	@Test
	public void testCartaEmpty() {
		Iterable<Carta> cartas = cartaRepository.findAll();	
		assertThat(cartas).isEmpty();
	}
	
	@Test
	public void testSaveCarta() {
		Carta carta = getCarta();
		cartaRepository.save(carta);
		
		//Verificar se não esta nulo
		assertNotNull(carta.getId());
		
		//Peguando Carta do banco de dados
		Carta fromDb = cartaRepository.findById(carta.getId()).orElse(null);
		assertNotNull(fromDb);//Verificando se nao foi nulo
		
		//Verficando se o id mandado é igual ao recebido
		assertThat(carta.getId()).isEqualTo(fromDb.getId());
		
		fromDb.setNome("TESTE ATUALIZADO");
		cartaRepository.save(fromDb);
		
		//Verificar se o arquivo foi atualizado
		Carta atualizado = cartaRepository.findById(fromDb.getId()).orElse(null);
		assertThat(fromDb.getId()).isEqualTo(atualizado.getId());
		
		//Contagem de decks
		long cartaCount = cartaRepository.count();
		assertEquals(cartaCount, 1);
		
		//Verificando contagem de registros
		Iterable<Carta> cartas = cartaRepository.findAll();
		int count = 0;
		
		for(Carta c : cartas) {
			count++;
		}
		
		assertEquals(count, 1);
		
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
