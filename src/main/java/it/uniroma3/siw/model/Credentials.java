package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Credentials {
	
	public static final String USER_ROLE = "USER";
	public static final String ADMIN_ROLE = "ADMIN";
	
	/*--------ID E VAR. D'ISTANZA--------*/
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private String password;
	private String role;
	
	/*--------ASSOCIAZIONI--------*/
	@OneToOne(cascade=CascadeType.ALL)
	private User user;
	
	@OneToMany(mappedBy="utente")
	private List<Recensione> recensioniScritte;

	
	
	
	/*--------EQUALS AND HASHCODE--------*/
	@Override
	public int hashCode() {
		return Objects.hash(password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credentials other = (Credentials) obj;
		return Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}

	
	

	
	
	/*--------METODI GETTERS AND SETTERS--------*/
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public List<Recensione> getRecensioniScritte() {
		return recensioniScritte;
	}

	public void setRecensioniScritte(List<Recensione> recensioniScritte) {
		this.recensioniScritte = recensioniScritte;
	}
	
	
	
	
}
