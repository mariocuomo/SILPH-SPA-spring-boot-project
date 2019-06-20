package it.uniroma3.siw.demospring.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;

import it.uniroma3.siw.demospring.model.Album;
import it.uniroma3.siw.demospring.model.Fotografia;
import it.uniroma3.siw.demospring.model.Fotografo;
import it.uniroma3.siw.demospring.model.Ordine;
import it.uniroma3.siw.demospring.model.RigaOrdine;
import it.uniroma3.siw.demospring.model.User;
import it.uniroma3.siw.demospring.services.AlbumService;
import it.uniroma3.siw.demospring.services.AlbumValidator;
import it.uniroma3.siw.demospring.services.FotografiaService;
import it.uniroma3.siw.demospring.services.FotografiaValidator;
import it.uniroma3.siw.demospring.services.FotografoService;
import it.uniroma3.siw.demospring.services.FotografoValidator;
import it.uniroma3.siw.demospring.services.OrdineService;
import it.uniroma3.siw.demospring.services.OrdineValidator;
import it.uniroma3.siw.demospring.services.UserService;
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


	@Autowired
	private AmazonS3 amazonS3Client;



	private void uploadFileToS3bucket(String bucketName, File file, String fileName) {
		amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
	}


	@Bean
	public AmazonS3 amazonS3Client(AWSCredentialsProvider credentialsProvider,
			@Value("${cloud.aws.region.static}") String region) {
		return AmazonS3ClientBuilder
				.standard()
				.withCredentials(credentialsProvider)
				.withRegion(Regions.EU_CENTRAL_1)
				.build();
	}

	@RequestMapping(value = "fotografo/{id}", method = RequestMethod.GET)
	public String getFotografo(@PathVariable ("id") Long id, Model model) {
		if(id!=null) {			
			List<Album> albums = service.albumDiArtista(albumService.tutti(), id);
			if(albums.size()==0) {
				Fotografo fotografo = fotografoService.FotografoPerId(id);
				model.addAttribute("fotografo",fotografo);
				return "noAlbum.html";
			}
			model.addAttribute("fotografo", this.fotografoService.FotografoPerId(id));
			model.addAttribute("albums", albums);
			return "fotografo.html";
		}else {
			model.addAttribute("fotografi", this.fotografoService.tutti());
			return "fotografi.html";
		}
	}


	@RequestMapping(value="/entra", method=RequestMethod.GET)
	public String entra(Model model, @RequestParam(defaultValue = "nulla") String action) throws MessagingException {
		String prossimaVista="index.html";
		if( action.equals("visitatore") )
			prossimaVista = "scelta.html";
		if( action.equals("admin")) {
			model.addAttribute("user",new User());
			prossimaVista = "login.html";
		}

		return prossimaVista;
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
		String[] arrayDiFoto = request.getParameterValues("images");
		if(arrayDiFoto == null) {
			model.addAttribute("fotografie", fotografiaService.tutti());
			model.addAttribute("errore","Non hai selezionato nessuna foto!");
			return "fotografie.html";
		}
		Long[] arrayDiFotoLongs = service.convertiStringInLong(arrayDiFoto);
		selezionate = service.fotografieSelezionate(fotografiaService.tutti(), arrayDiFotoLongs);
		model.addAttribute("fotografie", selezionate);
		model.addAttribute("ordine", new Ordine());
		return "aggiungiInfoOrdine.html";
	}

	@RequestMapping(value="/completa", method= RequestMethod.POST)	
	public String completaAcquisto(Model model,@Valid @ModelAttribute Ordine ordine, BindingResult bindingResult) {
		ordineValidator.validate(ordine, bindingResult);
		if(bindingResult.hasErrors()) {
			model.addAttribute("fotografie", selezionate);
			return "aggiungiInfoOrdine.html";
		}
		else {
			if(!EmailValidator.getInstance().isValid(ordine.getEmail()))
			{
				model.addAttribute("erroreEmail", "Email non valida");
				model.addAttribute("fotografie", selezionate);
				return "aggiungiInfoOrdine.html";
			}
			else {
				List<RigaOrdine> righeOrdine = service.creaRigheOrdine(selezionate);
				ordine.setRigheOrdine(righeOrdine);
				ordineService.salva(ordine);
				try {
					service.inviaMail(ordine);
				} catch (MessagingException e) {
					return "fineOrdine";
				}
				model.addAttribute("email", ordine.getEmail());
				return "fineOrdine";

			}
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
			model.addAttribute("fotografi", this.fotografoService.tutti());
			return "albums.html";
		}
	}

	@RequestMapping(value = "esci", method = RequestMethod.GET)
	public String esci(Model model) {
		return "index.html";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadFoto(@Valid @ModelAttribute Fotografia fotografia, 
			Model model, BindingResult bindingResult, 
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request) {
		fotografiaValidator.validate(fotografia, bindingResult);
		if(bindingResult.hasErrors()) {
			return "uploadFoto.html";
		}
		else {
			fotografia.setFotografo(this.fotografia.getFotografo());
			fotografia.setAlbum(this.fotografia.getAlbum());

			File convFile = null;
			try {
				convFile = this.convertMultiPartToFile(file);
			} catch (IOException e) {
				return "erroreCaricamento.html";
			}

			this.uploadFileToS3bucket("it.siw.uniroma3.cuomo", convFile, fotografia.getNome()+".jpg");
			String link=amazonS3Client.getUrl("it.siw.uniroma3.cuomo", fotografia.getNome()+".jpg").toString();
			fotografia.setLink(link);

			fotografiaService.salva(fotografia);

			return "fineOperazione.html";

		}
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}
}


