package it.uniroma3.siw.demospring.controller;

import java.util.List;

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

import it.uniroma3.siw.demospring.model.Album;
import it.uniroma3.siw.demospring.model.Fotografo;
import it.uniroma3.siw.demospring.model.Studente;
import it.uniroma3.siw.demospring.services.AlbumService;
import it.uniroma3.siw.demospring.services.FotografoService;
import it.uniroma3.siw.demospring.services.StudenteService;
import it.uniroma3.siw.demospring.services.StudenteValidator;
import it.uniroma3.siw.demospring.services.Service;


@Controller
public class MainController {

	@Autowired
	FotografoService fotografoService;

	@Autowired
	AlbumService albumService;

	@Autowired
	Service service;


	@RequestMapping(value = "fotografo/{id}", method = RequestMethod.GET)
	public String getFotografo(@PathVariable ("id") Long id, Model model) {
		if(id!=null) {			
			List<Album> albums = service.albumDiArtista(albumService.tutti(), id);
			model.addAttribute("fotografo", this.fotografoService.FotografoPerId(id));
			model.addAttribute("albums", albums);
			return "fotografo.html";
		}else {
			model.addAttribute("fotografi", this.fotografoService.tutti());
			return "fotografi.html";
		}
	}


	@RequestMapping(value="/entra", method=RequestMethod.GET)
	public String entra(Model model, @RequestParam String action) {

		Fotografo fotografo1 = new Fotografo();
		fotografo1.setNome("Mario");
		fotografo1.setCognome("Cuomo");

		Fotografo fotografo2 = new Fotografo();
		fotografo2.setNome("Francesca");
		fotografo2.setCognome("Leone");

		Album album1 = new Album();
		album1.setNome("via");
		Album album2 = new Album();
		album2.setNome("ora");
		album1.setFotografo(fotografo1);
		album2.setFotografo(fotografo1);

		Album album3 = new Album();
		album3.setNome("tutto");
		Album album4 = new Album();
		album4.setNome("losco");
		album3.setFotografo(fotografo2);
		album4.setFotografo(fotografo2);

		fotografoService.salva(fotografo1);
		fotografoService.salva(fotografo2);
		albumService.salva(album1);
		albumService.salva(album2);
		albumService.salva(album3);
		albumService.salva(album4);


		if( action.equals("visitatore") )
			return "scelta.html";
		else
			return "";
	}

	@RequestMapping(value="/fotografi", method=RequestMethod.GET)	
	public String fotografi(Model model) {
		model.addAttribute("fotografi", fotografoService.tutti());
		return "fotografi.html";
	}


	@RequestMapping(value="/album", method=RequestMethod.GET)	
	public String album(Model model) {
		model.addAttribute("albums", albumService.tutti());
		return "albums.html";
	}
}
