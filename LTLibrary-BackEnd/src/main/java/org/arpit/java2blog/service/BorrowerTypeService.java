package org.arpit.java2blog.service;

import java.util.List;

import org.arpit.java2blog.dao.BorrowerTypeDAO;
import org.arpit.java2blog.model.Borrowertype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("borrowerTypeService")
public class BorrowerTypeService {

	@Autowired
	BorrowerTypeDAO borrowerTypeDAO;
	
	@Transactional
	public List<Borrowertype> getAllBorrowertype() {
		return borrowerTypeDAO.getAllBorrowertype();
	}

	@Transactional
	public Borrowertype getBorrowertype(int id) {
		return borrowerTypeDAO.getBorrowertype(id);
	}

	@Transactional
	public void addBorrowertype(Borrowertype borrowerType) {
		borrowerTypeDAO.addBorrowertype(borrowerType);
	}
	
	@Transactional
	public Borrowertype addBorrowertypeREST(Borrowertype borrowerType) {
		return borrowerTypeDAO.addBorrowertype(borrowerType);
	}

	@Transactional
	public void updateBorrowertype(Borrowertype borrowerType) {
		borrowerTypeDAO.updateBorrowertype(borrowerType);

	}
	@Transactional
	public Borrowertype updateBorrowertypeREST(Borrowertype borrowerType) {
		return borrowerTypeDAO.updateBorrowertypeREST(borrowerType);
	}

	@Transactional
	public void deleteBorrowertype(int id) {
		borrowerTypeDAO.deleteBorrowertype(id);
	}
}
