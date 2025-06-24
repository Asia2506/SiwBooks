package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Autore;
import it.uniroma3.siw.model.Immagine;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.service.LibroService;
import it.uniroma3.siw.service.RecensioneService;

@Controller
public class LibroController {
	
	@Autowired
	LibroService libroService;
	@Autowired
	RecensioneService recensioneService;
	
	
	
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
	
	
	
	
	
}
