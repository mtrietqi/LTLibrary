package org.arpit.java2blog.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.arpit.java2blog.dao.TitleDAO;
import org.arpit.java2blog.model.AuthorNameFromTitleId;
import org.arpit.java2blog.model.TitleAuthors;
import org.arpit.java2blog.model.TitleCategories;
import org.arpit.java2blog.model.Titles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("titleService")
public class TitleService {

	@Autowired
	TitleDAO titleDAO;
	
	@Transactional
	public List<Titles> getAllTitles() {
		return titleDAO.getAllTitles();
	}

	@Transactional
	public Titles getTitles(int id) {
		return titleDAO.getTitles(id);
	}

	@Transactional
	public void addTitles(Titles title) {
		titleDAO.addTitles(title);
	}
	@Transactional
	public Titles addTitlesREST(Titles title) {
		return titleDAO.addTitles(title);
	}

	@Transactional
	public void updateTitles(Titles title) {
		titleDAO.updateTitles(title);

	}
	@Transactional
	public Titles updateTitlesREST(Titles title) {
		return titleDAO.updateTitlesREST(title);
	}

	@Transactional
	public void deleteTitles(int id) {
		titleDAO.deleteTitles(id);
	}
	
	@Transactional
	public Set<TitleAuthors> getTitleAuthorFromTitleId(int id)
	{
		Set<TitleAuthors> result = new HashSet<TitleAuthors>(titleDAO.getTitleAuthorFromTitleId(id));
		return result;
	}
	
	@Transactional
	public Set<TitleCategories> getTitleCategoriesFromTitleId(int id)
	{
		Set<TitleCategories> result = new HashSet<TitleCategories>(titleDAO.getTitleCategoriesFromTitleId(id));
		return result;
	}
	
}