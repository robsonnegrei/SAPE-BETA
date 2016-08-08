package com.quixada.sme.sape.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	DataSource dataSource;
	@Autowired
	CustomAuthenticationSuccessHandler handler;
	
	//configuração de login 
	//verifica os dados contidos no banco
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("usuario").password("senha").roles("ADMIN");
//		auth.inMemoryAuthentication().withUser("jooj").password("12345").roles("PCLEI");
//		auth.inMemoryAuthentication().withUser("user").password("password").roles("ADMIN");
		
	  auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery(
			"select email,senha,1 from usuario where email=?")
		.authoritiesByUsernameQuery(
			"select usuario.email, usuario_funcao.funcao from usuario join usuario_funcao on usuario.idUsuario = usuario_funcao.idUsuario where usuario.email=?");
	  	//Query de funcao testada no SQL server 5.6
	}	
	//aqui ele seta as views de acordo com usuario e senha, mostra página de erro
	//seta a página de login que contém o form de acesso e passa os parâmetros
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	//csrf desabilitado por enquanto
	 http.csrf().disable();
	 http
		 .authorizeRequests()
		 .antMatchers("/admin/**").hasAuthority("ADMIN")
		 .antMatchers("/portfolio/**").hasAnyAuthority("ADMIN","PCLEI")
		 .antMatchers("/css/**", "/js/**","/img/**","/bootstrap/**","/public/**").permitAll()
         .anyRequest().authenticated()
         .and()
     .formLogin()
         .loginPage("/login").failureUrl("/login?error").successHandler(handler)
         .usernameParameter("username").passwordParameter("password")
         .permitAll()
         .and()
     .logout().logoutSuccessUrl("/login?logout") 
        .and()
     .exceptionHandling().accessDeniedPage("/403");
		
	}
	
}
