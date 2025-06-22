package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Autore;
import it.uniroma3.siw.repository.AutoreRepository;

@Service
public class AutoreService {
	
	@Autowired
	private AutoreRepository autoreRepository;
	
	
	
	public Iterable<Autore> getAllAutors(){
		return this.autoreRepository.findAll();
	}
	
	
	
	public Autore getAuthorById(Long id) {
		return this.autoreRepository.findById(id).orElse(null);
	}
}
