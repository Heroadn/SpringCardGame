package software.house.springyugi.SpringYugi.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable{

	private static final long serialVersionUID = -1207132481322371409L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "nome", nullable = false) 
	private String nome;
	
	@Email
	@Column(name = "email", nullable = false) 
	private String email;
	
	@Column(name = "imagem", nullable = false) 
	private String imagem;
	
	@Column(name = "salt", nullable = false) 
	private String salt;
	
	@Column(name = "pontos", nullable = false) 
	private int pontos;
	
	@Column(name = "senha", nullable = false) 
	private String senha;
	
	@Transient
	private String confirmPass;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "usuario")
	private List<Deck> decks = new ArrayList<>();

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

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public List<Deck> getDecks() {
		return decks;
	}

	public void setDecks(List<Deck> decks) {
		this.decks = decks;
	}
	
	public void addDeck(Deck deck) {
		this.getDecks().add(deck);
	}
	
	public boolean isDeckEmpty() {
		return this.getDecks().isEmpty();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmPass() {
		return confirmPass;
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}
	
}
