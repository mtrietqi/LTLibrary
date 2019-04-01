package org.arpit.java2blog.service;

import java.util.List;

import org.arpit.java2blog.dao.TitleCategoryDAO;
import org.arpit.java2blog.model.TitleCategories;
import org.arpit.java2blog.model.TitleCategories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("titleCategoryService")
public class TitleCategoryService {

	@Autowired
	TitleCategoryDAO titleCategoryDAO;
	
	@Transactional
	public List<TitleCategories> getAllTitleCategories() {
		return titleCategoryDAO.getAllTitleCategories();
	}

	@Transactional
	public TitleCategories getTitleCategories(int id) {
		return titleCategoryDAO.getTitleCategories(id);
	}

	@Transactional
	public void addTitleCategories(TitleCategories titleCategory) {
		titleCategoryDAO.addTitleCategories(titleCategory);
	}
	@Transactional
	public TitleCategories addTitleCategoriesREST(TitleCategories titleCategory) {
		titleCategoryDAO.addTitleCategories(titleCategory);
		return titleCategory;
	}

	@Transactional
	public void updateTitleCategories(TitleCategories titleCategory) {
		titleCategoryDAO.updateTitleCategories(titleCategory);

	}
	@Transactional
	public TitleCategories updateTitleCategoriesREST(TitleCategories titleCategory) {
		return titleCategoryDAO.updateTitleCategoriesREST(titleCategory);

	}

	@Transactional
	public void deleteTitleCategories(int id) {
		titleCategoryDAO.deleteTitleCategories(id);
	}
}
