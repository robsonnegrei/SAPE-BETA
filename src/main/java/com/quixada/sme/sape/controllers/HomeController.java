package com.quixada.sme.sape.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	//slf4j
	private static Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value ={ "/","/home"})
	public String index(HttpServletRequest request){
		HttpSession session = request.getSession();
		return "home";
	}
	@RequestMapping(value = "/login",  method = { RequestMethod.GET, RequestMethod.POST })
	public String login(HttpServletRequest request, Model model){
		if(request.getMethod().toLowerCase().equals("post")){
			LOGGER.debug("Request com Post recebida!");
		}
//		HttpSession session = request.getSession();
//		//Sem sessão, manda pro login
//		if (session.getAttribute("usuario") == null) {
//			return "/";
//		}
		
		return "login";
	}
	
	 // Error page
//	  @RequestMapping("/error.html")
//	  public String error(HttpServletRequest request, Model model) {
//	    model.addAttribute("errorCode", request.getAttribute("javax.servlet.error.status_code"));
//	    Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
//	    String errorMessage = null;
//	    if (throwable != null) {
//	      errorMessage = throwable.getMessage();
//	    }
//	    model.addAttribute("errorMessage", errorMessage);
//	    return "error.html";
//	  }

}
