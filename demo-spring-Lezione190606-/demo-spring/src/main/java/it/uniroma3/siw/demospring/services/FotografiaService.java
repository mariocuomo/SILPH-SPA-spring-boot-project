package it.uniroma3.siw.demospring.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.demospring.model.Fotografia;
import it.uniroma3.siw.demospring.repository.FotografiaRepository;

@Service
public class FotografiaService {

	@Autowired
	FotografiaRepository fotografiaRepository;

	@Transactional	
	public Fotografia salva(Fotografia fotografia) {
		return fotografiaRepository.save(fotografia);	
	}

	@Transactional
	public List<Fotografia> tutti() {
		return (List<Fotografia>)fotografiaRepository.findAll();
	}

	@Transactional
	public Fotografia FotografiaPerId(Long id) {
		return fotografiaRepository.findById(id).get();
	}
}