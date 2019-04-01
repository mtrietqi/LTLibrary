package org.arpit.java2blog.controller;

import java.text.ParseException;
import java.util.List;

import org.arpit.java2blog.model.Books;
import org.arpit.java2blog.model.BorrowingDetails;
import org.arpit.java2blog.model.Borrowings;
import org.arpit.java2blog.model.Returndetails;
import org.arpit.java2blog.model.Returnings;
import org.arpit.java2blog.service.ReturnDetailService;
import org.hibernate.internal.util.beans.BeanInfoHelper.ReturningBeanInfoDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReturnDetailRESTController {
	
	@Autowired
	ReturnDetailService returnDetailService;
	// Get All Request
	@RequestMapping(value = "/getAllReturnDetailsREST", method = RequestMethod.GET, produces = "application/json")
	public List<Returndetails> getReturnDetails() {
		List<Returndetails> listOfReturnDetails = returnDetailService.getAllReturndetails();
		for (int i = 0; i < listOfReturnDetails.size(); i++) {
			listOfReturnDetails.get(i).getBorrowingdetails().getBorrowings().setBorrowingdetailses(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBorrowings().getBorrowers().setBorrowingses(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBorrowings().getBorrowers().setReturningses(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBorrowings().getBorrowers().getBorrowertype().setBorrowerses(null);
			
			listOfReturnDetails.get(i).getBorrowingdetails().getBooks().setBorrowingdetailses(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBooks().getTitles().setBookses(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBooks().getTitles().setPublishers(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBooks().getTitles().setTitleauthorses(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBooks().getTitles().setTitlecategorieses(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBooks().getTitles().setCoursebookses(null);
			
			listOfReturnDetails.get(i).getBorrowingdetails().setReturndetailses(null);
			
			
			listOfReturnDetails.get(i).getReturnings().getBorrowers().setBorrowingses(null);
			listOfReturnDetails.get(i).getReturnings().getBorrowers().setReturningses(null);
			listOfReturnDetails.get(i).getReturnings().getBorrowers().getBorrowertype().setBorrowerses(null);
			listOfReturnDetails.get(i).getReturnings().setReturndetailses(null);
		}
		return listOfReturnDetails;
	}
	//Get By Id
	@RequestMapping(value = "/getReturnDetailsREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public Returndetails getReturnDetailsByIdREST(@PathVariable int id) {
		Returndetails returnDetail= returnDetailService.getReturndetails(id);
		returnDetail.getBorrowingdetails().getBorrowings().setBorrowingdetailses(null);
		returnDetail.getBorrowingdetails().getBorrowings().getBorrowers().setBorrowingses(null);
		returnDetail.getBorrowingdetails().getBorrowings().getBorrowers().setReturningses(null);
		returnDetail.getBorrowingdetails().getBorrowings().getBorrowers().getBorrowertype().setBorrowerses(null);
		
		returnDetail.getBorrowingdetails().getBooks().setBorrowingdetailses(null);
		returnDetail.getBorrowingdetails().getBooks().getTitles().setBookses(null);
		returnDetail.getBorrowingdetails().getBooks().getTitles().setPublishers(null);
		returnDetail.getBorrowingdetails().getBooks().getTitles().setTitleauthorses(null);
		returnDetail.getBorrowingdetails().getBooks().getTitles().setTitlecategorieses(null);
		returnDetail.getBorrowingdetails().getBooks().getTitles().setCoursebookses(null);
		
		returnDetail.getBorrowingdetails().setReturndetailses(null);
		
		
		returnDetail.getReturnings().getBorrowers().setBorrowingses(null);
		returnDetail.getReturnings().getBorrowers().setReturningses(null);
		returnDetail.getReturnings().getBorrowers().getBorrowertype().setBorrowerses(null);
		returnDetail.getReturnings().setReturndetailses(null);

		return returnDetail;
	}
	//Delete Request
	@RequestMapping(value = "/deleteReturnDetailsREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<Returndetails> deleteReturnDetails(@PathVariable("id") int id) {
		returnDetailService.deleteReturndetails(id);
		List<Returndetails> listOfReturnDetails = returnDetailService.getAllReturndetails();
		for (int i = 0; i < listOfReturnDetails.size(); i++) {
			listOfReturnDetails.get(i).getBorrowingdetails().getBorrowings().setBorrowingdetailses(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBorrowings().getBorrowers().setBorrowingses(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBorrowings().getBorrowers().setReturningses(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBorrowings().getBorrowers().getBorrowertype().setBorrowerses(null);
			
			listOfReturnDetails.get(i).getBorrowingdetails().getBooks().setBorrowingdetailses(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBooks().getTitles().setBookses(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBooks().getTitles().setPublishers(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBooks().getTitles().setTitleauthorses(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBooks().getTitles().setTitlecategorieses(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBooks().getTitles().setCoursebookses(null);
			
			listOfReturnDetails.get(i).getBorrowingdetails().setReturndetailses(null);
			
			
			listOfReturnDetails.get(i).getReturnings().getBorrowers().setBorrowingses(null);
			listOfReturnDetails.get(i).getReturnings().getBorrowers().setReturningses(null);
			listOfReturnDetails.get(i).getReturnings().getBorrowers().getBorrowertype().setBorrowerses(null);
			listOfReturnDetails.get(i).getReturnings().setReturndetailses(null);
		}
		return listOfReturnDetails;

	}
	//add Request
	@RequestMapping(value = "/addReturnDetailsREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Returndetails addReturnDetailsREST(@RequestParam("description") String description,
			@RequestParam("returnStatus") String returnStatus,
			@RequestParam("bordetailId") Integer bordetailId,
			@RequestParam("returnId") Integer returnId) {

		BorrowingDetails borrowingDetail= new BorrowingDetails();
		borrowingDetail.setBordetailid(bordetailId);
		Returnings returning= new Returnings();
		returning.setReturnid(returnId);
		
	    Returndetails returnDetail= new Returndetails(description, returnStatus);
	    returnDetail.setBorrowingdetails(borrowingDetail);
	    returnDetail.setReturnings(returning);
		return returnDetailService.addReturndetailsREST(returnDetail);
	}
	// Update Request
	@RequestMapping(value = "/updateReturnDetailsREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Returndetails updateReturnDetailsREST(
			@RequestParam("returnDetailid") Integer returnDetailid,
			@RequestParam("description") String description,
			@RequestParam("returnStatus") String returnStatus,
			@RequestParam("bordetailId") Integer bordetailId,
			@RequestParam("returnId") Integer returnId) {

		BorrowingDetails borrowingDetail= new BorrowingDetails();
		borrowingDetail.setBordetailid(bordetailId);
		Returnings returning= new Returnings();
		returning.setReturnid(returnId);
		
	    Returndetails returnDetail= new Returndetails(returnDetailid,description, returnStatus);
	    returnDetail.setBorrowingdetails(borrowingDetail);
	    returnDetail.setReturnings(returning);
		return returnDetailService.updateReturndetailsREST(returnDetail);
	}
	//get Returndetails From Returning Id Request
	@RequestMapping(value = "/getReturndetailsFromReturningId/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<Returndetails> getReturndetailsFromReturningId(@PathVariable int id) {
		List<Returndetails> listOfReturnDetails = returnDetailService.getReturndetailsFromReturningId(id);
		for (int i = 0; i < listOfReturnDetails.size(); i++) {
			listOfReturnDetails.get(i).getBorrowingdetails().getBorrowings().setBorrowingdetailses(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBorrowings().getBorrowers().setBorrowingses(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBorrowings().getBorrowers().setReturningses(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBorrowings().getBorrowers().getBorrowertype().setBorrowerses(null);
			
			listOfReturnDetails.get(i).getBorrowingdetails().getBooks().setBorrowingdetailses(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBooks().getTitles().setBookses(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBooks().getTitles().setPublishers(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBooks().getTitles().setTitleauthorses(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBooks().getTitles().setTitlecategorieses(null);
			listOfReturnDetails.get(i).getBorrowingdetails().getBooks().getTitles().setCoursebookses(null);
			
			listOfReturnDetails.get(i).getBorrowingdetails().setReturndetailses(null);
					
			listOfReturnDetails.get(i).getReturnings().getBorrowers().setBorrowingses(null);
			listOfReturnDetails.get(i).getReturnings().getBorrowers().setReturningses(null);
			listOfReturnDetails.get(i).getReturnings().getBorrowers().getBorrowertype().setBorrowerses(null);
			listOfReturnDetails.get(i).getReturnings().setReturndetailses(null);
		}
		return listOfReturnDetails;
	}
}
