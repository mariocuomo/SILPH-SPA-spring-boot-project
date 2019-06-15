package it.uniroma3.siw.demospring.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.demospring.model.User;

public interface UserRepository extends CrudRepository<User, Long>{

	public User findByUsername(String username);
	
}
