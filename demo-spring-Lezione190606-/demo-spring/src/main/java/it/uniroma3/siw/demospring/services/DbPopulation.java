package it.uniroma3.siw.demospring.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.demospring.model.Album;
import it.uniroma3.siw.demospring.model.Fotografia;
import it.uniroma3.siw.demospring.model.Fotografo;
import it.uniroma3.siw.demospring.model.User;


@Component
public class DbPopulation implements ApplicationRunner {
	@Autowired
	FotografoService fotografoService;
	@Autowired
	AlbumService albumService;
	@Autowired
	FotografiaService fotografiaService;

	@Autowired
	UserService userService;

	private void populateDB() throws IOException, InterruptedException {		User user1 = new User();
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
		Fotografia fotografia5 = new Fotografia();
		fotografia5.setNome("lilium");
		fotografia5.setDescrizione("lilium roseo");
		fotografia5.setFotografo(fotografo2);
		fotografia5.setAlbum(album3);
		fotografia5.setLink("https://bulbishop.it/wp-content/uploads/2015/06/Bulbi-Lilium-Stargazer-01.jpg");
		Fotografia fotografia6 = new Fotografia();
		fotografia6.setNome("margherite");
		fotografia6.setDescrizione("due margherite");
		fotografia6.setFotografo(fotografo2);
		fotografia6.setAlbum(album3);
		fotografia6.setLink("https://www.ilgiardinodegliilluminati.it/wp-content/uploads/2019/02/margherita-significato-e-simbologia.jpg");
		Fotografia fotografia7 = new Fotografia();
		fotografia7.setNome("torino");
		fotografia7.setDescrizione("sullo sfondo la Mole Antonelliana");
		fotografia7.setFotografo(fotografo2);
		fotografia7.setAlbum(album4);
		fotografia7.setLink("https://www.fotocomefare.com/wp-content/uploads/fotografia-in-viaggio-di-sera.jpg");
		Fotografia fotografia8 = new Fotografia();
		fotografia8.setNome("traffico");
		fotografia8.setDescrizione("la città soffocata dal traffico");
		fotografia8.setFotografo(fotografo2);
		fotografia8.setAlbum(album4);
		fotografia8.setLink("https://www.avvenire.it/c/2017/PublishingImages/f1404a31e7964b9884aef80ef8a75b43/traffico_56809300.jpg");
		
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
		fotografiaService.salva(fotografia5);
		fotografiaService.salva(fotografia6);
		fotografiaService.salva(fotografia7);
		fotografiaService.salva(fotografia8);
		userService.salva(user1);
		userService.salva(user2);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
        this.populateDB();		
	}
}
