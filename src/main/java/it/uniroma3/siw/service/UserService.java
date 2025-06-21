package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public Utente getUser(Long id) {
		return this.userRepository.findById(id).orElse(null);
	}
	
	public Utente saveUser(Utente user) {
		return this.userRepository.save(user);
	}
}
