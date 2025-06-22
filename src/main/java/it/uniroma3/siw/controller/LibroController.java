package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Immagine;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.service.LibroService;

@Controller
public class LibroController {
	
	@Autowired
	LibroService libroService;
	
	
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
	
	
	
	@GetMapping("/books/{idLibro}")
	public String getLibro(@PathVariable("idLibro") Long idLibro,Model model) {
		model.addAttribute("book",this.libroService.getBookById(idLibro));
		return "dettagliLibro.html";
	}
	
}
