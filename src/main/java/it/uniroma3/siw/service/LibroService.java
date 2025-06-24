package it.uniroma3.siw.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.repository.LibroRepository;
import it.uniroma3.siw.model.Libro;

@Service
public class LibroService {

	@Autowired 
	private LibroRepository libroRepository;
	
	
	
	
	public Iterable<Libro> getAllBooks(){
		return this.libroRepository.findAll();
	}
	
	
	public Iterable<Libro> gatAllBooksByTitle(String titolo){
		return this.libroRepository.findByTitoloContainingIgnoreCase(titolo);
	}
	
	
	public Libro getBookById(Long id) {
		return this.libroRepository.findById(id).orElse(null);
	}
	
	
	public void deleteBook(Libro libro) {
		this.libroRepository.delete(libro);
	}
	
	
	public void save(Libro libro) {
		this.libroRepository.save(libro);
	}
	
	
	public boolean existsByTitoloAndAnnoPubblicazione(String titolo, int annoPubblicazione) {
		return libroRepository.findByTitoloAndAnnoPubblicazione(titolo, annoPubblicazione).isPresent();
	}
}
