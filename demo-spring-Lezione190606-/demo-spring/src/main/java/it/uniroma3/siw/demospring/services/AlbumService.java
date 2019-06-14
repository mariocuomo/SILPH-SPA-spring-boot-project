package it.uniroma3.siw.demospring.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.demospring.model.Album;
import it.uniroma3.siw.demospring.model.Fotografia;
import it.uniroma3.siw.demospring.repository.AlbumRepository;
import it.uniroma3.siw.demospring.repository.FotografiaRepository;

@Service
public class AlbumService{

	@Autowired
	AlbumRepository albumRepository;

	@Transactional	
	public Album salva(Album album) {
		return albumRepository.save(album);	
	}

	@Transactional
	public List<Album> tutti() {
		return (List<Album>)albumRepository.findAll();
	}

	@Transactional
	public Album AlbumPerId(Long id) {
		return albumRepository.findById(id).get();
	}
}
