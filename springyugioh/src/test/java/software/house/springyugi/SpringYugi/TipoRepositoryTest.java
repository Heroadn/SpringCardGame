package software.house.springyugi.SpringYugi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import software.house.springyugi.SpringYugi.models.Tipo;
import software.house.springyugi.SpringYugi.repository.TipoRespository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TipoRepositoryTest {
	
	@Autowired
	private TipoRespository tipoRepository;
	
	@Test
	public void saveTipo() {
		Tipo tipo = getTipo();
		tipoRepository.save(tipo);
		
		//Inserindo no Banco de dados
		Tipo fromDb = tipoRepository.findById(tipo.getId());
		assertNotNull(fromDb);
		
		//Se o valor inserindo é igual ao que esta no banco de dados
		assertThat(tipo.getId()).isEqualTo(fromDb.getId());
		
		//Atualizando Nome
		fromDb.setNome("Nome atualizado");
		tipoRepository.save(fromDb);
		
		//Verificando se o nome foi atualizado
		Tipo atualizado = tipoRepository.findById(tipo.getId());
		assertThat(atualizado.getNome()).isEqualTo(fromDb.getNome());
		
		//Contagem de decks
		long cartaCount = tipoRepository.count();
		assertEquals(cartaCount, 1);
		
		//Verificando contagem de registros
		Iterable<Tipo> tipos = tipoRepository.findAll();
		int count = 0;
		
		for(Tipo t : tipos) {
			count++;
		}
		
		assertEquals(count, 1);
		
	}
	
	private Tipo getTipo() {
		Tipo tipo = new Tipo();
		tipo.setNome("Fogo");
		tipo.setDescricao("Descriçao generico");
		tipo.setImagem("Image/images");
		return tipo;
	}
}
