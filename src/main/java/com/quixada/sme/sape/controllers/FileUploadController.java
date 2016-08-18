package com.quixada.sme.sape.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.quixada.sme.dao.ImagemDAO;
import com.quixada.sme.model.MetaImagem;
import com.quixada.sme.model.Usuario;

@RestController
@RequestMapping(value = {"/portfolio/controller"} )
@ComponentScan(value={"com.quixada.sme.dao"})
public class FileUploadController {

	@Autowired
	private ImagemDAO picDAO; 
	
	LinkedList<MetaImagem> files = new LinkedList<MetaImagem>();
	MetaImagem imagemMeta = null;
	/***************************************************
	 * URL: /rest/controller/upload  
	 * upload(): receives files
	 * @param request : MultipartHttpServletRequest auto passed
	 * @param response : HttpServletResponse auto passed
	 * @return LinkedList<FileMeta> as json format
	 ****************************************************/
	@RequestMapping(value="/upload", method = RequestMethod.POST)
	public @ResponseBody LinkedList<MetaImagem> upload(MultipartHttpServletRequest request, HttpServletResponse response) {
 
		//1. build an iterator
		 Iterator<String> itr =  request.getFileNames();
		 MultipartFile mpf = null;

		 //2. get each file
		 while(itr.hasNext()){
			 
			 //2.1 get next MultipartFile
			 mpf = request.getFile(itr.next()); 
			 System.out.println("Arquivo " + mpf.getOriginalFilename() +" enviado! "+files.size() + " usr: " + SecurityContextHolder.getContext().getAuthentication().getName());

			 //2.2 if files > 10 remove the first from the list
			 // if(files.size() >= 10)
			 //	 files.pop();
			 
			 //2.3 create new fileMeta
			 imagemMeta = new MetaImagem();
			 imagemMeta.setFileName(mpf.getOriginalFilename());
			 imagemMeta.setFileSize(mpf.getSize()/1024+" Kb");
			 imagemMeta.setFileType(mpf.getContentType());
			 
			 try {
				imagemMeta.setBytes(mpf.getBytes());
				
				// copy file to local disk (make sure the path "e.g. D:/temp/files" exists)
				//FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream("G:/temp/files/"+mpf.getOriginalFilename()));
				//Salvar no banco de dados.
				imagemMeta.setIdPost(Integer.parseInt(request.getParameter("idPost")));
				picDAO.adiciona(imagemMeta);
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 //2.4 add to files
			 files.add(imagemMeta);
			 
		 }
		 
		// result will be like this
		// [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
		return files;
 
	}
	/***************************************************
	 * URL: /rest/controller/get/{value}
	 * get(): get file as an attachment
	 * @param response : passed by the server
	 * @param value : value from the URL
	 * @return void
	 ****************************************************/
	@RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
	 public void get(HttpServletResponse response,@PathVariable String value){
		 MetaImagem getFile = files.get(Integer.parseInt(value));
		 try {		
			 	response.setContentType(getFile.getFileType());
			 	response.setHeader("Content-disposition", "attachment; filename=\""+getFile.getFileName()+"\"");
		        FileCopyUtils.copy(getFile.getBytes(), response.getOutputStream());
		 }catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 }
	 }
 
}
