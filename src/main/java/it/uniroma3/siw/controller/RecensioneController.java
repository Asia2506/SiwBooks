package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.LibroService;
import it.uniroma3.siw.service.RecensioneService;
import jakarta.validation.Valid;

@Controller
public class RecensioneController {
	
	@Autowired
	RecensioneService recensioneService;
	@Autowired 
	LibroService libroService;
	@Autowired 
	CredentialsService credentialsService;
	
	
	
	@PostMapping("/user/books/{libroId}/addReview")
    public String addReview(@PathVariable("libroId") Long libroId,
                            @Valid @ModelAttribute("recensione") Recensione recensione, // <--- Qui usiamo @Valid!
                            BindingResult bindingResult, // <--- Qui catturiamo gli errori di validazione
                            Model model) {

        
		Libro libro=this.libroService.getBookById(libroId);
		
		
		//recensioneValidator.validate(recensione, bindingResult);
        // Controlla se ci sono errori di validazione
        if (bindingResult.hasErrors()) {
            // Se ci sono errori, torna al form mostrando i messaggi di errore
            model.addAttribute("libro",libro ); // Assicurati di ripassare il libro al template
            model.addAttribute("errorMessage", "Ci sono errori nel form. Controlla i campi."); // Messaggio generico
            return "/user/formNewRecensione.html"; // Torna allo stesso form
        }
     // Se la validazione è superata:
        // Associa il libro alla recensione
        recensione.setLibro(libro);
    

        // Associa l'utente autenticato alla recensione
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials=this.credentialsService.getCredentials(userDetails.getUsername());

        
        recensione.setCredentials(credentials); // Associa le credenziali alla recensione

        // Salva la recensione tramite il servizio
        recensioneService.save(recensione);
        
        credentials.getRecensioniScritte().add(recensione);
        libro.getRecenzioni().add(recensione);
        
       
        return "redirect:/user/books/" + libro.getId();
    }
	
	
	
	@GetMapping("/user/recensioni")
	public String getMyReview(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials=this.credentialsService.getCredentials(userDetails.getUsername());

		model.addAttribute("myReviews",this.recensioneService.getRecensioniByCredentials(credentials));
		return "/user/recensioni.html";
	}
}
