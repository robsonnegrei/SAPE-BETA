package com.quixada.sme.sape.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;

//Configuracao da engine do thymeleaf
@Configuration
public class ThymeleafExtension {
	
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	@PostConstruct
	public void extension(){
		FileTemplateResolver resolver = new FileTemplateResolver();
		resolver.setPrefix("classpath:/templates");
		resolver.setSuffix(".html");
		resolver.setTemplateMode("HTML5");
		resolver.setOrder(templateEngine.getTemplateResolvers().size());
		resolver.setCacheable(false);
		templateEngine.addTemplateResolver(resolver);
		//templateEngine.addDialect(new SpringSecurityDialect());
	}
}
