package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.repository.CredentialsRepository;

@Service
public class CredentialsService {

	@Autowired
	private CredentialsRepository credentialsRepository;
	
	
	public Credenziali getCredentials(Long id) {
		return this.credentialsRepository.findById(id).orElse(null);
	}
	
	public Credenziali getCredentials(String username) {
		return this.credentialsRepository.findByUsername(username).orElse(null);
	}
	
	public Credenziali saveCredentials(Credenziali credentials) {
		return this.credentialsRepository.save(credentials);
	}
}
