package org.arpit.java2blog.controller;

import java.util.List;
import org.arpit.java2blog.model.Borrowertype;
import org.arpit.java2blog.service.BorrowerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BorrowerTypeRESTController 
{
	@Autowired
	BorrowerTypeService borrowerTypeService;
	// Get All Request
	@RequestMapping(value = "/getAllBorrowerTypeREST",method=RequestMethod.GET,produces = "application/json")
	public List<Borrowertype> getBorrowerType()
	{
		List<Borrowertype> borrowerType = borrowerTypeService.getAllBorrowertype();
		for (int i = 0; i < borrowerType.size(); i++)
		{
			borrowerType.get(i).setBorrowerses(null);
		}
		return borrowerType;
	}
	//Delete Request
	@RequestMapping(value = "/deleteBorrowerTypeREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<Borrowertype> deleteBorrowerType(@PathVariable("id") int id) {
		borrowerTypeService.deleteBorrowertype(id);
		List<Borrowertype> borrowerType = borrowerTypeService.getAllBorrowertype();
		for (int i = 0; i < borrowerType.size(); i++)
		{
			borrowerType.get(i).setBorrowerses(null);
		}
		return borrowerType;	 
	}
	//Get By Id
	@RequestMapping(value = "/getBorrowerTypeByIdREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public Borrowertype getCountryByID(@PathVariable("id") int id) {
		 Borrowertype b = this.borrowerTypeService.getBorrowertype(id);
		 b.setBorrowerses(null);
		 return b;
	}
	//Add Request
	@RequestMapping(value = "/addBorrowerTypeREST", method = RequestMethod.POST, produces = "application/json")
	public Borrowertype addBorrowerType(@RequestBody Borrowertype borrowerType) {	
			return borrowerTypeService.addBorrowertypeREST(borrowerType);
	}
	//Update Request
	@RequestMapping(value = "/updateBorrowerTypeREST", method = RequestMethod.POST, produces = "application/json")
	public Borrowertype updateBorrowerType(@RequestBody Borrowertype borrowerType) {	
			return borrowerTypeService.updateBorrowertypeREST(borrowerType);
	}
}
