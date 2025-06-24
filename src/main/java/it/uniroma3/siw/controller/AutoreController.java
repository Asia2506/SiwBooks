package it.uniroma3.siw.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.AutoreValidator;
import it.uniroma3.siw.model.Autore;
import it.uniroma3.siw.model.Immagine;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.service.AutoreService;
import it.uniroma3.siw.service.ImmagineService;
import jakarta.validation.Valid;

@Controller
public class AutoreController {
	
	// Definisci la directory dove verranno salvate le immagini
    private static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/images/fotografie";

	
	@Autowired
	AutoreService autoreService;
	@Autowired
	AutoreValidator autoreValidator;
	@Autowired
	ImmagineService immagineService;
	
	
	@GetMapping("/authors")
	public String showAutors(@RequestParam(value="surname",required=false) String cognome,Model model) {
		
		if(cognome!=null && !cognome.isEmpty()) 
			model.addAttribute("authors",this.autoreService.getAllAuthorsBySurname(cognome));
		else	
			model.addAttribute("authors",this.autoreService.getAllAuthors());
		
		return "authors.html";
	}
	
	
	@GetMapping("/user/authors")
	public String showAutorsForUser(@RequestParam(value="surname",required=false) String cognome,Model model) {
		
		if(cognome!=null && !cognome.isEmpty()) 
			model.addAttribute("authors",this.autoreService.getAllAuthorsBySurname(cognome));
		else	
			model.addAttribute("authors",this.autoreService.getAllAuthors());
		
		return "/user/authors.html";
	}
	
	
	@GetMapping("/admin/manageAuthors")
	public String showAutorsForAdmin(@RequestParam(value="surname",required=false) String cognome,Model model) {
		
		if(cognome!=null && !cognome.isEmpty()) 
			model.addAttribute("authors",this.autoreService.getAllAuthorsBySurname(cognome));
		else	
			model.addAttribute("authors",this.autoreService.getAllAuthors());
		
		return "/admin/manageAuthors.html";
	}
	
	
	
	@GetMapping("/admin/authors/delete/{idAutore}")
	public String deleteBook(@PathVariable("idAutore") Long id,Model model) {
		Autore autore=this.autoreService.getAuthorById(id);
		for(Libro libro:autore.getLibri()) {
			libro.getAutori().remove(libro.getAutori().indexOf(autore));
		}
		this.autoreService.deleteAuthor(autore);
		return "redirect:/admin/manageAuthors";
	}
	
	
	
	@GetMapping("/authors/{idAutore}")
	public String getAutore(@PathVariable("idAutore") Long idAutore,Model model) {
		model.addAttribute("author",this.autoreService.getAuthorById(idAutore));
		return "dettagliAutore.html";
	}
	
	
	@GetMapping("/user/authors/{idAutore}")
	public String getAutoreForUser(@PathVariable("idAutore") Long idAutore,Model model) {
		model.addAttribute("author",this.autoreService.getAuthorById(idAutore));
		return "/user/dettagliAutore.html";
	}
	
	
	@GetMapping("/admin/addAuthorForm")
	public String showNewAutoreForm(Model model) {
        model.addAttribute("autore", new Autore()); // Inizializza un nuovo oggetto Autore per il form
        return "/admin/formNewAutore.html";
    }
	
	
	@PostMapping("/admin/addAuthor")
    public String createNewAutore(@Valid @ModelAttribute("autore") Autore autore,
                                BindingResult bindingResult,
                                @RequestParam("fileFotografia") MultipartFile fileFotografia, // Per il caricamento del file
                                Model model) {

        // 1. Esegui la validazione delle annotazioni JSR-303/380 sull'oggetto Autore
        //    (già fatto con @Valid)

        // 2. Esegui la validazione personalizzata con AutoreValidator
        autoreValidator.validate(autore, bindingResult);

        // Verifica se ci sono errori di validazione
        if (bindingResult.hasErrors()) {
            // Se ci sono errori, torna al form e mostra i messaggi
            return "/admin/formNewAutore.html";
        }

        // 3. Gestione del caricamento della fotografia
        if (!fileFotografia.isEmpty()) {
            try {
                // Genera un nome file unico per evitare sovrascritture
                String fileName =fileFotografia.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIRECTORY, fileName);
                
                // Assicurati che la directory esista
                Files.createDirectories(filePath.getParent());

                // Salva il file sul disco
                Files.copy(fileFotografia.getInputStream(), filePath);

                // Crea un oggetto Immagine e salvalo nel database
                Immagine fotografia = new Immagine();
                fotografia.setPath("/images/fotografie/" + fileName); // Path relativo per il browser
                String nomeImg="Fotografia di " + autore.getNome() + " " + autore.getCognome();
                fotografia.setDescrizione(nomeImg);
                immagineService.save(fotografia); // Salva l'immagine nel DB

                // Associa l'immagine all'autore
                autore.setFotografia(fotografia);

            } catch (IOException e) {
                // Gestione dell'errore di caricamento del file
                model.addAttribute("fileError", "Errore nel caricamento della fotografia: " + e.getMessage());
                return "/admin/formNewAutore.html"; // Torna al form con l'errore
            }
        } else {
            // Se la fotografia è obbligatoria, aggiungi un errore qui
            // model.addAttribute("fileError", "La fotografia dell'autore è obbligatoria.");
            // return "formNewAutore.html";
            // Oppure, se è opzionale, non fare nulla o imposta un'immagine di default
        }

        // 4. Salva l'autore nel database
        autoreService.save(autore);

        return "redirect:/admin/manageAuthors"; // Reindirizza alla pagina di gestione autori
    }
}
