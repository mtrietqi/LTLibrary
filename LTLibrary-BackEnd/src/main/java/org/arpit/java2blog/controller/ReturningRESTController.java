package org.arpit.java2blog.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.arpit.java2blog.model.Returnings;
import org.arpit.java2blog.model.Borrowers;

import org.arpit.java2blog.service.ReturningService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ReturningRESTController {
	
	@Autowired
	ReturningService returningService;
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	// Get All Request
	@RequestMapping(value = "/getAllReturningsREST", method = RequestMethod.GET, produces = "application/json")
	public List<Returnings> getReturnings() {
		List<Returnings> listOfReturnings = returningService.getAllReturnings();
		for (int i = 0; i < listOfReturnings.size(); i++) {
			listOfReturnings.get(i).getBorrowers().setBorrowingses(null);
			listOfReturnings.get(i).getBorrowers().setReturningses(null);
			listOfReturnings.get(i).getBorrowers().getBorrowertype().setBorrowerses(null);
			listOfReturnings.get(i).setReturndetailses(null);
		}
		return listOfReturnings;
	}
	//add Request
	@RequestMapping(value = "/addReturningsREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Returnings addReturningsREST(
			@RequestParam("borrowerId") Integer borrowerId,
			@RequestParam("librariansByCreatedby") Integer librariansByCreatedby,
			@RequestParam("librariansByUpdatedby") Integer librariansByUpdatedby,
			@RequestParam("returnDate") String returnDate,
			@RequestParam("createdDate") String createdDate, @RequestParam("updatedDate") String updatedDate)
			throws ParseException {

		Borrowers borrower = new Borrowers();
		borrower.setBorrowerid(borrowerId);
		Returnings returning = new Returnings(librariansByUpdatedby, librariansByCreatedby, formatter.parse(returnDate), formatter.parse(createdDate),
				formatter.parse(updatedDate));
		returning.setBorrowers(borrower);
		return returningService.addReturningsREST(returning);
	}
	//Get By Id
	@RequestMapping(value = "/getReturningsREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public Returnings getReturningsByIdREST(@PathVariable int id) {
		Returnings returning= returningService.getReturnings(id);
		returning.getBorrowers().setBorrowingses(null);
		returning.getBorrowers().setReturningses(null);
		returning.getBorrowers().getBorrowertype().setBorrowerses(null);
		returning.setReturndetailses(null);
		return returning;
	}
	//Delete Request
	@RequestMapping(value = "/deleteReturningsREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<Returnings> deleteReturnings(@PathVariable("id") int id) {
		returningService.deleteReturnings(id);
		List<Returnings> listOfReturnings = returningService.getAllReturnings();
		for (int i = 0; i < listOfReturnings.size(); i++) {
			listOfReturnings.get(i).getBorrowers().setBorrowingses(null);
			listOfReturnings.get(i).getBorrowers().setReturningses(null);
			listOfReturnings.get(i).getBorrowers().getBorrowertype().setBorrowerses(null);
			listOfReturnings.get(i).setReturndetailses(null);
		}
		 return listOfReturnings;	 

	}
	//Update Request
	@RequestMapping(value = "/updateReturningsREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Returnings updateReturningsREST(
			@RequestParam("returnId") Integer returnId,
			@RequestParam("borrowerId") Integer borrowerId,
			@RequestParam("librariansByCreatedby") Integer librariansByCreatedby,
			@RequestParam("librariansByUpdatedby") Integer librariansByUpdatedby,
			@RequestParam("returnDate") String returnDate,
			@RequestParam("createdDate") String createdDate, @RequestParam("updatedDate") String updatedDate)
			throws ParseException {

		Borrowers borrower = new Borrowers();
		borrower.setBorrowerid(borrowerId);
		Returnings returning = new Returnings(returnId,librariansByUpdatedby, librariansByCreatedby, formatter.parse(returnDate), formatter.parse(createdDate),
				formatter.parse(updatedDate));
		returning.setBorrowers(borrower);
		return returningService.updateReturningsREST(returning);
	}
	
	

}
