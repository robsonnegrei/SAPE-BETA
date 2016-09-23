package com.quixada.sme.sape.config;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.quixada.sme.dao.UsuarioDAO;
import com.quixada.sme.model.Usuario;

@Component
@ComponentScan(value={"com.quixada.sme.dao","com.quixada.sme.model"})
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private static Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);
	
	@Autowired
	private UsuarioDAO uDao;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
			HttpSession session = request.getSession();
			String email = (String) SecurityContextHolder.getContext().getAuthentication().getName();
			session.setAttribute("versao", "1.1.0-BETA");
			try {
				Usuario usuarioAutenticado = uDao.buscar(email);
				//Aqui seta o usuario com suas propriedades
				session.setAttribute("usuario", usuarioAutenticado);
				logger.info(usuarioAutenticado.getEmail() + " se conectou!");
			} catch (SQLException e) {
				logger.error("Erro ao autenticar usuario" + e.getMessage() );
			}
			
			RequestCache rc = new HttpSessionRequestCache();
			SavedRequest savedRequest = rc.getRequest(request, response);
			
			try{
				Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
				if (roles.contains(new SimpleGrantedAuthority("ADMIN"))) {
					response.sendRedirect("/admin");
					return;
				}
				if (roles.contains(new SimpleGrantedAuthority("PCLEI"))) {
					response.sendRedirect("/pclei");
					return;
				}
				response.sendRedirect(savedRequest.getRedirectUrl());
			}
			catch(NullPointerException e){
				logger.error("Erro ao redirecionar apos login : " + e.getMessage() + " - redirecionando para [/]");
				response.sendRedirect("/");
			}
	}
}
