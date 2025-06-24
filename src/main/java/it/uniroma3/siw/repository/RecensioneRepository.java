package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Recensione;

public interface RecensioneRepository extends CrudRepository<Recensione,Long>{
	
	
	Optional<Recensione> findByLibroIdAndCredentialsUsername(Long libroId, String username);
	
	List<Recensione> findByCredentials(Credentials utente);
	
	Optional<Recensione> findById(Long id);
}
