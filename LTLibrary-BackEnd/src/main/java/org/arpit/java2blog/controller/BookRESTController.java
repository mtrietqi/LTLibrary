package org.arpit.java2blog.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletContext;

import org.arpit.java2blog.model.BookNotYetReturn;
import org.arpit.java2blog.model.Books;

import org.arpit.java2blog.model.Books;
import org.arpit.java2blog.model.Titles;
import org.arpit.java2blog.model.Books;
import org.arpit.java2blog.model.Titles;

import org.arpit.java2blog.service.BookService;
import org.arpit.java2blog.service.TitleService;
import org.arpit.java2blog.util.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class BookRESTController {
	@Autowired
	BookService bookService;

	@Autowired
	private ServletContext servletContext;
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	// Get All Request
	@RequestMapping(value = "/getAllBooksREST", method = RequestMethod.GET, produces = "application/json")
	public List<Books> getBooks() {
		List<Books> listOfBooks = bookService.getAllBooks();
		for (int i = 0; i < listOfBooks.size(); i++) {
			listOfBooks.get(i).getTitles().setBookses(null);
			listOfBooks.get(i).getTitles().setPublishers(null);
			listOfBooks.get(i).getTitles().setTitleauthorses(null);
			listOfBooks.get(i).getTitles().setTitlecategorieses(null);
			listOfBooks.get(i).getTitles().setCoursebookses(null);
			listOfBooks.get(i).setBorrowingdetailses(null);
		}
		return listOfBooks;
	}
	//add Request
	@RequestMapping(value = "/addBooksREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Books addBooksREST(
			@RequestParam("titleId") Integer titleId,
			@RequestParam("librariansByCreatedby") Integer librariansByCreatedby,
			@RequestParam("librariansByUpdatedby") Integer librariansByUpdatedby,
			@RequestParam("barCode") String barCode,
			@RequestParam("bookStatus") String bookStatus,
			@RequestParam("note") String note,
			@RequestParam("realPrice") Float realPrice,
			@RequestParam("createdDate") String createdDate, @RequestParam("updatedDate") String updatedDate)
			throws ParseException {

		Titles title = new Titles();
		title.setTitleid(titleId);

		Books book= new Books(librariansByCreatedby, librariansByUpdatedby,
								barCode, bookStatus, realPrice, note, formatter.parse(createdDate), formatter.parse(updatedDate));
		book.setTitles(title);

		Books newBooks = bookService.addBooksREST(book);


		return newBooks;
	}
	//Delete Request
	@RequestMapping(value = "/deleteBooksREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<Books> deleteBooks(@PathVariable("id") int id) {
		Books b = bookService.getBooks(id);
		
		bookService.deleteBooks(id);
		List<Books> listOfBooks = bookService.getAllBooks();
		for (int i = 0; i < listOfBooks.size(); i++) {
			listOfBooks.get(i).getTitles().setBookses(null);
			listOfBooks.get(i).getTitles().setPublishers(null);
			listOfBooks.get(i).getTitles().setTitleauthorses(null);
			listOfBooks.get(i).getTitles().setTitlecategorieses(null);
			listOfBooks.get(i).getTitles().setCoursebookses(null);
			listOfBooks.get(i).setBorrowingdetailses(null);
		}
		return listOfBooks;

	}
	//Get By Id
	@RequestMapping(value = "/getBooksREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public Books getBooksByIdREST(@PathVariable int id) {
		Books book = bookService.getBooks(id);
		book.getTitles().setBookses(null);
		book.getTitles().setPublishers(null);
		book.getTitles().setTitleauthorses(null);
		book.getTitles().setTitlecategorieses(null);
		book.getTitles().setCoursebookses(null);
		book.setBorrowingdetailses(null);
		return book;
	}
	//Update Request
	@RequestMapping(value = "/updateBooksREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Books updateBooksREST(
			@RequestParam("bookId") Integer bookId,
			@RequestParam("titleId") Integer titleId,
			@RequestParam("librariansByCreatedby") Integer librariansByCreatedby,
			@RequestParam("librariansByUpdatedby") Integer librariansByUpdatedby,
			@RequestParam("barCode") String barCode,
			@RequestParam("bookStatus") String bookStatus,
			@RequestParam("note") String note,
			@RequestParam("realPrice") Float realPrice,
			@RequestParam("createdDate") String createdDate, @RequestParam("updatedDate") String updatedDate)
			throws ParseException {

		
	
		Titles title = new Titles();
		title.setTitleid(titleId);
		Books currentBooks = bookService.getBooks(bookId);
		Books book = new Books(bookId,librariansByCreatedby, librariansByUpdatedby,
				barCode, bookStatus, realPrice, note, formatter.parse(createdDate), formatter.parse(updatedDate));
		book.setTitles(title);
		return bookService.updateBooksREST(book);
	}
	// get All Book Not Yet Return Request
	@RequestMapping(value = "/getAllBookNotYetReturnREST",method=RequestMethod.GET,produces = "application/json")
	public List<BookNotYetReturn> getBookNotYetReturnREST() {
		List<BookNotYetReturn> listOfBookNotYetReturns = bookService.getAllBookNotYetReturn();
		return listOfBookNotYetReturns;
	}
	//get Book Not Yet Return Amount Request
	@RequestMapping(value = "/getBookNotYetReturnAmountREST",method=RequestMethod.GET,produces = "application/json")
	public Long getBookNotYetReturnAmountREST() {
		Long bookNotYetReturnAmount = bookService.getBookNotYetReturnAmount();
		return bookNotYetReturnAmount;
	}
	// get Book Amount Request
	@RequestMapping(value = "/getBookAmountREST",method=RequestMethod.GET,produces = "application/json")
	public Long getBookAmountREST() {
		Long bookAmount = bookService.getBookAmount();
		return bookAmount;
	}
	// get All Available Book Request
	@RequestMapping(value = "/getAllAvailableBookREST",method=RequestMethod.GET,produces = "application/json")
	public List<Books> getAllAvailableBookREST() {
		List<Books> listOfAvailableBook = bookService.getAllAvailableBook();
		for (int i = 0; i < listOfAvailableBook.size(); i++) {
			listOfAvailableBook.get(i).getTitles().setBookses(null);
			listOfAvailableBook.get(i).getTitles().setPublishers(null);
			listOfAvailableBook.get(i).getTitles().setTitleauthorses(null);
			listOfAvailableBook.get(i).getTitles().setTitlecategorieses(null);
			listOfAvailableBook.get(i).getTitles().setCoursebookses(null);
			listOfAvailableBook.get(i).setBorrowingdetailses(null);
		}
		return listOfAvailableBook;
	}
	// get All Book Not Yet Return By borrower id Request
	@RequestMapping(value = "/getBookNotYetReturnByBorrowerREST/{id}",method=RequestMethod.GET,produces = "application/json")
	public List<BookNotYetReturn> getBookNotYetReturnByBorrowerREST(@PathVariable int id) {
		List<BookNotYetReturn> listOfBookNotYetReturnByBorrower = bookService.getBookNotYetReturnByBorrower(id);
		return listOfBookNotYetReturnByBorrower;
	}
	// get All Book By Status Request
	@RequestMapping(value = "/getAllBookByStatusREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<Books> getAllBookByStatusREST(@PathVariable int id) {
		List<Books> listOfBooks = bookService.getAllBookByStatus(id);
		for (int i = 0; i < listOfBooks.size(); i++) {
			listOfBooks.get(i).getTitles().setBookses(null);
			listOfBooks.get(i).getTitles().setPublishers(null);
			listOfBooks.get(i).getTitles().setTitleauthorses(null);
			listOfBooks.get(i).getTitles().setTitlecategorieses(null);
			listOfBooks.get(i).getTitles().setCoursebookses(null);
			listOfBooks.get(i).setBorrowingdetailses(null);
		}
		return listOfBooks;
	}
}
