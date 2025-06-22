package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.service.AutoreService;

@Controller
public class AutoreController {
	
	@Autowired
	AutoreService autoreService;
	
	
	@GetMapping("/authors")
	public String showAutors(@RequestParam(value="surname",required=false) String cognome,Model model) {
		
		if(cognome!=null && !cognome.isEmpty()) 
			model.addAttribute("authors",this.autoreService.getAllAuthorsBySurname(cognome));
		else	
			model.addAttribute("authors",this.autoreService.getAllAuthors());
		
		return "authors.html";
	}
	
	
	@GetMapping("/authors/{idAutore}")
	public String getAutore(@PathVariable("idAutore") Long idAutore,Model model) {
		model.addAttribute("author",this.autoreService.getAuthorById(idAutore));
		return "dettagliAutore.html";
	}
	
}
