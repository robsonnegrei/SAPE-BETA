package com.quixada.sme.sape.config;


import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	//@Autowired
	//DataSource dataSource;
	//configuração de login 
	//verifica os dados contidos no banco
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("usuario").password("senha").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("jooj").password("12345").roles("PCLEI");
		auth.inMemoryAuthentication().withUser("user").password("password").roles("ADMIN");
//	  auth.jdbcAuthentication().dataSource(dataSource)
//		.usersByUsernameQuery(
//			"select username,senha, enabled from users where username=?")
//		.authoritiesByUsernameQuery(
//			"select username, role from user_roles where username=?");
	}	
	//aqui ele seta as views de acordo com usuario e senha, mostra página de erro
	//seta a página de login que contém o form de acesso e passa os parâmetros
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	//csrf desabilitado por enquanto
	 http.csrf().disable();
	 http
		 .authorizeRequests()
		 .antMatchers("/admin/**").access("hasRole('ADMIN')")
		 .antMatchers("/**", "/css/**", "/js/**","/img/**","/bootstrap/**").permitAll()
         .anyRequest().authenticated()
         .and()
     .formLogin()
         .loginPage("/login").failureUrl("/login?error").defaultSuccessUrl("/admin")
         .usernameParameter("username").passwordParameter("password")
         .permitAll()
         .and()
     .logout().logoutSuccessUrl("/login?logout") 
        .and()
     .exceptionHandling().accessDeniedPage("/403");
		
	}
	
}
