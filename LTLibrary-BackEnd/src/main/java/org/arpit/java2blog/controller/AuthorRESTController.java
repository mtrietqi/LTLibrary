package org.arpit.java2blog.controller;

import java.util.List;

import org.arpit.java2blog.model.Authors;
import org.arpit.java2blog.model.Authors;
import org.arpit.java2blog.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorRESTController {


	@Autowired
	AuthorService authorService;
//	@CrossOrigin
	// Get All Authors Request
	@RequestMapping(value = "/getAllAuthorsREST",method=RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public List<Authors> getAuthors() {
		List<Authors> listOfAuthors = authorService.getAllAuthors();
		for(int i = 0; i<listOfAuthors.size(); i++)
			listOfAuthors.get(i).setTitleauthorses(null);
		return listOfAuthors;
	}
	// Delete Authors Request
	@RequestMapping(value = "/deleteAuthorsREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<Authors> deleteAuthors(@PathVariable("id") int id) {
		authorService.deleteAuthors(id);
		List<Authors> listOfAuthors = authorService.getAllAuthors();
		for(int i = 0; i<listOfAuthors.size(); i++)
			listOfAuthors.get(i).setTitleauthorses(null);
		 return listOfAuthors;	 

	}
	// Get Author By Id Request
	@RequestMapping(value = "/getAuthorsByIDREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public Authors getAuthorsByID(@PathVariable("id") int id) {
		 Authors c= this.authorService.getAuthors(id);
		 c.setTitleauthorses(null);
		 return c;
	}	
	// Add Authors Request
	@RequestMapping(value = "/addAuthorsREST", method = RequestMethod.POST, produces = "application/json")
	public Authors addAuthors(@RequestBody Authors author) {	
			return authorService.addAuthorsREST(author);
			
	}
	// Update Authors Request
	@RequestMapping(value = "/updateAuthorsREST", method = RequestMethod.POST, produces = "application/json")
	public Authors updateAuthors(@RequestBody Authors author) {	
			return authorService.updateAuthorsREST(author);
	}

}
