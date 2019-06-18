package it.uniroma3.siw.demospring.services;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.demospring.model.Ordine;

@Component
public class OrdineValidator  implements Validator{

	@Override
	public boolean supports(Class<?> aclass) {
		// TODO Auto-generated method stub
		return Ordine.class.equals(aclass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required");
	}

}
