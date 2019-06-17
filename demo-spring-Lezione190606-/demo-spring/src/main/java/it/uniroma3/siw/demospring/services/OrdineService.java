package it.uniroma3.siw.demospring.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.demospring.model.Fotografo;
import it.uniroma3.siw.demospring.model.Ordine;
import it.uniroma3.siw.demospring.repository.FotografoRepository;
import it.uniroma3.siw.demospring.repository.OrdineRepository;

@Service
public class OrdineService {

	@Autowired
	OrdineRepository ordineRepository;
	
	@Transactional
	public Ordine salva(Ordine ordine) {
		return ordineRepository.save(ordine);
	}
	
	@Transactional
	public List<Ordine> tutti() {
		return (List<Ordine>)ordineRepository.findAll();
	}
	
	@Transactional
	public Ordine OrdinePerId(Long id) {
		return ordineRepository.findById(id).get();
	}
	
	
}