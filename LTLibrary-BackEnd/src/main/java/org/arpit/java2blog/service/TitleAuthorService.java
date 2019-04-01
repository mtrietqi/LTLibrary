package org.arpit.java2blog.service;

import java.util.List;

import org.arpit.java2blog.dao.TitleAuthorDAO;
import org.arpit.java2blog.model.TitleAuthors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("titleAuthorService")
public class TitleAuthorService {

	@Autowired
	TitleAuthorDAO titleAuthorDAO;
	
	@Transactional
	public List<TitleAuthors> getAllTitleAuthors() {
		return titleAuthorDAO.getAllTitleAuthors();
	}

	@Transactional
	public TitleAuthors getTitleAuthors(int id) {
		return titleAuthorDAO.getTitleAuthors(id);
	}

	@Transactional
	public void addTitleAuthors(TitleAuthors titleAuthor) {
		titleAuthorDAO.addTitleAuthors(titleAuthor);
	}
	@Transactional
	public TitleAuthors addTitleAuthorsREST(TitleAuthors titleAuthor) {
		titleAuthorDAO.addTitleAuthors(titleAuthor);
		return titleAuthor;
	}

	@Transactional
	public void updateTitleAuthors(TitleAuthors titleAuthor) {
		titleAuthorDAO.updateTitleAuthors(titleAuthor);

	}
	@Transactional
	public TitleAuthors updateTitleAuthorsREST(TitleAuthors titleAuthor) {
		return titleAuthorDAO.updateTitleAuthorsREST(titleAuthor);

	}

	@Transactional
	public void deleteTitleAuthors(int id) {
		titleAuthorDAO.deleteTitleAuthors(id);
	}
}
