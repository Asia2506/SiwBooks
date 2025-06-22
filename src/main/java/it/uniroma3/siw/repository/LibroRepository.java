package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Libro;

public interface LibroRepository extends CrudRepository<Libro,Long>{
	
	List<Libro> findByTitoloContainingIgnoreCase(String titolo);
}
