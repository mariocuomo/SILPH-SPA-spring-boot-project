package it.uniroma3.siw.demospring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/css/**").permitAll()
		.antMatchers("/autorizzato", "/salvaFotografo", 
				"/addFotografo", "/salvaAlbum", 
				"/addAlbum", "/menuAmministratore",
				"/salvaFotografia","/fotografoUploadFoto/*",
				"/albumUploadFoto/*","/upload").authenticated()
		.and()
		.formLogin().defaultSuccessUrl("/autorizzato", true)
		//.loginPage("/login").permitAll()
		.and()
		.logout().permitAll()
		.and()
		.csrf().disable();
	}

	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
}
