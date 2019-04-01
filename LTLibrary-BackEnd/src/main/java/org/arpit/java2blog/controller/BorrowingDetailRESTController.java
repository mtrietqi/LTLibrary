package org.arpit.java2blog.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import org.arpit.java2blog.model.Books;
import org.arpit.java2blog.model.BorrowingDetails;
import org.arpit.java2blog.model.Borrowings;
import org.arpit.java2blog.model.TitleCategories;
import org.arpit.java2blog.service.BorrowingDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BorrowingDetailRESTController {
	
	@Autowired
	BorrowingDetailService borrowingDetailService;
	// Get All Request
	@CrossOrigin
	@RequestMapping(value = "/getAllBorrowingDetailsREST", method = RequestMethod.GET, produces = "application/json")
	public List<BorrowingDetails> getBorrowingDetails() {
		List<BorrowingDetails> listOfBorrowingDetails = borrowingDetailService.getAllBorrowingDetails();
		for (int i = 0; i < listOfBorrowingDetails.size(); i++) {
			listOfBorrowingDetails.get(i).getBorrowings().setBorrowingdetailses(null);
			listOfBorrowingDetails.get(i).getBorrowings().getBorrowers().setBorrowingses(null);
			listOfBorrowingDetails.get(i).getBorrowings().getBorrowers().setReturningses(null);
			listOfBorrowingDetails.get(i).getBorrowings().getBorrowers().getBorrowertype().setBorrowerses(null);
			
			listOfBorrowingDetails.get(i).getBooks().setBorrowingdetailses(null);
			listOfBorrowingDetails.get(i).getBooks().getTitles().setBookses(null);
			listOfBorrowingDetails.get(i).getBooks().getTitles().setPublishers(null);
			listOfBorrowingDetails.get(i).getBooks().getTitles().setTitleauthorses(null);
			listOfBorrowingDetails.get(i).getBooks().getTitles().setTitlecategorieses(null);
			listOfBorrowingDetails.get(i).getBooks().getTitles().setCoursebookses(null);
			
			listOfBorrowingDetails.get(i).setReturndetailses(null);
		}
		return listOfBorrowingDetails;
	}
	//Get By Id
	@RequestMapping(value = "/getBorrowingDetailsREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public BorrowingDetails getBorrowingDetailsByIdREST(@PathVariable int id) {
		BorrowingDetails borrowingDetail= borrowingDetailService.getBorrowingDetails(id);
		borrowingDetail.getBorrowings().setBorrowingdetailses(null);
		borrowingDetail.getBorrowings().getBorrowers().setBorrowingses(null);
		borrowingDetail.getBorrowings().getBorrowers().setReturningses(null);
		borrowingDetail.getBorrowings().getBorrowers().getBorrowertype().setBorrowerses(null);
		
		borrowingDetail.getBooks().setBorrowingdetailses(null);
		borrowingDetail.getBooks().getTitles().setBookses(null);
		borrowingDetail.getBooks().getTitles().setPublishers(null);
		borrowingDetail.getBooks().getTitles().setTitleauthorses(null);
		borrowingDetail.getBooks().getTitles().setTitlecategorieses(null);
		borrowingDetail.getBooks().getTitles().setCoursebookses(null);
		
		borrowingDetail.setReturndetailses(null);
		return borrowingDetail;
	}
	//Delete Request
	@RequestMapping(value = "/deleteBorrowingDetailsREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<BorrowingDetails> deleteBorrowingDetails(@PathVariable("id") int id) {
		borrowingDetailService.deleteBorrowingDetails(id);
		List<BorrowingDetails> listOfBorrowingDetails = borrowingDetailService.getAllBorrowingDetails();
		for (int i = 0; i < listOfBorrowingDetails.size(); i++) {
			listOfBorrowingDetails.get(i).getBorrowings().setBorrowingdetailses(null);
			listOfBorrowingDetails.get(i).getBorrowings().getBorrowers().setBorrowingses(null);
			listOfBorrowingDetails.get(i).getBorrowings().getBorrowers().setReturningses(null);
			listOfBorrowingDetails.get(i).getBorrowings().getBorrowers().getBorrowertype().setBorrowerses(null);
			
			listOfBorrowingDetails.get(i).getBooks().setBorrowingdetailses(null);
			listOfBorrowingDetails.get(i).getBooks().getTitles().setBookses(null);
			listOfBorrowingDetails.get(i).getBooks().getTitles().setPublishers(null);
			listOfBorrowingDetails.get(i).getBooks().getTitles().setTitleauthorses(null);
			listOfBorrowingDetails.get(i).getBooks().getTitles().setTitlecategorieses(null);
			listOfBorrowingDetails.get(i).getBooks().getTitles().setCoursebookses(null);
			
			listOfBorrowingDetails.get(i).setReturndetailses(null);
		}
		return listOfBorrowingDetails; 

	}
	//add Request
	@RequestMapping(value = "/addBorrowingDetailsREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public BorrowingDetails addBorrowingDetailsREST(@RequestParam("isReturn") boolean isReturn,
			@RequestParam("borrowingId") Integer borrowingId,
			@RequestParam("bookId") Integer bookId)
			throws ParseException {

		Borrowings borrowing = new Borrowings();
		borrowing.setBorrowingid(borrowingId);
		Books book = new Books();
		book.setBookid(bookId);
		BorrowingDetails borrowingDetail= new BorrowingDetails(isReturn);
		borrowingDetail.setBorrowings(borrowing);
		borrowingDetail.setBooks(book);
		return borrowingDetailService.addBorrowingDetailsREST(borrowingDetail);
	}
	//Update Request
	@RequestMapping(value = "/updateBorrowingDetailsREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public BorrowingDetails updateBorrowingDetailsREST(
			@RequestParam("bordetailId") Integer bordetailId,
			@RequestParam("isReturn") boolean isReturn,
			@RequestParam(value="borrowingId",required=false) Integer borrowingId,
			@RequestParam(value="bookId",required=false) Integer bookId)
			throws ParseException {

		Borrowings borrowing = new Borrowings();
		borrowing.setBorrowingid(borrowingId);
		Books book = new Books();
		book.setBookid(bookId);
		BorrowingDetails borrowingDetail= new BorrowingDetails(bordetailId,isReturn);
		borrowingDetail.setBorrowings(borrowing);
		borrowingDetail.setBooks(book);
		return borrowingDetailService.updateBorrowingDetailsREST(borrowingDetail);
	}
//	@RequestMapping(value = "/getBorrowingDetailsFromBorrowingId/{id}",method=RequestMethod.GET,produces = "application/json")
//	public List<BorrowingDetails> getBorrowingDetailsFromBorrowingId(@PathVariable int id) {
//		List<BorrowingDetails> listOfBorrowingDetailsFromBorrowingId = borrowingDetailService.getBorrowingDetailsFromBorrowingId(id);
//		return listOfBorrowingDetailsFromBorrowingId;
//	}
	//get from borrowing id
	@RequestMapping(value = "/getBorrowingDetailsFromBorrowingId/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<BorrowingDetails> getBorrowingDetailsFromBorrowingId(@PathVariable int id) {
		List<BorrowingDetails> listOfBorrowingDetailsFromBorrowingId = borrowingDetailService.getBorrowingDetailsFromBorrowingId(id);
		for (int i = 0; i < listOfBorrowingDetailsFromBorrowingId.size(); i++) {
			listOfBorrowingDetailsFromBorrowingId.get(i).getBorrowings().setBorrowingdetailses(null);
			listOfBorrowingDetailsFromBorrowingId.get(i).getBorrowings().getBorrowers().setBorrowingses(null);
			listOfBorrowingDetailsFromBorrowingId.get(i).getBorrowings().getBorrowers().setReturningses(null);
			listOfBorrowingDetailsFromBorrowingId.get(i).getBorrowings().getBorrowers().getBorrowertype().setBorrowerses(null);
			
			listOfBorrowingDetailsFromBorrowingId.get(i).getBooks().setBorrowingdetailses(null);
			listOfBorrowingDetailsFromBorrowingId.get(i).getBooks().getTitles().setBookses(null);
			listOfBorrowingDetailsFromBorrowingId.get(i).getBooks().getTitles().setPublishers(null);
			listOfBorrowingDetailsFromBorrowingId.get(i).getBooks().getTitles().setTitleauthorses(null);
			listOfBorrowingDetailsFromBorrowingId.get(i).getBooks().getTitles().setTitlecategorieses(null);
			listOfBorrowingDetailsFromBorrowingId.get(i).getBooks().getTitles().setCoursebookses(null);
			
			listOfBorrowingDetailsFromBorrowingId.get(i).setReturndetailses(null);
		}
		return listOfBorrowingDetailsFromBorrowingId;
	}
}
