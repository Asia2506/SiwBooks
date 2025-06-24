package it.uniroma3.siw.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.LibroValidator;
import it.uniroma3.siw.model.Autore;
import it.uniroma3.siw.model.Immagine;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.service.AutoreService;
import it.uniroma3.siw.service.ImmagineService;
import it.uniroma3.siw.service.LibroService;
import it.uniroma3.siw.service.RecensioneService;
import jakarta.validation.Valid;

@Controller
public class LibroController {
	
	private static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/images/copertine";

	
	@Autowired
	LibroService libroService;
	@Autowired
	RecensioneService recensioneService;
	@Autowired
	AutoreService autoreService;
	@Autowired
	LibroValidator libroValidator;
	@Autowired 
	ImmagineService immagineService;
	
	
	
	@GetMapping("/books/{idLibro}")
	public String getLibro(@PathVariable("idLibro") Long idLibro,Model model) {
		model.addAttribute("book",this.libroService.getBookById(idLibro));
		return "dettagliLibro.html";
	}
	
	
	@GetMapping("/user/books/{idLibro}")
	public String getLibroForUser(@PathVariable("idLibro") Long idLibro,Model model) {
		Libro libro=this.libroService.getBookById(idLibro);
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username=userDetails.getUsername();
		
		List<Recensione> altreRecensioni=libro.getRecenzioni();
		Optional<Recensione> recensioneUser=this.recensioneService.getRecensioneByUsernameEIdLibro(idLibro, username);
		if(recensioneUser.isPresent()) {
			model.addAttribute("recensioneUser",recensioneUser.get());
			altreRecensioni.remove(altreRecensioni.indexOf(recensioneUser.get()));
		}else
			model.addAttribute("recensioneUser",null);
		
		
		model.addAttribute("altreRecensioni",altreRecensioni);
		model.addAttribute("book",libro);
		return "/user/dettagliLibro.html";
	}
	
	
	@GetMapping("/books")
	public String showBooks(@RequestParam(value="title",required=false) String titoloLibro,Model model) {
		
		Iterable<Libro> libri;
		
		if(titoloLibro!=null && !titoloLibro.isEmpty()) {
			libri=this.libroService.gatAllBooksByTitle(titoloLibro);
		}else
			libri=this.libroService.getAllBooks();
		// Questa è la sezione chiave per filtrare le immagini in memoria:
		// Per ogni oggetto Libro recuperato, modifichiamo la sua lista di immagini
		// per includere SOLO la prima immagine la cui descrizione contiene "Copertina".
		// Questa modifica è TEMPORANEA e avviene solo sugli oggetti Java che passeremo al template,
		// non influisce sul database.
		
		List<Libro> libriConCopertinaFiltrata = new ArrayList<Libro>();
		for (Libro libro : libri) {
			List<Immagine> copertine = libro.getImmagini().stream()
					.filter(immagine -> immagine.getDescrizione() != null && immagine.getDescrizione().toLowerCase().contains("copertina"))
					.collect(Collectors.toList());

            if (!copertine.isEmpty()) {
                // Se abbiamo trovato una o più copertine, usiamo solo la prima.
                // Questo setImmagini sostituisce la lista originale con la lista filtrata
                // per questo specifico oggetto 'libro' in memoria.
                libro.setImmagini(List.of(copertine.get(0)));
            } else {
                // Se nessuna immagine con "Copertina" è stata trovata,
                // impostiamo una lista vuota per assicurarci che venga visualizzato il placeholder nell'HTML.
                libro.setImmagini(new ArrayList<>());
            }
            libriConCopertinaFiltrata.add(libro); // Aggiungi il libro (modificato in memoria) alla lista finale
		}
		model.addAttribute("books",libriConCopertinaFiltrata);
		return "books.html";
	}
	
	
	
	@GetMapping("/admin/manageBooks")
	public String showBooksForAdmin(@RequestParam(value="title",required=false) String titoloLibro,Model model) {
		
		Iterable<Libro> libri;
		
		if(titoloLibro!=null && !titoloLibro.isEmpty()) {
			libri=this.libroService.gatAllBooksByTitle(titoloLibro);
		}else
			libri=this.libroService.getAllBooks();
		
		List<Libro> libriConCopertinaFiltrata = new ArrayList<Libro>();
		for (Libro libro : libri) {
			List<Immagine> copertine = libro.getImmagini().stream()
					.filter(immagine -> immagine.getDescrizione() != null && immagine.getDescrizione().toLowerCase().contains("copertina"))
					.collect(Collectors.toList());

            if (!copertine.isEmpty()) {
                libro.setImmagini(List.of(copertine.get(0)));
            } else {
                libro.setImmagini(new ArrayList<>());
            }
            libriConCopertinaFiltrata.add(libro); // Aggiungi il libro (modificato in memoria) alla lista finale
		}
		model.addAttribute("books",libriConCopertinaFiltrata);
		return "/admin/manageBooks.html";
	}
	
	
	
	
	@GetMapping("/user/books")
	public String showBooksForUser(@RequestParam(value="title",required=false) String titoloLibro,Model model) {
		
		Iterable<Libro> libri;
		
		if(titoloLibro!=null && !titoloLibro.isEmpty()) {
			libri=this.libroService.gatAllBooksByTitle(titoloLibro);
		}else
			libri=this.libroService.getAllBooks();
		
		List<Libro> libriConCopertinaFiltrata = new ArrayList<Libro>();
		for (Libro libro : libri) {
			List<Immagine> copertine = libro.getImmagini().stream()
					.filter(immagine -> immagine.getDescrizione() != null && immagine.getDescrizione().toLowerCase().contains("copertina"))
					.collect(Collectors.toList());

            if (!copertine.isEmpty()) {
                libro.setImmagini(List.of(copertine.get(0)));
            } else {
                libro.setImmagini(new ArrayList<>());
            }
            libriConCopertinaFiltrata.add(libro); // Aggiungi il libro (modificato in memoria) alla lista finale
		}
		model.addAttribute("books",libriConCopertinaFiltrata);
		return "/user/books.html";
	}
	
	
	
	
	@GetMapping("/admin/books/delete/{idLibro}")
	public String deleteBook(@PathVariable("idLibro") Long id,Model model) {
		Libro libro=this.libroService.getBookById(id);
		for(Autore autore:libro.getAutori()) {
			autore.getLibri().remove(autore.getLibri().indexOf(libro));
		}
		this.libroService.deleteBook(libro);
		return "redirect:/admin/manageBooks";
	}
	
	
	
	
	
	
	@GetMapping("/user/books/{idLibro}/reviewForm")
	public String writeNewReview(@PathVariable("idLibro") Long id,Model model) {
		model.addAttribute("recensione",new Recensione());
		model.addAttribute("libro",this.libroService.getBookById(id));
		return "/user/formNewRecensione.html";
	}
	
	
	
	
	
	@GetMapping("/admin/addBookForm")
	public String showNewAutoreForm(Model model) {
        model.addAttribute("libro", new Libro()); // Inizializza un nuovo oggetto Autore per il form
        model.addAttribute("autori", autoreService.getAllAuthors());
        return "/admin/formNewLibro.html";
    }
	
	
	@PostMapping("/admin/addBook")
    public String createNewLibro(@Valid @ModelAttribute("libro") Libro libro,
                               BindingResult bindingResult,
                               @RequestParam("fileImmagineCopertina") MultipartFile fileImmagineCopertina,
                               @RequestParam(value = "autori", required = false) List<Long> selectedAutoriIds,
                               Model model) {
		
		// Imposta gli autori sul libro prima della validazione
        List<Autore> autoriPerLibro = new ArrayList<>();
        if (selectedAutoriIds != null && !selectedAutoriIds.isEmpty()) {
            autoriPerLibro = autoreService.findAllById(selectedAutoriIds);
        }
        libro.setAutori(autoriPerLibro);
		
		libroValidator.validate(libro, bindingResult);

        // Verifica se ci sono errori di validazione
        if (bindingResult.hasErrors()) {
            // Se ci sono errori, torna al form e mostra i messaggi
            return "/admin/formNewLibro.html";
        }

        // 3. Gestione del caricamento della fotografia
        if (!fileImmagineCopertina.isEmpty()) {
            try {
                // Genera un nome file unico per evitare sovrascritture
                String fileName =fileImmagineCopertina.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIRECTORY, fileName);
                
                // Assicurati che la directory esista
                Files.createDirectories(filePath.getParent());

                // Salva il file sul disco
                Files.copy(fileImmagineCopertina.getInputStream(), filePath);

                // Crea un oggetto Immagine e salvalo nel database
                Immagine fotografia = new Immagine();
                fotografia.setPath("/images/copertine/" + fileName); // Path relativo per il browser
                String nomeImg="Fotografia di " + libro.getTitolo();
                fotografia.setDescrizione(nomeImg);
                immagineService.save(fotografia); // Salva l'immagine nel DB

                // Associa l'immagine all'autore
                libro.getImmagini().add(fotografia);

            } catch (IOException e) {
                // Gestione dell'errore di caricamento del file
                model.addAttribute("fileError", "Errore nel caricamento della fotografia: " + e.getMessage());
                return "/admin/formNewLibro.html"; // Torna al form con l'errore
            }
        } else {
            // Se la fotografia è obbligatoria, aggiungi un errore qui
            // model.addAttribute("fileError", "La fotografia dell'autore è obbligatoria.");
            // return "formNewAutore.html";
            // Oppure, se è opzionale, non fare nulla o imposta un'immagine di default
        }

        // 4. Salva l'autore nel database
        libroService.save(libro);

        return "redirect:/admin/manageBooks"; // Reindirizza alla pagina di gestione autori
		
	}
	
	
	
	
}
