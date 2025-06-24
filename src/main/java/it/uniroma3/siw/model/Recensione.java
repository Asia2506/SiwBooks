package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Recensione {
	
	/*--------ID E VAR. D'ISTANZA--------*/
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String titolo;
	
	@NotNull
	@Min(value = 1) // Voto minimo 1
    @Max(value = 5) // Voto massimo 5
	private int voto;		//tra0 e 5
	
	@NotBlank
	private String testo;
	//utente che lo ha scritto
	
	
	
	/*--------ASSOCIAZIONI--------*/
	//libro a cui si riferisce la recensione
	@ManyToOne
	private Libro libro;
	
	@ManyToOne
	private Credentials credentials;


	
	
	
	/*--------EQUALS AND HASHCODE--------*/
	@Override
	public int hashCode() {
		return Objects.hash(libro, testo, titolo, voto);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recensione other = (Recensione) obj;
		return Objects.equals(libro, other.libro) && Objects.equals(testo, other.testo)
				&& Objects.equals(titolo, other.titolo) && Objects.equals(voto, other.voto);
	}
	
	
	
	
	
	/*--------METODI GETTERS AND SETTERS--------*/
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
	public int getVoto() {
		return voto;
	}
	public void setVoto(int voto) {
		this.voto = voto;
	}
	
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	
	public Libro getLibro() {
		return libro;
	}
	public void setLibro(Libro libro) {
		this.libro = libro;
	}
	
	public Credentials getCredentials() {
		return credentials;
	}
	public void setCredentials(Credentials utente) {
		this.credentials = utente;
	}
	
	
	
}
