package org.arpit.java2blog.service;

import java.util.List;

import org.arpit.java2blog.dao.LibrarianDAO;
import org.arpit.java2blog.model.Librarians;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("librarianService")
public class LibrarianService {

	@Autowired
	LibrarianDAO librarianDAO;
	
	@Transactional
	public List<Librarians> getAllLibrarians() {
		return librarianDAO.getAllLibrarians();
	}

	@Transactional
	public Librarians getLibrarians(int id) {
		return librarianDAO.getLibrarians(id);
	}

	@Transactional
	public void addLibrarians(Librarians librarian) {
		librarianDAO.addLibrarians(librarian);
	}
	@Transactional
	public Librarians addLibrariansREST(Librarians librarian) {
		return librarianDAO.addLibrarians(librarian);
	}

	@Transactional
	public void updateLibrarians(Librarians librarian) {
		librarianDAO.updateLibrarians(librarian);

	}
	@Transactional
	public Librarians updateLibrariansREST(Librarians librarian) {
		return librarianDAO.updateLibrariansREST(librarian);
	}

	@Transactional
	public void deleteLibrarians(int id) {
		librarianDAO.deleteLibrarians(id);
	}
	
	@Transactional
	public LibrarianDAO getLibrarianDAO() {
		return librarianDAO;
	}
	@Transactional
	public void setLibrarianDAO(LibrarianDAO librarianDAO) {
		this.librarianDAO = librarianDAO;
	}
	// for test
	@Transactional
	public List<Librarians> getAllLibrariansTEST() {
		return librarianDAO.getAllLibrariansTEST();
	}

	@Transactional
	public Librarians getLibrariansTEST(int id) {
		return librarianDAO.getLibrariansTEST(id);
	}

	@Transactional
	public void addLibrariansTEST(Librarians librarian) {
		librarianDAO.addLibrariansTEST(librarian);
	}
	@Transactional
	public Librarians addLibrariansRESTTEST(Librarians librarian) {
		return librarianDAO.addLibrariansTEST(librarian);
	}

	@Transactional
	public void updateLibrariansTEST(Librarians librarian) {
		librarianDAO.updateLibrariansTEST(librarian);

	}
	@Transactional
	public Librarians updateLibrariansRESTTEST(Librarians librarian) {
		return librarianDAO.updateLibrariansRESTTEST(librarian);
	}

	@Transactional
	public void deleteLibrariansTEST(int id) {
		librarianDAO.deleteLibrariansTEST(id);
	}
	

	
}
