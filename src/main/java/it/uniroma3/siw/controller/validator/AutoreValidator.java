package it.uniroma3.siw.controller.validator;

import it.uniroma3.siw.model.Autore;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class AutoreValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Autore.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Autore autore = (Autore) target;
        
        if (autore.getDataNascita()!=null && autore.getDataNascita().isAfter(LocalDate.now())) {
            errors.rejectValue("dataNascita", "Past.autore.dataNascita", "La data di nascita non può essere nel futuro.");
        }

        // Validazione della data di morte (se presente)
        if (autore.getDataMorte() != null) {
            if (autore.getDataMorte().isBefore(autore.getDataNascita())) {
                errors.rejectValue("dataMorte", "DateRange.autore.dataMorte", "La data di morte non può essere precedente alla data di nascita.");
            } else if (autore.getDataMorte().isAfter(LocalDate.now())) {
                errors.rejectValue("dataMorte", "Past.autore.dataMorte", "La data di morte non può essere nel futuro.");
            }
        }
    }
}
