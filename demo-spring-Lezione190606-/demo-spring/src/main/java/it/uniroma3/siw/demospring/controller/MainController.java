package it.uniroma3.siw.demospring.controller;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
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
import it.uniroma3.siw.demospring.model.User;
import it.uniroma3.siw.demospring.services.AlbumService;
import it.uniroma3.siw.demospring.services.AlbumValidator;
import it.uniroma3.siw.demospring.services.FotografoService;
import it.uniroma3.siw.demospring.services.FotografoValidator;
import it.uniroma3.siw.demospring.services.StudenteService;
import it.uniroma3.siw.demospring.services.StudenteValidator;
import it.uniroma3.siw.demospring.services.UserService;
//import it.uniroma3.siw.demospring.services.UserService;
import it.uniroma3.siw.demospring.services.Service;


@Controller
public class MainController {

	@Autowired
	FotografoService fotografoService;
	@Autowired
	FotografoValidator fotografoValidator;


	@Autowired
	AlbumService albumService;
	@Autowired
	AlbumValidator albumValidator;

	@Autowired
	UserService userService;

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

		User user1 = new User();
		user1.setUsername("mario");
		user1.setPassword("mariopassword");

		User user2 = new User();
		user2.setUsername("francesca");
		user2.setPassword("francescapassword");

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
		userService.salva(user1);
		userService.salva(user2);


		if( action.equals("visitatore") )
			return "scelta.html";
		else {
			model.addAttribute("user",new User());
			return "login.html";
		}
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

	@RequestMapping(value="/autorizzato", method= {RequestMethod.GET, RequestMethod.POST})	
	public String autorizzato(Model model) {
		return "amministratore.html";
	}

	@RequestMapping(value="/salvaFotografo", method= {RequestMethod.GET, RequestMethod.POST})	
	public String IntentoaggiungiFotografo(Model model) {
		model.addAttribute("fotografo",new Fotografo());
		return "fotografoForm.html";
	}

	@RequestMapping(value="/addFotografo", method= {RequestMethod.GET, RequestMethod.POST})	
	public String aggiungiFotografo(Model model,@Valid @ModelAttribute Fotografo fotografo, BindingResult bindingResult) {
		fotografoValidator.validate(fotografo, bindingResult);
		if(bindingResult.hasErrors())
			return "fotografoForm.html";
		else {
			fotografoService.salva(fotografo);
			return "";
		}
	}

	@RequestMapping(value="/salvaAlbum", method= {RequestMethod.GET, RequestMethod.POST})	
	public String IntentoaggiungiAlbum(Model model) {
		model.addAttribute("fotografi", fotografoService.tutti());
		model.addAttribute("album",new Album());
		return "albumForm.html";
	}

	@RequestMapping(value="/addAlbum", method= RequestMethod.POST)	
	public String aggiungiAlbum(Model model,@Valid @ModelAttribute Album album, BindingResult bindingResult) {
		albumValidator.validate(album, bindingResult);
		if(bindingResult.hasErrors()) {
			model.addAttribute("fotografi", fotografoService.tutti());
			return "albumForm.html";
		}
		else {
			Long id = Long.valueOf(album.getIdFotografo()).longValue();
			Fotografo fotografo;
			fotografo= fotografoService.FotografoPerId(id);
			album.setFotografo(fotografo);
			int a =0;
			album.setIdFotografo("");
			albumService.salva(album);
			return "fineOperazione.html";
		}
	}

}


