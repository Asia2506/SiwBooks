package it.uniroma3.siw.controller.validator;

import it.uniroma3.siw.model.Libro;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LibroValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Libro.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Libro libro = (Libro) target;

        if (libro.getAnnoPubblicazione() > java.time.Year.now().getValue() + 1) { // Permetti l'anno corrente o prossimo se senso
            errors.rejectValue("annoPubblicazione", "Future.libro.annoPubblicazione", "L'anno di pubblicazione non può essere nel futuro.");
        }
    }
}
