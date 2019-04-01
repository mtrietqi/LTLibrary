package org.arpit.java2blog.service;

import java.util.Date;
import java.util.List;

import org.arpit.java2blog.dao.BorrowingDAO;
import org.arpit.java2blog.model.Borrowings;
import org.arpit.java2blog.model.Borrowings;
import org.arpit.java2blog.model.Borrowings;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("borrowingService")
public class BorrowingService {

	@Autowired
	BorrowingDAO borrowingDAO;
	
	@Transactional
	public List<Borrowings> getAllBorrowings() {
		return borrowingDAO.getAllBorrowings();
	}

	@Transactional
	public Borrowings getBorrowings(int id) {
		return borrowingDAO.getBorrowings(id);
	}

	@Transactional
	public void addBorrowings(Borrowings borrowing) {
		borrowingDAO.addBorrowings(borrowing);
	}
//	@Transactional
//	public Borrowings addBorrowingsREST(Integer borrowerId, Date borDate,Integer librariansByUpdatedby,Integer librariansByCreatedby) {
//		return borrowingDAO.addBorrowingsREST(borrowerId, borDate, librariansByUpdatedby, librariansByCreatedby);
//	}
	

	@Transactional
	public Borrowings addBorrowingsREST(Borrowings borrowing) {
		return borrowingDAO.addBorrowings(borrowing);
	}
	@Transactional
	public void updateBorrowings(Borrowings borrowing) {
		borrowingDAO.updateBorrowings(borrowing);

	}
	@Transactional
	public Borrowings updateBorrowingsREST(Borrowings borrowing) {
		return borrowingDAO.updateBorrowingsREST(borrowing);
	}

	@Transactional
	public void deleteBorrowings(int id) {
		borrowingDAO.deleteBorrowings(id);
	}
}
