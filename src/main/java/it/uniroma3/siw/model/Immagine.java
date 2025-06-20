package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Immagine {

	/*--------ID E VAR. D'ISTANZA--------*/
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String descrizione;
	private String path;
	
	
	
	
	
	/*--------EQUALS AND HASHCODE--------*/
	@Override
	public int hashCode() {
		return Objects.hash(path);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Immagine other = (Immagine) obj;
		return Objects.equals(path, other.path);
	}
	
	
	
	
	
	
	/*--------METODI GETTERS AND SETTERS--------*/
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
	
	
	
}
