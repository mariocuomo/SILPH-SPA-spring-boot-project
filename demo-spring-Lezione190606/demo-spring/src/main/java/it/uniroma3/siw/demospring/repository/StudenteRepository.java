package it.uniroma3.siw.demospring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import it.uniroma3.siw.demospring.model.Studente;

public interface StudenteRepository extends CrudRepository<Studente, Long>{
	//findByDatiDaRecuperare(sarebbero le variabili)
	public List<Studente> findByNome(String nome);
	public List<Studente> findByNomeAndCognome(String nome, String cognome);
}
