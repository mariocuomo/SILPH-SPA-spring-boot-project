package it.uniroma3.siw.demospring.controller;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.demospring.model.Fotografo;
import it.uniroma3.siw.demospring.model.Studente;
import it.uniroma3.siw.demospring.services.FotografoService;
import it.uniroma3.siw.demospring.services.StudenteService;
import it.uniroma3.siw.demospring.services.StudenteValidator;

@Controller
public class MainController {

	@Autowired
	FotografoService fotografoService;

	@Autowired
	private StudenteService studenteService;

	@Autowired
	private StudenteValidator studenteValidator;

	@RequestMapping(value = "/studente", method = RequestMethod.POST)
	public String newStudente(@Valid @ModelAttribute("studente") Studente studente,
			Model model, BindingResult bindingResult) {

		this.studenteValidator.validate(studente, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.studenteService.inserisci(studente);
			model.addAttribute("studenti", this.studenteService.tutti());
			return "studenti.html";
		}else {
			return "studenteForm.html";
		}
	}

	@RequestMapping(value = "/fotografo/{id}", method = RequestMethod.GET)
	public String getFotografo(@PathVariable ("id") Long id, Model model) {
		if(id!=null) {
			model.addAttribute("fotografo", this.fotografoService.FotografoPerId(id));
			return "fotografo.html";
		}else {
			model.addAttribute("fotografi", this.fotografoService.tutti());
			return "fotografi.html";
		}
	}

	@RequestMapping(value="/entra", method=RequestMethod.GET)
	public String entra(Model model, @RequestParam String action) {
		
		Fotografo fotografo1 = new Fotografo();
		fotografo1.setNome("mario");
		fotografo1.setCognome("cuomo");

		Fotografo fotografo2 = new Fotografo();
		fotografo2.setNome("francesca");
		fotografo2.setNome("leone");
		
		fotografoService.salva(fotografo1);
		fotografoService.salva(fotografo2);
		
		if( action.equals("visitatore") )
			return "scelta.html";
		else
			return "studenteForm.html";
	}
	
	@RequestMapping(value="/fotografi", method=RequestMethod.GET)	
	public String fotografi(Model model) {
		model.addAttribute("fotografi", fotografoService.tutti());
		return "fotografi.html";
	}
}
