package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.repository.RecensioneRepository;

@Service
public class RecensioneService {

	@Autowired
	private RecensioneRepository recensioneRepository;
	
	
	
	public Recensione save(Recensione recensione) {
        return recensioneRepository.save(recensione);
    }
	
	
	
	public Optional<Recensione> getRecensioneByUsernameEIdLibro(Long idLibro,String username){
		return this.recensioneRepository.findByLibroIdAndCredentialsUsername(idLibro, username);
	}
	
	
	public List<Recensione> getRecensioniByCredentials(Credentials credentials){
		return this.recensioneRepository.findByCredentials(credentials);
	}
}
