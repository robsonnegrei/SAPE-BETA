package com.quixada.sme.sape.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RelatorioController {
	@RequestMapping(value={"PCLei/getRelatorio","/getRelatorio"})
	public String getRelatorioAlunos(HttpServletRequest request){
		
		
		
		return "PCLei/pagResultadoAvaliacao";
		
	}

}
