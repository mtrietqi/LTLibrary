package org.arpit.java2blog.service;

import java.util.List;

import org.arpit.java2blog.dao.CategoryDAO;
import org.arpit.java2blog.model.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("categoryService")
public class CategoryService {

	@Autowired
	CategoryDAO categoryDAO;
	
	@Transactional
	public List<Categories> getAllCategories() {
		return categoryDAO.getAllCategories();
	}

	@Transactional
	public Categories getCategories(int id) {
		return categoryDAO.getCategories(id);
	}

	@Transactional
	public void addCategories(Categories category) {
		categoryDAO.addCategories(category);
	}

	@Transactional
	public void updateCategories(Categories category) {
		categoryDAO.updateCategories(category);
	}

	@Transactional
	public void deleteCategories(int id) {
		categoryDAO.deleteCategories(id);
	}
	@Transactional
	public Categories addCategoriesREST(Categories category) {
		return categoryDAO.addCategories(category);
	}
	@Transactional
	public Categories updateCategoriesREST(Categories category) {
		return categoryDAO.updateCategoriesREST(category);
	}

	public CategoryDAO getCategoryDAO() {
		return categoryDAO;
	}

	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}
	
	//For Testing
	@Transactional
	public List<Categories> getAllCategoriesTEST() {
		return categoryDAO.getAllCategoriesTEST();
	}

	@Transactional
	public Categories getCategoriesTEST(int id) {
		return categoryDAO.getCategoriesTEST(id);
	}

	@Transactional
	public void addCategoriesTEST(Categories category) {
		categoryDAO.addCategoriesTEST(category);
	}

	@Transactional
	public void updateCategoriesTEST(Categories category) {
		categoryDAO.updateCategoriesTEST(category);
	}

	@Transactional
	public void deleteCategoriesTEST(int id) {
		categoryDAO.deleteCategoriesTEST(id);
	}
	@Transactional
	public Categories addCategoriesRESTTEST(Categories category) {
		return categoryDAO.addCategoriesTEST(category);
	}
	@Transactional
	public Categories updateCategoriesRESTTEST(Categories category) {
		return categoryDAO.updateCategoriesRESTTEST(category);
	}
	
}
