package it.uniroma3.siw.controller.validator;

import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.service.LibroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LibroValidator implements Validator {

	@Autowired
	LibroService libroService;
	
    @Override
    public boolean supports(Class<?> clazz) {
        return Libro.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Libro libro = (Libro) target;
        
        
        if (libro.getTitolo() != null && !libro.getTitolo().isEmpty() && libro.getAnnoPubblicazione() != 0) {
            if (libroService.existsByTitoloAndAnnoPubblicazione(libro.getTitolo(), libro.getAnnoPubblicazione())) {
                // If it's an existing book being edited, allow it if it's the same book
                // This check assumes you're using this validator for both new and edit forms.
                // For a new book, libro.getId() would be null or 0.
                if (libro.getId() == null || !libroService.getBookById(libro.getId()).equals(libro)) {
                    errors.reject("libro.duplicato", "Questo libro (titolo e anno di pubblicazione) esiste già.");
                }
            }
        }
        
        if (libro.getAnnoPubblicazione() > java.time.Year.now().getValue() + 1) { // Permetti l'anno corrente o prossimo se senso
            errors.rejectValue("annoPubblicazione", "Future.libro.annoPubblicazione", "L'anno di pubblicazione non può essere nel futuro.");
        }
    }
}
