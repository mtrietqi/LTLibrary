package org.arpit.java2blog.service;

import java.util.List;

import org.arpit.java2blog.dao.BookDAO;
import org.arpit.java2blog.model.BookNotYetReturn;
import org.arpit.java2blog.model.Books;
import org.arpit.java2blog.model.Borrowers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("bookService")
public class BookService {

	@Autowired
	BookDAO bookDAO;
	
	@Transactional
	public List<Books> getAllBooks() {
		return bookDAO.getAllBooks();
	}

	@Transactional
	public Books getBooks(int id) {
		return bookDAO.getBooks(id);
	}

	@Transactional
	public void addBooks(Books book) {
		bookDAO.addBooks(book);
	}
	@Transactional
	public Books addBooksREST(Books book) {
		return bookDAO.addBooks(book);
	}

	@Transactional
	public void updateBooks(Books book) {
		bookDAO.updateBooks(book);

	}
	
	@Transactional
	public Books updateBooksREST(Books book) {
		return bookDAO.updateBooksREST(book);
	}

	@Transactional
	public void deleteBooks(int id) {
		bookDAO.deleteBooks(id);
	}
	
	@Transactional
	public List<BookNotYetReturn> getAllBookNotYetReturn() {
		return bookDAO.getAllBookNotYetReturn();
	}
	
	@Transactional
	public Long getBookNotYetReturnAmount() {
		return bookDAO.getBookNotYetReturnAmount();
	}
	@Transactional
	public Long getBookAmount() {
		return bookDAO.getBookAmount();
	}
	@Transactional
	public List<Books> getAllAvailableBook() {
		return bookDAO.getAllAvailableBook();
	}
	@Transactional
	public List<BookNotYetReturn> getBookNotYetReturnByBorrower(int id) {
		return bookDAO.getBookNotYetReturnByBorrwer(id);
	}
	
	@Transactional
	public List<Books> getAllBookByStatus(int id) {
		return bookDAO.getAllBookByStatus(id);
	}
}
