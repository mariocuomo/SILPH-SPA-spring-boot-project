package it.uniroma3.siw.demospring.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.demospring.model.Fotografo;
import it.uniroma3.siw.demospring.repository.FotografoRepository;

@Service
public class FotografoService {

	@Autowired
	FotografoRepository fotografoRepository;
	
	@Transactional
	public Fotografo salva(Fotografo fotografo) {
		return fotografoRepository.save(fotografo);
	}
	
	@Transactional
	public List<Fotografo> tutti() {
		return (List<Fotografo>)fotografoRepository.findAll();
	}
	
	@Transactional
	public Fotografo FotografoPerId(Long id) {
		return fotografoRepository.findById(id).get();
	}
	
	
}