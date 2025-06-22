package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.service.AutoreService;

@Controller
public class AutoreController {
	
	@Autowired
	AutoreService autoreService;
	
	
	@GetMapping("/authors")
	public String showAutors(Model model) {
		model.addAttribute("authors",this.autoreService.getAllAutors());
		return "authors.html";
	}
	
	
	@GetMapping("/authors/{idAutore}")
	public String getAutore(@PathVariable("idAutore") Long idAutore,Model model) {
		model.addAttribute("author",this.autoreService.getAuthorById(idAutore));
		return "dettagliAutore.html";
	}
	
}
