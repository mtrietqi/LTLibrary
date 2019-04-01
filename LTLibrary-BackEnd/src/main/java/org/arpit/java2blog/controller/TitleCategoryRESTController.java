package org.arpit.java2blog.controller;




import java.util.List;

import org.arpit.java2blog.model.TitleCategories;
import org.arpit.java2blog.model.Titles;

import org.arpit.java2blog.model.Categories;

import org.arpit.java2blog.service.TitleCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TitleCategoryRESTController {
	@Autowired
	TitleCategoryService titleCategoryService;
	// Get All Request
	@RequestMapping(value = "/getAllTitleCategoriesREST", method = RequestMethod.GET, produces = "application/json")
	public List<TitleCategories> getTitleCategories() {
		List<TitleCategories> listOfTitleCategories = titleCategoryService.getAllTitleCategories();
		for (int i = 0; i < listOfTitleCategories.size(); i++) {
			listOfTitleCategories.get(i).getCategories().setTitlecategorieses(null);
			listOfTitleCategories.get(i).getTitles().setBookses(null);
			listOfTitleCategories.get(i).getTitles().setPublishers(null);
			listOfTitleCategories.get(i).getTitles().setTitleauthorses(null);
			listOfTitleCategories.get(i).getTitles().setTitlecategorieses(null);
			listOfTitleCategories.get(i).getTitles().setCoursebookses(null);
		}
		return listOfTitleCategories;
	}
	//Get By Id
	@RequestMapping(value = "/getTitleCategoriesByIDREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public TitleCategories getTitleCategoriesByID(@PathVariable("id") int id) {
		 TitleCategories tc= this.titleCategoryService.getTitleCategories(id);
		 tc.getCategories().setTitlecategorieses(null);
		 tc.getTitles().setBookses(null);
		 tc.getTitles().setPublishers(null);
		 tc.getTitles().setTitleauthorses(null);
		 tc.getTitles().setTitlecategorieses(null);
		 tc.getTitles().setCoursebookses(null);
		 return tc;
	}
	//add Request
	@RequestMapping(value = "/addTitleCategoriesREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public TitleCategories addTitleCategoriesREST(
			@RequestParam("catId") Integer catId,
			@RequestParam("titleId") Integer titleId
			) {

		Categories category= new Categories();
		category.setCatid(catId);
		Titles title= new Titles();
		title.setTitleid(titleId);
		TitleCategories titleCategory= new TitleCategories();
		titleCategory.setCategories(category);
		titleCategory.setTitles(title);

		// p.setImagePath(imagePath);
		TitleCategories newTitleCategories = titleCategoryService.addTitleCategoriesREST(titleCategory);


		return newTitleCategories;
	}
	//Delete Request
	@RequestMapping(value = "/deleteTitleCategoriesREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<TitleCategories> deleteTitleCategories(@PathVariable("id") int id) {
		titleCategoryService.deleteTitleCategories(id);
		List<TitleCategories> listOfTitleCategories = titleCategoryService.getAllTitleCategories();
		for (int i = 0; i < listOfTitleCategories.size(); i++) {
			listOfTitleCategories.get(i).getCategories().setTitlecategorieses(null);
			listOfTitleCategories.get(i).getTitles().setBookses(null);
			listOfTitleCategories.get(i).getTitles().setPublishers(null);
			listOfTitleCategories.get(i).getTitles().setTitleauthorses(null);
			listOfTitleCategories.get(i).getTitles().setTitlecategorieses(null);
			listOfTitleCategories.get(i).getTitles().setCoursebookses(null);
		}
		 return listOfTitleCategories;	 

	}
	//Update Request
	@RequestMapping(value = "/updateTitleCategoriesREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public TitleCategories updateTitleCategoriesREST(
			@RequestParam("titleCatid") Integer titleCatid,
			@RequestParam("catId") Integer catId,
			@RequestParam("titleId") Integer titleId){
		// upload file


	
		Categories category= new Categories();
		category.setCatid(catId);
		Titles title= new Titles();
		title.setTitleid(titleId);
		TitleCategories titleCategory= new TitleCategories(titleCatid);
		titleCategory.setCategories(category);
		titleCategory.setTitles(title);
		
		return titleCategoryService.updateTitleCategoriesREST(titleCategory);
	}
	

}
