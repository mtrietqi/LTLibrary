package org.arpit.java2blog.service;

import java.util.List;

import org.arpit.java2blog.dao.AuthorDAO;
import org.arpit.java2blog.model.Authors;
import org.arpit.java2blog.model.Authors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("authorService")
public class AuthorService {

	@Autowired
	AuthorDAO authorDao;
	
	@Transactional
	public List<Authors> getAllAuthors() {
		return authorDao.getAllAuthors();
	}

	@Transactional
	public Authors getAuthors(int id) {
		return authorDao.getAuthors(id);
	}

	@Transactional
	public void addAuthors(Authors author) {
		authorDao.addAuthors(author);
	}
	
	@Transactional
	public Authors addAuthorsREST(Authors author) {
		return authorDao.addAuthors(author);
	}

	@Transactional
	public void updateAuthors(Authors author) {
		authorDao.updateAuthors(author);

	}
	@Transactional
	public Authors updateAuthorsREST(Authors author) {
		return authorDao.updateAuthorsREST(author);

	}

	@Transactional
	public void deleteAuthors(int id) {
		authorDao.deleteAuthors(id);
	}

	public AuthorDAO getAuthorDao() {
		return authorDao;
	}

	public void setAuthorDao(AuthorDAO authorDao) {
		this.authorDao = authorDao;
	}
	//For testing
	
	@Transactional
	public List<Authors> getAllAuthorsTEST() {
		return authorDao.getAllAuthorsTEST();
	}

	@Transactional
	public Authors getAuthorsTEST(int id) {
		return authorDao.getAuthorsTEST(id);
	}

	@Transactional
	public void addAuthorsTEST(Authors author) {
		authorDao.addAuthorsTEST(author);
	}
	
	@Transactional
	public Authors addAuthorsRESTTEST(Authors author) {
		return authorDao.addAuthorsTEST(author);
	}

	@Transactional
	public void updateAuthorsTEST(Authors author) {
		authorDao.updateAuthorsTEST(author);

	}
	@Transactional
	public Authors updateAuthorsRESTTEST(Authors author) {
		return authorDao.updateAuthorsRESTTEST(author);

	}

	@Transactional
	public void deleteAuthorsTEST(int id) {
		authorDao.deleteAuthorsTEST(id);
	}


	
}
