package org.arpit.java2blog.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.swing.JOptionPane;

import org.arpit.java2blog.model.Borrowertype;
import org.arpit.java2blog.model.Borrowers;
import org.arpit.java2blog.model.Borrowers;
import org.arpit.java2blog.model.Borrowers;
import org.arpit.java2blog.model.Borrowertype;
import org.arpit.java2blog.model.Borrowers;
import org.arpit.java2blog.model.Borrowers;
import org.arpit.java2blog.service.BorrowerTypeService;
import org.arpit.java2blog.util.FileUpload;
import org.arpit.java2blog.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class BorrowersRESTController {
	@Autowired
	BorrowerService borrowerService;

	@Autowired
	private ServletContext servletContext;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	// Get All Request
	@RequestMapping(value = "/getAllBorrowersREST", method = RequestMethod.GET, produces = "application/json")
	public List<Borrowers> getBorrowers() {
		List<Borrowers> listOfBorrowers = borrowerService.getAllBorrowers();
		for (int i = 0; i < listOfBorrowers.size(); i++) {
			listOfBorrowers.get(i).getBorrowertype().setBorrowerses(null);
			listOfBorrowers.get(i).setBorrowingses(null);
			listOfBorrowers.get(i).setReturningses(null);
		}
		return listOfBorrowers;
	}
	//add Request
	@RequestMapping(value = "/addBorrowersREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Borrowers addBorrowersREST(@RequestParam("file") MultipartFile file,
			@RequestParam("borrowerTypeId") Integer borrowerTypeId,
			@RequestParam("librariansByCreatedby") Integer librariansByCreatedby,
			@RequestParam("librariansByUpdatedby") Integer librariansByUpdatedby, @RequestParam("schoolId") String schoolId,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("barCode") String barCode, @RequestParam("gender") boolean gender,
			@RequestParam("email") String email, @RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("address") String address, @RequestParam("status") boolean status,
			@RequestParam("createdDate") String createdDate, @RequestParam("updatedDate") String updatedDate)
			throws ParseException {

		Borrowertype bt = new Borrowertype();
		bt.setBorrowertypeid(borrowerTypeId);
		Borrowers borrower = new Borrowers(librariansByCreatedby, librariansByUpdatedby, schoolId, firstName, lastName,
				barCode, gender, email, phoneNumber, address, status, formatter.parse(createdDate),
				formatter.parse(updatedDate));
		borrower.setBorrowertype(bt);

		// p.setImagePath(imagePath);
		Borrowers newBorrowers = borrowerService.addBorrowersREST(borrower);

		// upload file
		FileUpload fileUpload = new FileUpload();
		fileUpload.processFileName(file, servletContext.getRealPath("/"),"WEB-INF\\borrowerImages\\", newBorrowers.getBorrowerid());
		String fileName = file.getOriginalFilename();
		fileName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		String avatarPath = newBorrowers.getBorrowerid() + fileName;
		newBorrowers.setAvatar(avatarPath);
		borrowerService.updateBorrowers(newBorrowers);

		return newBorrowers;
	}
	//Delete Request
	@RequestMapping(value = "/deleteBorrowersREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<Borrowers> deleteBorrowers(@PathVariable("id") int id) {
		Borrowers b = borrowerService.getBorrowers(id);
		// delete person image
		String fileName = servletContext.getRealPath("/") + FileUpload.UPLOAD_FILE_PATH + b.getAvatar();
		if (b.getAvatar() != null) {
			File f = new File(fileName);
			if (f.isFile())
				f.delete();
		}
		borrowerService.deleteBorrowers(id);
		List<Borrowers> listOfBorrowers = borrowerService.getAllBorrowers();
		for (int i = 0; i < listOfBorrowers.size(); i++) {
			listOfBorrowers.get(i).getBorrowertype().setBorrowerses(null);
			listOfBorrowers.get(i).setBorrowingses(null);
			listOfBorrowers.get(i).setReturningses(null);
		}
		return listOfBorrowers;

	}
	//Get By Id
	@RequestMapping(value = "/getBorrowersREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public Borrowers getBorrowersByIdREST(@PathVariable int id) {
		Borrowers b = borrowerService.getBorrowers(id);
		b.getBorrowertype().setBorrowerses(null);
		b.setBorrowingses(null);
		b.setReturningses(null);
		return b;
	}
	//Update Request
	@RequestMapping(value = "/updateBorrowersREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Borrowers updateBorrowersREST(@RequestParam("file") MultipartFile file,
			@RequestParam("borrowerId") Integer borrowerId,
			@RequestParam("borrowerTypeId") String borrowerTypeId,
			@RequestParam("librariansByCreatedby") Integer librariansByCreatedby,
			@RequestParam("librariansByUpdatedby") Integer librariansByUpdatedby, 
			@RequestParam("schoolId") String schoolId,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("barCode") String barCode, @RequestParam("gender") boolean gender,
			@RequestParam("email") String email, @RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("address") String address, @RequestParam("status") boolean status,
			@RequestParam("createdDate") String createdDate, @RequestParam("updatedDate") String updatedDate)
			throws ParseException {
		// upload file

		FileUpload fileUpload = new FileUpload();
		fileUpload.processFileName(file, servletContext.getRealPath("/"),"WEB-INF\\borrowerImages\\", borrowerId);
		String fileName = file.getOriginalFilename();
		fileName = fileName.substring(fileName.lastIndexOf("."));
	
		Borrowertype bt = new Borrowertype();
		bt.setBorrowertypeid(Integer.parseInt(borrowerTypeId));
		Borrowers currentBorrowers = borrowerService.getBorrowers(borrowerId);
		Borrowers b = new Borrowers(borrowerId,librariansByCreatedby, librariansByUpdatedby, schoolId, firstName, lastName,
				barCode, gender, email, phoneNumber, address, status, formatter.parse(createdDate),
				formatter.parse(updatedDate));;
		b.setBorrowertype(bt);
		String avatarPath = b.getBorrowerid()+fileName;
		System.out.println(avatarPath);
		if (file != null && file.getSize() > 0)
			b.setAvatar(avatarPath);
		else
			b.setAvatar(currentBorrowers.getAvatar());
		return borrowerService.updateBorrowersREST(b);
	}
	//Update without Image Request
	@RequestMapping(value = "/updateBorrowersWithoutImageREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Borrowers updateBorrowersWithoutImageREST(
			@RequestParam("borrowerId") Integer borrowerId,
			@RequestParam("borrowerTypeId") String borrowerTypeId,
			@RequestParam("librariansByCreatedby") Integer librariansByCreatedby,
			@RequestParam("librariansByUpdatedby") Integer librariansByUpdatedby, 
			@RequestParam("schoolId") String schoolId,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("barCode") String barCode, @RequestParam("gender") boolean gender,
			@RequestParam("email") String email, @RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("address") String address, @RequestParam("status") boolean status,
			@RequestParam("createdDate") String createdDate, @RequestParam("updatedDate") String updatedDate)
			throws ParseException {
		
		
		Borrowertype bt = new Borrowertype();
		bt.setBorrowertypeid(Integer.parseInt(borrowerTypeId));
		Borrowers currentBorrowers = borrowerService.getBorrowers(borrowerId);
		Borrowers b = new Borrowers(borrowerId,librariansByCreatedby, librariansByUpdatedby, schoolId, firstName, lastName,
				barCode, gender, email, phoneNumber, address, status, formatter.parse(createdDate),
				formatter.parse(updatedDate));;
		b.setBorrowertype(bt);
		b.setAvatar(currentBorrowers.getAvatar());
		return borrowerService.updateBorrowersREST(b);
	}
	//Add without Image
	@RequestMapping(value = "/addBorrowersWithoutImageREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Borrowers addBorrowersWithoutREST(
			@RequestParam("borrowerTypeId") Integer borrowerTypeId,
			@RequestParam("librariansByCreatedby") Integer librariansByCreatedby,
			@RequestParam("librariansByUpdatedby") Integer librariansByUpdatedby, @RequestParam("schoolId") String schoolId,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("barCode") String barCode, @RequestParam("gender") boolean gender,
			@RequestParam("email") String email, @RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("address") String address, @RequestParam("status") boolean status,
			@RequestParam("createdDate") String createdDate, @RequestParam("updatedDate") String updatedDate)
			throws ParseException {

		Borrowertype bt = new Borrowertype();
		bt.setBorrowertypeid(borrowerTypeId);
		Borrowers borrower = new Borrowers(librariansByCreatedby, librariansByUpdatedby, schoolId, firstName, lastName,
				barCode, gender, email, phoneNumber, address, status, formatter.parse(createdDate),
				formatter.parse(updatedDate));
		borrower.setBorrowertype(bt);

		// p.setImagePath(imagePath);
		Borrowers newBorrowers = borrowerService.addBorrowersREST(borrower);
		return newBorrowers;
	}

}