package it.uniroma3.siw.controller.validator; // Assicurati che il pacchetto sia corretto

import it.uniroma3.siw.model.Recensione;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validatore personalizzato per l'entità Recensione.
 * Controlla la validità del titolo, testo e voto della recensione.
 */
@Component
public class RecensioneValidator implements Validator {

    private static final int MIN_VOTO = 1;
    private static final int MAX_VOTO = 5;

    /**
     * Indica se questo validatore supporta la classe fornita.
     * @param clazz la classe da validare.
     * @return true se la classe è Recensione, false altrimenti.
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Recensione.class.equals(clazz);
    }

    /**
     * Esegue la validazione sull'oggetto target e registra gli errori nell'oggetto Errors.
     * @param target l'oggetto da validare (una Recensione).
     * @param errors l'oggetto Errors in cui registrare eventuali errori.
     */
    @Override
    public void validate(Object target, Errors errors) {
        Recensione recensione = (Recensione) target;

        // Validazione del titolo
        if (recensione.getTitolo() == null || recensione.getTitolo().isBlank()) {
            errors.rejectValue("titolo", "NotBlank.recensione.titolo", "Il titolo non può essere vuoto.");
        } else if (recensione.getTitolo().length() > 100) {
            errors.rejectValue("titolo", "Size.recensione.titolo.max", "Il titolo non può superare i 100 caratteri.");
        }

        // Validazione del testo
        if (recensione.getTesto() == null || recensione.getTesto().isBlank()) {
            errors.rejectValue("testo", "NotBlank.recensione.testo", "Il testo della recensione non può essere vuoto.");
        } else if (recensione.getTesto().length() > 1000) { // Ho impostato 1000 come max, puoi cambiarlo se serve
            errors.rejectValue("testo", "Size.recensione.testo.max", "Il testo della recensione non può superare i 1000 caratteri.");
        }

        // Validazione del voto
        // Ho assunto che 'voto' in Recensione.java sia ora di tipo `int` o `Integer`.
        // Se fosse ancora `String`, la logica qui sotto dovrebbe includere un parsing sicuro.
        // Se il campo in Recensione fosse `int valutazione;` e usassi @NotNull, @Min, @Max direttamente
        // sulla proprietà dell'entità, parte di questa validazione potrebbe essere gestita da quelle annotazioni.
        if (recensione.getVoto() == 0) { // Controlla se il voto è stato lasciato a 0 (valore di default per int) o non impostato
             errors.rejectValue("valutazione", "NotNull.recensione.valutazione", "Il voto non può essere vuoto.");
        } else if (recensione.getVoto() < MIN_VOTO || recensione.getVoto() > MAX_VOTO) {
            errors.rejectValue("valutazione", "Range.recensione.valutazione", "Il voto deve essere compreso tra " + MIN_VOTO + " e " + MAX_VOTO + ".");
        }

        // Potresti aggiungere qui altre logiche di validazione personalizzate
        // Esempio: Controllo se l'utente ha già recensito questo libro (anche se questa logica
        // è meglio gestirla nel servizio/controller prima di chiamare il validatore).
    }
}

