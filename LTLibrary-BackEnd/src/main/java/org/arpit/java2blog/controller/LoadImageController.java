package org.arpit.java2blog.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletContext;
import javax.swing.JOptionPane;

import org.arpit.java2blog.model.Borrowers;
import org.arpit.java2blog.model.Librarians;
import org.arpit.java2blog.model.Titles;
import org.arpit.java2blog.service.BorrowerService;
import org.arpit.java2blog.service.BorrowerTypeService;
import org.arpit.java2blog.service.LibrarianService;
import org.arpit.java2blog.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

public class LoadImageController {
	
	//Default path of borrower images on server
	private static final String BORROWER_IMAGE_PATH =  "WEB-INF\\borrowerImages\\";
	//Default path of book images on server
	private static final String TITLE_IMAGE_PATH =  "WEB-INF\\bookImages\\";
	//Default path of table of content files on server
	private static final String TOC_PATH =  "WEB-INF\\tableOfContent\\";
	//Default path of librarian images on server
	private static final String LIBRARIAN_PATH =  "WEB-INF\\librarianImages\\";
	
	@Autowired
	private ServletContext servletContext;
	@Autowired
	BorrowerService borrowerService;
	@Autowired
	TitleService titleService;
	@Autowired
	LibrarianService librarianService;
	
	//Provide accessible server links for borrower images
	@RequestMapping(value = "/image/borrowerImages/{imageName}")
    @ResponseBody
    public byte[] getBorrowerImage(@PathVariable(value = "imageName") String imageId) throws IOException {
		if (!imageId.equals("undefined"))
		{
			System.out.println("Image name "+imageId);
			Borrowers borrower = borrowerService.getBorrowers(Integer.parseInt(imageId));
	        File serverFile = new File(servletContext.getRealPath("/")+BORROWER_IMAGE_PATH+borrower.getAvatar());
	        System.out.println(serverFile);
	        return Files.readAllBytes(serverFile.toPath());
		}
		else
		{
			return null;
		}
    }
	
	//Provide accessible server links for book images
	@RequestMapping(value = "/image/bookImages/{imageName}")
    @ResponseBody
    public byte[] getBookImage(@PathVariable(value = "imageName") String imageId) throws IOException {
		if (!imageId.equals("undefined"))
		{

			Titles titles = titleService.getTitles(Integer.parseInt(imageId));
	        File serverFile = new File(servletContext.getRealPath("/")+TITLE_IMAGE_PATH+titles.getBookimage());
	        System.out.println(serverFile);
	        return Files.readAllBytes(serverFile.toPath());
		}
		else
		{
			return null;
		}
    }
	
	//Provide accessible server links for table of content images/files
	@RequestMapping(value = "/image/tableOfContent/{imageName}")
    @ResponseBody
    public byte[] getTOC(@PathVariable(value = "imageName") String imageId) throws IOException {
		if (!imageId.equals("undefined"))
		{
			Titles titles = titleService.getTitles(Integer.parseInt(imageId));
	        File serverFile = new File(servletContext.getRealPath("/")+TOC_PATH+titles.getTablecontent());
	        return Files.readAllBytes(serverFile.toPath());
		}
		else
		{
			return null;
		}
    }
	
	//Provide accessible server links for librarian images
	@RequestMapping(value = "/image/librarian/{imageName}")
    @ResponseBody
    public byte[] getLibrarian(@PathVariable(value = "imageName") String imageId) throws IOException {
		if (!imageId.equals("undefined"))
		{
			Librarians librarians = librarianService.getLibrarians(Integer.parseInt(imageId));
	        File serverFile = new File(servletContext.getRealPath("/")+LIBRARIAN_PATH+librarians.getAvatar());
	        return Files.readAllBytes(serverFile.toPath());
		}
		else
		{
			return null;
		}
    }
	
}