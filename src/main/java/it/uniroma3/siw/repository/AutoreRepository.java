package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Autore;

public interface AutoreRepository extends CrudRepository<Autore,Long>{
	
	List<Autore> findByCognomeContainingIgnoreCase(String cognome);


    Optional<Autore> findByNomeAndCognomeAndDataNascita(String nome, String cognome, LocalDate dataNascita);
}
