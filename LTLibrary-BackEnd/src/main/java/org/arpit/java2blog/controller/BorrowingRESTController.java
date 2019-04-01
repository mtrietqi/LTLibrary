package org.arpit.java2blog.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.arpit.java2blog.model.Borrowings;
import org.arpit.java2blog.model.Librarians;
import org.arpit.java2blog.model.Borrowers;
import org.arpit.java2blog.model.Borrowertype;
import org.arpit.java2blog.model.Borrowings;
import org.arpit.java2blog.model.Borrowings;
import org.arpit.java2blog.service.BorrowingService;
import org.arpit.java2blog.service.LibrarianService;
import org.arpit.java2blog.util.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class BorrowingRESTController {
	
	@Autowired
	BorrowingService borrowingService;
	@Autowired
	LibrarianService librarianService;
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	// Get All Request
	@RequestMapping(value = "/getAllBorrowingsREST", method = RequestMethod.GET, produces = "application/json")
	public List<Borrowings> getBorrowings() {
		List<Borrowings> listOfBorrowings = borrowingService.getAllBorrowings();
		for (int i = 0; i < listOfBorrowings.size(); i++) {
			listOfBorrowings.get(i).getBorrowers().setBorrowingses(null);
			listOfBorrowings.get(i).getBorrowers().setReturningses(null);
			listOfBorrowings.get(i).getBorrowers().getBorrowertype().setBorrowerses(null);
			listOfBorrowings.get(i).setBorrowingdetailses(null);
		}
		return listOfBorrowings;
	}
	//add Request
	@RequestMapping(value = "/addBorrowingsREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Borrowings addBorrowingsREST(
			@RequestParam("libUsername") String libUsername,
			@RequestParam("libPassword") String libPassword,
			@RequestParam("borrowerId") Integer borrowerId,
			@RequestParam("librariansByCreatedby") Integer librariansByCreatedby,
			@RequestParam("librariansByUpdatedby") Integer librariansByUpdatedby,
			@RequestParam("borDate") String borDate,
			@RequestParam("createdDate") String createdDate, @RequestParam("updatedDate") String updatedDate)
			throws ParseException {
		List<Librarians> libList = librarianService.getAllLibrarians();
		for (Librarians lib:libList)
		{
			if (lib.getUsername().equals(libUsername))
			{
				if (lib.getPassword().equals(libPassword))
				{
					Borrowers borrower = new Borrowers();
					borrower.setBorrowerid(borrowerId);
					Borrowings borrowing = new Borrowings(librariansByUpdatedby, librariansByCreatedby, formatter.parse(borDate), formatter.parse(createdDate),
							formatter.parse(updatedDate));
					borrowing.setBorrowers(borrower);
					return borrowingService.addBorrowingsREST(borrowing);
				}
			}
		}
		return null;
	}
	//add request
	@RequestMapping(value = "/addBorrowingsRESTNormal", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Borrowings addBorrowingsRESTNormal(
			@RequestParam("borrowerId") Integer borrowerId,
			@RequestParam("librariansByCreatedby") Integer librariansByCreatedby,
			@RequestParam("librariansByUpdatedby") Integer librariansByUpdatedby,
			@RequestParam("borDate") String borDate,
			@RequestParam("createdDate") String createdDate, @RequestParam("updatedDate") String updatedDate)
			throws ParseException {
		
		Borrowers borrower = new Borrowers();
		borrower.setBorrowerid(borrowerId);
		Borrowings borrowing = new Borrowings(librariansByUpdatedby, librariansByCreatedby, formatter.parse(borDate), formatter.parse(createdDate),
				formatter.parse(updatedDate));
		borrowing.setBorrowers(borrower);
		return borrowingService.addBorrowingsREST(borrowing);
	}
	 
	//Get By Id
	@RequestMapping(value = "/getBorrowingsREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public Borrowings getBorrowingsByIdREST(@PathVariable int id) {
		Borrowings borrowing= borrowingService.getBorrowings(id);
		borrowing.getBorrowers().setBorrowingses(null);
		borrowing.getBorrowers().setReturningses(null);
		borrowing.getBorrowers().getBorrowertype().setBorrowerses(null);
		borrowing.setBorrowingdetailses(null);
		return borrowing;
	}
	//Delete Request
	@RequestMapping(value = "/deleteBorrowingsREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<Borrowings> deleteBorrowings(@PathVariable("id") int id) {
		borrowingService.deleteBorrowings(id);
		List<Borrowings> listOfBorrowings = borrowingService.getAllBorrowings();
		for (int i = 0; i < listOfBorrowings.size(); i++) {
			listOfBorrowings.get(i).getBorrowers().setBorrowingses(null);
			listOfBorrowings.get(i).getBorrowers().setReturningses(null);
			listOfBorrowings.get(i).getBorrowers().getBorrowertype().setBorrowerses(null);
			listOfBorrowings.get(i).setBorrowingdetailses(null);
		}
		 return listOfBorrowings;	 

	}
	//Update Request
	@RequestMapping(value = "/updateBorrowingsREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Borrowings updateBorrowingsREST(
			@RequestParam("borrowingId") Integer borrowingId,
			@RequestParam("borrowerId") Integer borrowerId,
			@RequestParam("librariansByCreatedby") Integer librariansByCreatedby,
			@RequestParam("librariansByUpdatedby") Integer librariansByUpdatedby,
			@RequestParam("borDate") String borDate,
			@RequestParam("createdDate") String createdDate, @RequestParam("updatedDate") String updatedDate)
			throws ParseException {

		Borrowers borrower = new Borrowers();
		borrower.setBorrowerid(borrowerId);
		Borrowings borrowing = new Borrowings(borrowingId,librariansByUpdatedby, librariansByCreatedby, formatter.parse(borDate), formatter.parse(createdDate),
				formatter.parse(updatedDate));
		borrowing.setBorrowers(borrower);
		return borrowingService.updateBorrowingsREST(borrowing);
	}
	
	

}
