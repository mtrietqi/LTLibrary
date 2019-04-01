package org.arpit.java2blog.service;

import java.util.List;

import org.arpit.java2blog.dao.BorrowerDAO;
import org.arpit.java2blog.model.Borrowers;
import org.arpit.java2blog.model.Borrowers;
import org.arpit.java2blog.model.Borrowers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("borrowerService")
public class BorrowerService {

	@Autowired
	BorrowerDAO borrowerDAO;
	
	@Transactional
	public List<Borrowers> getAllBorrowers() {
		return borrowerDAO.getAllBorrowers();
	}

	@Transactional
	public Borrowers getBorrowers(int id) {
		return borrowerDAO.getBorrowers(id);
	}

	@Transactional
	public void addBorrowers(Borrowers borrower) {
		borrowerDAO.addBorrowers(borrower);
	}
	@Transactional
	public Borrowers addBorrowersREST(Borrowers borrower) {
		return borrowerDAO.addBorrowers(borrower);
	}

	@Transactional
	public void updateBorrowers(Borrowers borrower) {
		borrowerDAO.updateBorrowers(borrower);

	}
	@Transactional
	public Borrowers updateBorrowersREST(Borrowers borrower) {
		return borrowerDAO.updateBorrowersREST(borrower);
	}

	@Transactional
	public void deleteBorrowers(int id) {
		borrowerDAO.deleteBorrowers(id);
	}
}
