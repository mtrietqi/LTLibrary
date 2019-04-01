package org.arpit.java2blog.controller;




import java.util.List;

import org.arpit.java2blog.model.TitleAuthors;
import org.arpit.java2blog.model.Titles;

import org.arpit.java2blog.model.Authors;
import org.arpit.java2blog.service.TitleAuthorService;
import org.arpit.java2blog.service.TitleCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TitleAuthorRESTController {
	@Autowired
	TitleAuthorService titleAuthorService;
	// Get All Request
	@RequestMapping(value = "/getAllTitleAuthorsREST", method = RequestMethod.GET, produces = "application/json")
	public List<TitleAuthors> getTitleAuthors() {
		List<TitleAuthors> listOfTitleAuthors = titleAuthorService.getAllTitleAuthors();
		for (int i = 0; i < listOfTitleAuthors.size(); i++) {
			listOfTitleAuthors.get(i).getAuthors().setTitleauthorses(null);
			listOfTitleAuthors.get(i).getTitles().setBookses(null);
			listOfTitleAuthors.get(i).getTitles().setPublishers(null);
			listOfTitleAuthors.get(i).getTitles().setTitleauthorses(null);
			listOfTitleAuthors.get(i).getTitles().setTitlecategorieses(null);
			listOfTitleAuthors.get(i).getTitles().setCoursebookses(null);
		}
		return listOfTitleAuthors;
	}
	//Get By Id
	@RequestMapping(value = "/getTitleAuthorsByIDREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public TitleAuthors getTitleAuthorsByID(@PathVariable("id") int id) {
		 TitleAuthors titleAuthor= this.titleAuthorService.getTitleAuthors(id);
		 titleAuthor.getAuthors().setTitleauthorses(null);
		 titleAuthor.getTitles().setBookses(null);
		 titleAuthor.getTitles().setPublishers(null);
		 titleAuthor.getTitles().setTitleauthorses(null);
		 titleAuthor.getTitles().setTitlecategorieses(null);
		 titleAuthor.getTitles().setCoursebookses(null);
		 return titleAuthor;
	}
	//add Request
	@RequestMapping(value = "/addTitleAuthorsREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public TitleAuthors addTitleAuthorsREST(
			@RequestParam("auId") Integer auId,
			@RequestParam("titleId") Integer titleId
			) {

		Authors author= new Authors();
		author.setAuid(auId);
		Titles title= new Titles();
		title.setTitleid(titleId);
		TitleAuthors titleAuthor= new TitleAuthors();
		titleAuthor.setAuthors(author);
		titleAuthor.setTitles(title);

		// p.setImagePath(imagePath);
		TitleAuthors newTitleAuthors = titleAuthorService.addTitleAuthorsREST(titleAuthor);


		return newTitleAuthors;
	}
	//Delete Request
	@RequestMapping(value = "/deleteTitleAuthorsREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<TitleAuthors> deleteTitleAuthors(@PathVariable("id") int id) {
		titleAuthorService.deleteTitleAuthors(id);
		List<TitleAuthors> listOfTitleAuthors = titleAuthorService.getAllTitleAuthors();
		for (int i = 0; i < listOfTitleAuthors.size(); i++) {
			listOfTitleAuthors.get(i).getAuthors().setTitleauthorses(null);
			listOfTitleAuthors.get(i).getTitles().setBookses(null);
			listOfTitleAuthors.get(i).getTitles().setPublishers(null);
			listOfTitleAuthors.get(i).getTitles().setTitleauthorses(null);
			listOfTitleAuthors.get(i).getTitles().setTitlecategorieses(null);
			listOfTitleAuthors.get(i).getTitles().setCoursebookses(null);
		}
		 return listOfTitleAuthors;	 

	}
	// Update Request
	@RequestMapping(value = "/updateTitleAuthorsREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public TitleAuthors updateTitleAuthorsREST(
			@RequestParam("titleAuthorId") Integer titleAuthorId,
			@RequestParam("auId") Integer auId,
			@RequestParam("titleId") Integer titleId){
		// upload file


	
		Authors author= new Authors();
		author.setAuid(auId);
		Titles title= new Titles();
		title.setTitleid(titleId);
		TitleAuthors titleAuthor= new TitleAuthors(titleAuthorId);
		titleAuthor.setAuthors(author);
		titleAuthor.setTitles(title);
		
		return titleAuthorService.updateTitleAuthorsREST(titleAuthor);
	}
	

}
