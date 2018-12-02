package software.house.springyugi.SpringYugi.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Deck")
public class Deck implements Serializable{
	
	private static final long serialVersionUID = -6920073845093218917L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "nome", nullable = false) 
	private String nome;
	
	@Column(name = "descricao", nullable = false) 
	private String descricao;
	
	@Column(name = "imagem", nullable = false) 
	private String imagem;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Carta> cartas = new ArrayList<>();
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private Usuario usuario;
	
	public Deck(long id, String nome, String descricao, String imagem, List<Carta> cartas) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.imagem = imagem;
		this.cartas = cartas;
	}
	
	public Deck() {}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public List<Carta> getCartas() {
		return cartas;
	}	

	public void setCartas(List<Carta> cartas) {
		this.cartas = cartas;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
