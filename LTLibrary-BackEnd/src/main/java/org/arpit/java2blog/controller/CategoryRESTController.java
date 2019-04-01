package org.arpit.java2blog.controller;

import java.util.List;
import org.arpit.java2blog.model.Categories;
import org.arpit.java2blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryRESTController {
	@Autowired
	CategoryService categoryService;
	// Get All Request
	@RequestMapping(value = "/getAllCategoriesREST", method = RequestMethod.GET, produces = "application/json")
	public List<Categories> getCountries(Model model) {
		List<Categories> listOfCategories = categoryService.getAllCategories();
		for (int i = 0; i < listOfCategories.size(); i++)
			listOfCategories.get(i).setTitlecategorieses(null);
		return listOfCategories;
	}
	// Delete Request
	@RequestMapping(value = "/deleteCategoryREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<Categories> deleteCategory(@PathVariable("id") int id) {
		categoryService.deleteCategories(id);
		List<Categories> listOfCategories = categoryService.getAllCategories();
		for (int i = 0; i < listOfCategories.size(); i++)
			listOfCategories.get(i).setTitlecategorieses(null);
		return listOfCategories;
	}
	//Get By Id
	@RequestMapping(value = "/getCategoryByIDREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public Categories getCountryByID(@PathVariable("id") int id) {
		Categories c = this.categoryService.getCategories(id);
		c.setTitlecategorieses(null);
		return c;
	}
	//add Request
	@RequestMapping(value = "/addCategoryREST", method = RequestMethod.POST, produces = "application/json")
	public Categories addCategory(@RequestBody Categories category) {	
			return categoryService.addCategoriesREST(category);
	}
	// Update Request
	@RequestMapping(value = "/updateCategoryREST", method = RequestMethod.POST, produces = "application/json")
	public Categories updateCountry(@RequestBody Categories category) {	
			return categoryService.updateCategoriesREST(category);
	}
	

}
