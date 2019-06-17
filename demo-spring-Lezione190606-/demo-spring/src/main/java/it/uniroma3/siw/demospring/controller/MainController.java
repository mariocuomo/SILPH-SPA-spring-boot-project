package it.uniroma3.siw.demospring.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
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
import it.uniroma3.siw.demospring.model.Fotografia;
import it.uniroma3.siw.demospring.model.Fotografo;
import it.uniroma3.siw.demospring.model.Ordine;
import it.uniroma3.siw.demospring.model.RigaOrdine;
import it.uniroma3.siw.demospring.model.Studente;
import it.uniroma3.siw.demospring.model.User;
import it.uniroma3.siw.demospring.services.AlbumService;
import it.uniroma3.siw.demospring.services.AlbumValidator;
import it.uniroma3.siw.demospring.services.FotografiaService;
import it.uniroma3.siw.demospring.services.FotografiaValidator;
import it.uniroma3.siw.demospring.services.FotografoService;
import it.uniroma3.siw.demospring.services.FotografoValidator;
import it.uniroma3.siw.demospring.services.OrdineService;
import it.uniroma3.siw.demospring.services.OrdineValidator;
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

	private Fotografia fotografia;
	@Autowired
	FotografiaValidator fotografiaValidator;
	@Autowired
	FotografiaService fotografiaService;

	@Autowired
	OrdineValidator ordineValidator;
	@Autowired
	OrdineService ordineService;
	List<Fotografia> selezionate;

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
	public String entra(Model model, @RequestParam(defaultValue = "nulla") String action) {
		if( action.equals("nulla") )
			return "index.html";
		
		if( action.equals("visitatore") )
			return "scelta.html";
		else if( action.equals("admin")) {
			model.addAttribute("user",new User());
			return "login.html";
		}else {
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
			album1.setNome("raggisolari");
			album1.setDescrizione("una raccolta di foto raggianti");
			Album album2 = new Album();
			album2.setNome("altamarea");
			album2.setDescrizione("l'altamarea come l'umore");
			album1.setFotografo(fotografo1);
			album2.setFotografo(fotografo1);

			Album album3 = new Album();
			album3.setNome("tuttoinfiore");
			album3.setDescrizione("fiori di campagna");
			Album album4 = new Album();
			album4.setNome("rumorecittadino");
			album4.setDescrizione("linea tra silenzio e rumore");
			album3.setFotografo(fotografo2);
			album4.setFotografo(fotografo2);


			Fotografia fotografia1 = new Fotografia();
			fotografia1.setNome("raggioincollina");
			fotografia1.setDescrizione("un raggio raggiante");
			fotografia1.setFotografo(fotografo1);
			fotografia1.setAlbum(album1);
			fotografia1.setLink("https://i0.wp.com/fondazionenenni.blog/wp-content/uploads/2019/04/raggio-di-sole.jpg");
			Fotografia fotografia2 = new Fotografia();
			fotografia2.setNome("raggioinforesta");
			fotografia2.setDescrizione("un raggio raggiante 2");
			fotografia2.setFotografo(fotografo1);
			fotografia2.setAlbum(album1);
			fotografia2.setLink("https://cdn.pixabay.com/photo/2017/11/10/10/34/forest-2935923_960_720.jpg");
			Fotografia fotografia3 = new Fotografia();
			fotografia3.setNome("mareaescogli");
			fotografia3.setDescrizione("onda che si infrange");
			fotografia3.setFotografo(fotografo1);
			fotografia3.setAlbum(album2);
			fotografia3.setLink("http://www.passionepesce.it/wp-content/uploads/sites/3/2013/05/Alta-marea-a23728111.jpg");
			Fotografia fotografia4 = new Fotografia();
			fotografia4.setNome("saintMichel");
			fotografia4.setDescrizione("la calma prima della tempesta");
			fotografia4.setFotografo(fotografo1);
			fotografia4.setAlbum(album2);
			fotografia4.setLink("https://www.presidentformaggi.it/sites/default/files/styles/immagine_gallery/public/Mont%20Saint%20Michelle%20e%20le%20sue%20Specialita_V2mod.jpg");

			fotografoService.salva(fotografo1);
			fotografoService.salva(fotografo2);
			albumService.salva(album1);
			albumService.salva(album2);
			albumService.salva(album3);
			albumService.salva(album4);
			fotografiaService.salva(fotografia1);
			fotografiaService.salva(fotografia2);
			fotografiaService.salva(fotografia3);
			fotografiaService.salva(fotografia4);

			userService.salva(user1);
			userService.salva(user2);
			return "index.html";
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
	
	@RequestMapping(value = "album/{id}", method = RequestMethod.GET)
	public String getAlbum(@PathVariable ("id") Long id, Model model) {
		if(id!=null) {	
			model.addAttribute("album", this.albumService.AlbumPerId(id));
			model.addAttribute("fotografo", this.albumService.AlbumPerId(id).getFotografo());
			model.addAttribute("fotografie", this.albumService.AlbumPerId(id).getFotografie());
			return "album.html";
		}else {
			model.addAttribute("album", albumService.tutti());
			return "albums.html";
		}
	}

	@RequestMapping(value="/acquistafotografie", method= {RequestMethod.GET, RequestMethod.POST})	
	public String intentoAcquistaFotografie(Model model) {
		model.addAttribute("fotografie", fotografiaService.tutti());
		return "fotografie.html";
	}

	@RequestMapping(value="/acquista", method= RequestMethod.GET)	
	public String aggiungiInfo(Model model, HttpServletRequest request) {
		model.addAttribute("ordine", new Ordine());
		String[] arrayDiFoto = request.getParameterValues("images");
		Long[] arrayDiFotoLongs = service.convertiStringInLong(arrayDiFoto);
		selezionate = service.fotografieSelezionate(fotografiaService.tutti(), arrayDiFotoLongs);
		model.addAttribute("fotografie", selezionate);
		return "aggiungiInfoOrdine.html";
	}

	@RequestMapping(value="/completa", method= RequestMethod.POST)	
	public String completaAcquisto(Model model,@Valid @ModelAttribute Ordine ordine, BindingResult bindingResult) {
		ordineValidator.validate(ordine, bindingResult);
		if(bindingResult.hasErrors()) {
			model.addAttribute("ordine", new Ordine());
			model.addAttribute("fotografie", selezionate);
			return "aggiungiInfoOrdine.html";
		}
		else {
			List<RigaOrdine> righeOrdine = service.creaRigheOrdine(selezionate);
			ordine.setRigheOrdine(righeOrdine);
			ordineService.salva(ordine);
			return "fineOrdine";
		}
	}

	@RequestMapping(value="/autorizzato", method= {RequestMethod.GET, RequestMethod.POST})	
	public String autorizzato(Model model) {
		return "amministratore.html";
	}

	
	
	@RequestMapping(value="/visualizzaRichieste", method= {RequestMethod.GET, RequestMethod.POST})	
	public String tutteLeRichieste(Model model) {
		List<Ordine> ordines = ordineService.tutti();
		model.addAttribute("ordini",ordines);
		return "ordini.html";
	}
	
	@RequestMapping(value = "ordine/{id}", method = RequestMethod.GET)
	public String getOrdine(@PathVariable ("id") Long id, Model model) {
		if(id!=null) {	
			ArrayList<Fotografia> ordinate;
			ordinate = service.fotografieOrdinate(this.ordineService.OrdinePerId(id).getRigheOrdine());
			model.addAttribute("ordine", this.ordineService.OrdinePerId(id));
			model.addAttribute("fotografie", ordinate);
			return "ordine.html";
		}else {
			model.addAttribute("ordini", ordineService.tutti());
			return "ordini.html";
		}
	}
	
	@RequestMapping(value="/salvaFotografo", method= {RequestMethod.GET, RequestMethod.POST})	
	public String intentoaggiungiFotografo(Model model) {
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
			return "fineOperazione.html";
		}
	}

	@RequestMapping(value="/salvaAlbum", method= {RequestMethod.GET, RequestMethod.POST})	
	public String intentoaggiungiAlbum(Model model) {
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
			album.setIdFotografo("");
			albumService.salva(album);
			return "fineOperazione.html";
		}
	}

	@RequestMapping(value="/menuAmministratore", method= {RequestMethod.GET, RequestMethod.POST})	
	public String fineOperazione(Model model) {
		return "amministratore.html";
	}

	@RequestMapping(value="/salvaFotografia", method= {RequestMethod.GET, RequestMethod.POST})	
	public String intentosalvaFotografia(Model model) {
		model.addAttribute("fotografi", fotografoService.tutti());
		return "fotografoUploadFoto.html";
	}

	@RequestMapping(value = "fotografoUploadFoto/{id}", method = RequestMethod.GET)
	public String fotografoUploadFoto(@PathVariable ("id") Long id, Model model) {
		if(id!=null) {
			this.fotografia = new Fotografia();
			Fotografo fotografo = this.fotografoService.FotografoPerId(id);
			fotografia.setFotografo(fotografo);
			List<Album> albums = service.albumDiArtista(albumService.tutti(), id);
			model.addAttribute("fotografo", fotografo);
			model.addAttribute("albums", albums);
			return "albumUploadFoto.html";
		}else {
			//damodificare
			model.addAttribute("fotografi", this.fotografoService.tutti());
			return "fotografi.html";
		}
	}

	@RequestMapping(value = "albumUploadFoto/{id}", method = RequestMethod.GET)
	public String albumUploadFoto(@PathVariable ("id") Long id, Model model) {
		if(id!=null) {
			fotografia.setAlbum(albumService.AlbumPerId(id));
			model.addAttribute("fotografia",fotografia);
			return "uploadFoto.html";
		}else {
			//damodificare
			model.addAttribute("fotografi", this.fotografoService.tutti());
			return "albums.html";
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadFoto(@Valid @ModelAttribute Fotografia fotografia, Model model, BindingResult bindingResult) {
		fotografiaValidator.validate(fotografia, bindingResult);
		if(bindingResult.hasErrors()) {
			return "uploadFoto.html";
		}
		else {
			fotografia.setFotografo(this.fotografia.getFotografo());
			fotografia.setAlbum(this.fotografia.getAlbum());
			fotografia.setLink("https://i1.wp.com/www.cybercloud.guru/wp-content/uploads/2018/03/s3.png");
			fotografiaService.salva(fotografia);

			/****codice per salvare su storage****/
			return "fineOperazione.html";
		}
	}
}


