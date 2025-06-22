package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Libro {
	
	/*--------ID E VAR. D'ISTANZA--------*/
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int annoPubblicazione;
	private String titolo;
	
	/*--------ASSOCIAZIONI--------*/
	//una o più immagine
	@OneToMany(cascade = CascadeType.ALL)
	List<Immagine> immagini;
	//uno o più autori
	@ManyToMany(mappedBy="libri")
	List<Autore> autori;
	//recensioni
	@OneToMany(mappedBy="libro",cascade = CascadeType.ALL)
	List<Recensione> recenzioni;
	
	
	
	
	/*--------EQUALS AND HASHCODE--------*/
	@Override
	public int hashCode() {
		return Objects.hash(annoPubblicazione, titolo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		return annoPubblicazione == other.annoPubblicazione && Objects.equals(titolo, other.titolo);
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
	
	public int getAnnoPubblicazione() {
		return annoPubblicazione;
	}
	public void setAnnoPubblicazione(int annoPubblicazione) {
		this.annoPubblicazione = annoPubblicazione;
	}
	
	public List<Immagine> getImmagini() {
		return immagini;
	}
	public void setImmagini(List<Immagine> immagini) {
		this.immagini = immagini;
	}
	
	public List<Autore> getAutori() {
		return autori;
	}
	public void setAutori(List<Autore> autori) {
		this.autori = autori;
	}
	
	public List<Recensione> getRecenzioni() {
		return recenzioni;
	}
	public void setRecenzioni(List<Recensione> recenzioni) {
		this.recenzioni = recenzioni;
	}
	
	
	
	
	
	
}
