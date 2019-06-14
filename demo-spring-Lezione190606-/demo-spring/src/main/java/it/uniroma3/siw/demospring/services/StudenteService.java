package it.uniroma3.siw.demospring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.demospring.model.Studente;
import it.uniroma3.siw.demospring.repository.StudenteRepository;

@Service
public class StudenteService {
	
	/*Significato Autowired
	 * crea un instanza di questa classe 
	 * e assegnalo alla variabile che ti ho indicato
	 * (fa tutto da solo)
	 * */
	@Autowired 
	private StudenteRepository studenteRepository;
	
	@Transactional 
	public Studente inserisci(Studente studente) {
		return studenteRepository.save(studente);
	}
	
	@Transactional
	public List<Studente> tutti(){
		return (List<Studente>) studenteRepository.findAll();
	}

	public Studente studentePerId(Long id) {
		return this.studenteRepository.findById(id).get();
	}
	
	
	
	
}
