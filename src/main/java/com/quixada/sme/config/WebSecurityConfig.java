package com.quixada.sme.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	//injeção do datasource que foi feito na classe MvcConfig
	@Autowired
	DataSource dataSource;
	
	//configuração de login 
	//verifica os dados contidos no banco
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		
	  auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery(
			"select username,senha, enabled from users where username=?")
		.authoritiesByUsernameQuery(
			"select username, role from user_roles where username=?");
	}	
	//aqui ele seta as views de acordo com usuario e senha, mostra página de erro
	//seta a página de login que contém o form de acesso e passa os parâmetros
	//a página /hello só é setada caso o usuario seja do tipo administrador
	//caso seu usuário e senha estejam errados, ele seta um erro 
	@Override
	protected void configure(HttpSecurity http) throws Exception {

	  http.authorizeRequests()
		.antMatchers("/hello").access("hasRole('ROLE_ADMIN')")		
		.anyRequest().permitAll()
		.and()
		  .formLogin().loginPage("/login")
		  .usernameParameter("username").passwordParameter("senha")
		.and()
		  .logout().logoutSuccessUrl("/login?logout")	
		 .and()
		 .exceptionHandling().accessDeniedPage("/403")
		.and()
		  .csrf();
	}
	
}
