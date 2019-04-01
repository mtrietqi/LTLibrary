package org.arpit.java2blog.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletContext;

import org.arpit.java2blog.model.Librarians;

import org.arpit.java2blog.service.LibrarianService;
import org.arpit.java2blog.util.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class LibrarianRESTController {
	@Autowired
	LibrarianService librarianService;
	@Autowired
	private ServletContext servletContext;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	// Get All Request
	@CrossOrigin
	@RequestMapping(value = "/getAllLibrariansREST",method=RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public List<Librarians> getLibrarians() {
		List<Librarians> listOfLibrarians = librarianService.getAllLibrarians();
		return listOfLibrarians;
	}
	//Delete Request
	@RequestMapping(value = "/deleteLibrariansREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<Librarians> deleteLibrarians(@PathVariable("id") int id) {
		// delete person image
		Librarians lib = librarianService.getLibrarians(id);
		
		String fileName = servletContext.getRealPath("/") + "WEB-INF\\librarianImages\\" + lib.getAvatar();
		if (lib.getAvatar() != null) {
			File f = new File(fileName);
			if (f.isFile())
				f.delete();
		}
				
		librarianService.deleteLibrarians(id);
		List<Librarians> listOfCountries = librarianService.getAllLibrarians();
		 return listOfCountries;	 

	}
	//Get By Id
	@RequestMapping(value = "/getLibrariansByIDREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public Librarians getLibrariansByID(@PathVariable("id") int id) {
		 Librarians librarian= this.librarianService.getLibrarians(id);
		 return librarian;
	}	
	
	//add Request
	@RequestMapping(value = "/addLibrariansREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Librarians addLibrariansREST(@RequestParam(value = "avatar", required = false) MultipartFile avatar,
			@RequestParam("userName") String userName,
			@RequestParam("password") String password,
			@RequestParam("librariansByCreatedby") Integer librariansByCreatedby,
			@RequestParam("librariansByUpdatedby") Integer librariansByUpdatedby,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("gender") boolean gender,
			@RequestParam("email") String email, @RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("address") String address, @RequestParam("status") boolean status,
			@RequestParam("createdDate") String createdDate, @RequestParam("updatedDate") String updatedDate,
			@RequestParam(value="libRole", required=false) String libRole)
			throws ParseException {

		
		Librarians librarian = new Librarians(firstName, lastName, gender, email, phoneNumber, address,
				userName, password, status,
				formatter.parse(createdDate),formatter.parse(updatedDate), librariansByUpdatedby, librariansByCreatedby,libRole);

		// p.setImagePath(imagePath);
		Librarians newLibrarians = librarianService.addLibrariansREST(librarian);
		if (!avatar.isEmpty())
		{
			// upload file
			FileUpload fileUpload = new FileUpload();
			fileUpload.processFileName(avatar, servletContext.getRealPath("/"),"WEB-INF\\librarianImages\\", newLibrarians.getLibid());
			String fileName = avatar.getOriginalFilename();
			fileName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			String avatarPath = newLibrarians.getLibid() + fileName;
			newLibrarians.setAvatar(avatarPath);
			librarianService.updateLibrarians(newLibrarians);
		}
		return newLibrarians;
	}
	
	@RequestMapping(value = "/updateLibrariansREST", method = RequestMethod.POST, produces = "application/json")
	public Librarians updateLibrarians(@RequestBody Librarians author) {	
			return librarianService.updateLibrariansREST(author);
	}
	// Update Request
	@RequestMapping(value = "/updateLibrariansREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Librarians updateLibrariansREST(@RequestParam(value = "avatar", required = false) MultipartFile avatar,
			@RequestParam("libId") Integer libId,
			@RequestParam("userName") String userName,
			@RequestParam("password") String password,
			@RequestParam("librariansByCreatedby") Integer librariansByCreatedby,
			@RequestParam("librariansByUpdatedby") Integer librariansByUpdatedby,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("gender") boolean gender,
			@RequestParam("email") String email, @RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("address") String address, @RequestParam("status") boolean status,
			@RequestParam("createdDate") String createdDate, @RequestParam("updatedDate") String updatedDate,
			@RequestParam(value="libRole", required=false) String libRole)
			throws ParseException {
		// upload file
		Librarians librarian;
		String avatarPath = "";
		Librarians currentLibrarians = librarianService.getLibrarians(libId);
		librarian = new Librarians(libId,firstName, lastName, gender, email, phoneNumber, address,
				userName, password, status,
				formatter.parse(createdDate),formatter.parse(updatedDate), librariansByUpdatedby, librariansByCreatedby, libRole);
		String fileName = "";

		if (avatar != null) {
			fileName = avatar.getOriginalFilename();
			fileName = fileName.substring(fileName.lastIndexOf("."));	
	        avatarPath = librarian.getLibid()+fileName;
			FileUpload fileUpload = new FileUpload();
			fileUpload.processFileName(avatar, servletContext.getRealPath("/"),"WEB-INF\\librarianImages\\", libId);
		}
		if (avatar != null && avatar.getSize() > 0)
			librarian.setAvatar(avatarPath);
		else
			librarian.setAvatar(currentLibrarians.getAvatar());
		return librarianService.updateLibrariansREST(librarian);
	}

}