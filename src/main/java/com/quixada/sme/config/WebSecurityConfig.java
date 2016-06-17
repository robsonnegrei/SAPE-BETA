package com.quixada.sme.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.quixada.sme.dao.UsuarioDAO;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	//injeção do datasource que foi feito na classe MvcConfig
	@Autowired
	private UsuarioDAO uDAO; 
	
	//configuração de login 
	//verifica os dados contidos no banco
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("usuario").password("senha").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("jooj").password("12345").roles("USER");
		
//	  auth.jdbcAuthentication().dataSource(dataSource)
//		.usersByUsernameQuery(
//			"select username,senha, enabled from users where username=?")
//		.authoritiesByUsernameQuery(
//			"select username, role from user_roles where username=?");
	}	
	//aqui ele seta as views de acordo com usuario e senha, mostra página de erro
	//seta a página de login que contém o form de acesso e passa os parâmetros
	//a página /hello só é setada caso o usuario seja do tipo administrador
	//caso seu usuário e senha estejam errados, ele seta um erro 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
	        .anyRequest().authenticated() 
	        .and()
	    .formLogin()
	    	.loginPage("/login")
	        .and()
	    .httpBasic();
	}
	
}
