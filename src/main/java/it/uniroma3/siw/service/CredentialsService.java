package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;

@Service
public class CredentialsService {

	@Autowired
	private CredentialsRepository credentialsRepository;
	
	@Autowired
    protected PasswordEncoder passwordEncoder;
	
	
	
	@Transactional
	public Credentials getCredentials(Long id) {
		return this.credentialsRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public Credentials getCredentials(String username) {
		return this.credentialsRepository.findByUsername(username).orElse(null);
	}
	
	
	@Transactional
    public Credentials saveCredentials(Credentials credentials) {
        credentials.setRole(Credentials.USER_ROLE);
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }
	
}
