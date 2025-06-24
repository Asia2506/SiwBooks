package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Autore;
import it.uniroma3.siw.repository.AutoreRepository;

@Service
public class AutoreService {
	
	@Autowired
	private AutoreRepository autoreRepository;
	
	
	
	public Iterable<Autore> getAllAuthors(){
		return this.autoreRepository.findAll();
	}
	
	public List<Autore> findAllById(Iterable<Long> ids){
		return (List<Autore>) this.autoreRepository.findAllById(ids);
	}
	
	
	public Autore getAuthorById(Long id) {
		return this.autoreRepository.findById(id).orElse(null);
	}
	
	
	public Iterable<Autore> getAllAuthorsBySurname(String cognome){
		return this.autoreRepository.findByCognomeContainingIgnoreCase(cognome);
	}
	
	
	public void deleteAuthor(Autore autore) {
		this.autoreRepository.delete(autore);
	}
	
	public void save(Autore autore) {
		this.autoreRepository.save(autore);
	}
}
