package it.uniroma3.siw.demospring.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.uniroma3.siw.demospring.model.Album;

@Component
public class Service {
	
	public List<Album> albumDiArtista(List<Album> albums, Long id) {
		ArrayList<Album> albumsDiArtista=new ArrayList<>();
		for (Album album : albums) {
			if(album.getFotografo().getId()==id)
				albumsDiArtista.add(album);
		}
		
		return albumsDiArtista;
	}
}
