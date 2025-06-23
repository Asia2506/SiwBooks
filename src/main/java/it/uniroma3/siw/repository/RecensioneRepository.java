package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Recensione;

public interface RecensioneRepository extends CrudRepository<Recensione,Long>{
	
	
	Optional<Recensione> findByLibroIdAndUtenteUsername(Long libroId, String username);
}
