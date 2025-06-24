package it.uniroma3.siw.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
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
	public String showNewBookForm(Model model) {
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
		
		
		libroValidator.validate(libro, bindingResult);

        // Verifica se ci sono errori di validazione
        if (bindingResult.hasErrors()) {
            // Se ci sono errori, torna al form e mostra i messaggi
        	model.addAttribute("autori", autoreService.getAllAuthors());
            return "/admin/formNewLibro.html";
        }

        // 3. Gestione del caricamento della fotografia
        if (!fileImmagineCopertina.isEmpty()) {
            try {
                // Genera un nome file unico per evitare sovrascritture
                String fileName =System.currentTimeMillis() + "_copertina"+fileImmagineCopertina.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIRECTORY, fileName);
                
                // Assicurati che la directory esista
                Files.createDirectories(filePath.getParent());

                // Salva il file sul disco
                Files.copy(fileImmagineCopertina.getInputStream(), filePath);

                // Crea un oggetto Immagine e salvalo nel database
                Immagine copertina = new Immagine();
                copertina.setPath("/images/copertine/" + fileName); // Path relativo per il browser
                String nomeImg="Copertina di " + libro.getTitolo();
                copertina.setDescrizione(nomeImg);
                immagineService.save(copertina); // Salva l'immagine nel DB

                // Associa l'immagine all'autore
                
                List<Immagine> immagini=new ArrayList<Immagine>();
                immagini.add(copertina);
                libro.setImmagini(immagini);

            } catch (IOException e) {
                // Gestione dell'errore di caricamento del file
                model.addAttribute("fileError", "Errore nel caricamento della fotografia: " + e.getMessage());
                model.addAttribute("autori", autoreService.getAllAuthors());
                return "/admin/formNewLibro.html"; // Torna al form con l'errore
            }
        }
        
        //4. Salva il libro nel database
        libroService.save(libro);

     // Gestisce l'associazione con gli autori dal lato proprietario (Autore)
        if (selectedAutoriIds != null && !selectedAutoriIds.isEmpty()) {
            List<Autore> autoriSelezionati = autoreService.findAllById(selectedAutoriIds);
            
            // Imposta la lista degli autori sul libro (lato inverso)
            // Questo non persiste direttamente la relazione ManyToMany, ma è buono per la coerenza dell'oggetto in memoria.
            if(libro.getAutori() == null) {
                libro.setAutori(new ArrayList<>());
            }
            libro.getAutori().clear(); // Pulisci eventuali autori preesistenti se il libro fosse modificato
            libro.getAutori().addAll(autoriSelezionati);


            // AGGIORNAMENTO FONDAMENTALE: Aggiungi il libro alla lista di libri di ogni autore e salvali
            for (Autore autore : autoriSelezionati) {
                if (autore.getLibri() == null) {
                    autore.setLibri(new ArrayList<>());
                }
                // Controlla se il libro è già presente per evitare duplicati logici
                if (!autore.getLibri().contains(libro)) {
                    autore.getLibri().add(libro);
                }
                autoreService.save(autore); // Salva l'autore per persistere la relazione
            }
        } else {
            // Se nessun autore è selezionato, assicurati che la lista di autori del libro sia vuota
            libro.setAutori(new ArrayList<>());
        }
        

        return "redirect:/admin/manageBooks"; // Reindirizza alla pagina di gestione autori
		
	}
	
	
	
	@GetMapping("/admin/books/edit/{id}")
    public String showFormModificaLibro(@PathVariable("id") Long id, Model model) {
        Libro libro = libroService.getBookById(id);
        if (libro == null) {
            return "redirect:/error";
        }
        model.addAttribute("libro", libro);
        model.addAttribute("autori", autoreService.getAllAuthors());
        return "admin/modificaLibro.html";
    }

	 @PostMapping("/admin/books/edit/{id}")
	    public String updateBook(@PathVariable("id") Long id,
	                             @ModelAttribute("libro") Libro libroForm, // Conterrà solo gli autori selezionati
	                             BindingResult bindingResult, // Utilizzato per validazione di autori, se necessaria
	                             @RequestParam(value = "newFilesImmagini", required = false) List<MultipartFile> newFilesImmagini, // Nuove immagini da aggiungere
	                             @RequestParam(value = "imagesToDelete", required = false) List<Long> imagesToDelete, // ID delle immagini da eliminare
	                             Model model) {

	        Libro existingLibro = libroService.getBookById(id);
	        if (existingLibro == null) {
	            return "redirect:/error"; // O una pagina di errore 404
	        }

	        // Il validator del libro potrebbe validare anche campi non presenti nel form,
	        // quindi potremmo aver bisogno di un validator specifico o di un controllo manuale qui.
	        // Per ora, validiamo solo gli autori se la validazione di default li include.
	        // libroValidator.validate(libroForm, bindingResult); // Disabilitato, in quanto il form non ha tutti i campi per il validator completo

	        // Aggiorna solo gli autori, che sono modificabili
	        existingLibro.setAutori(libroForm.getAutori());

	        // 1. Gestione delle immagini da eliminare
	        if (imagesToDelete != null && !imagesToDelete.isEmpty()) {
	            Iterator<Immagine> iterator = existingLibro.getImmagini().iterator();
	            while (iterator.hasNext()) {
	                Immagine img = iterator.next();
	                if (imagesToDelete.contains(img.getId())) {
	                    try {
	                        Path filePath = Paths.get("src/main/resources/static" + img.getPath());
	                        Files.deleteIfExists(filePath); // Elimina il file dal filesystem
	                        immagineService.delete(img); // Elimina l'immagine dal DB
	                        iterator.remove(); // Rimuovi l'immagine dalla lista del libro
	                    } catch (IOException e) {
	                        System.err.println("Errore durante l'eliminazione del file immagine: " + img.getPath() + " - " + e.getMessage());
	                        // Considera di aggiungere un errore al modello o di loggare l'eccezione
	                    }
	                }
	            }
	        }

	        // 2. Gestione delle nuove immagini caricate
	        if (newFilesImmagini != null && !newFilesImmagini.isEmpty()) {
	            for (MultipartFile file : newFilesImmagini) {
	                if (!file.isEmpty()) {
	                    try {
	                        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
	                        Path uploadPath = Paths.get("src/main/resources/static/images/altre/"); // Cartella per tutte le immagini dei libri
	                        if (!Files.exists(uploadPath)) {
	                            Files.createDirectories(uploadPath);
	                        }
	                        Path filePath = uploadPath.resolve(fileName);
	                        Files.copy(file.getInputStream(), filePath);

	                        Immagine nuovaImmagine = new Immagine();
	                        nuovaImmagine.setPath("/images/altre/" + fileName);
	                        nuovaImmagine.setDescrizione("Immagine aggiuntiva di " + existingLibro.getTitolo()); // Puoi raffinare la descrizione
	                        immagineService.save(nuovaImmagine);
	                        
	                        // Inizializza la lista di immagini se è null
	                        if (existingLibro.getImmagini() == null) {
	                            existingLibro.setImmagini(new ArrayList<>());
	                        }
	                        existingLibro.getImmagini().add(nuovaImmagine);

	                    } catch (IOException e) {
	                        model.addAttribute("fileError", "Errore nel caricamento di una nuova immagine: " + e.getMessage());
	                        model.addAttribute("autori", autoreService.getAllAuthors());
	                        model.addAttribute("libro", existingLibro); // Passa il libro esistente per ripopolare il form
	                        return "/admin/modficaLibro.html"; // Torna alla stessa pagina per mostrare l'errore
	                    }
	                }
	            }
	        }

	        libroService.save(existingLibro); // Salva il libro con le modifiche alle immagini e autori

	        return "redirect:/admin/books/edit/" + id; // Reindirizza alla pagina di dettaglio del libro
	    }
	
	
}
