package com.quixada.sme.sape.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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

	  auth.inMemoryAuthentication().withUser("visitante@sape.com").password("").roles("VISITANTE");
	  //Geral
	  auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery(
			"select email,senha,1 from usuario where email=?")
		.authoritiesByUsernameQuery(
			"select usuario.email, usuario_funcao.funcao from usuario join usuario_funcao on usuario.idUsuario = usuario_funcao.idUsuario where usuario.email=?");
	  	//Query testada no SQL server 5.6
	  
	}	
	
	//aqui ele seta as views de acordo com usuario e senha, mostra página de erro
	//seta a página de login que contém o form de acesso e passa os parâmetros
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	//csrf desabilitado por enquanto
	 http.csrf().disable();
	 http
		 .authorizeRequests()
		 .antMatchers("/PCLei/gerarRelatorio","PCLei/pagRelatorio").hasAuthority("VISITANTE")
		 .antMatchers("/admin/**").hasAuthority("ADMIN")
		 .antMatchers("/PCLei/**").hasAnyAuthority("PCLEI", "ADMIN")
		 .antMatchers("/portfolio/**").hasAnyAuthority("ADMIN","PCLEI","VISITANTE")
		 .antMatchers("/visitar").permitAll()
		 .antMatchers("/").permitAll()
		 .antMatchers("/css/**", "/js/**","/img/**","/bootstrap/**","/public/**").permitAll()
         .and()
     .exceptionHandling().accessDeniedPage("/403")
        .and()
     .formLogin()
         .loginPage("/login").failureUrl("/login?error").successHandler(handler)
         .usernameParameter("username").passwordParameter("password")
         .permitAll()
         .and()
     .logout().logoutSuccessUrl("/login?logout");
	 
       
	}
	
}
