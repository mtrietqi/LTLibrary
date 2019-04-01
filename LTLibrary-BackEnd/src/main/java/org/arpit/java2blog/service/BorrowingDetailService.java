package org.arpit.java2blog.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.arpit.java2blog.dao.BorrowingDetailDAO;
import org.arpit.java2blog.model.Borrowers;
import org.arpit.java2blog.model.BorrowingDetails;
import org.arpit.java2blog.model.TitleCategories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("borrowingDetailService")
public class BorrowingDetailService {

	@Autowired
	BorrowingDetailDAO borrowingDetailDAO;
	
	@Transactional
	public List<BorrowingDetails> getAllBorrowingDetails() {
		return borrowingDetailDAO.getAllBorrowingDetails();
	}

	@Transactional
	public BorrowingDetails getBorrowingDetails(int id) {
		return borrowingDetailDAO.getBorrowingDetails(id);
	}

	@Transactional
	public void addBorrowingDetails(BorrowingDetails borrowingDetail) {
		borrowingDetailDAO.addBorrowingDetails(borrowingDetail);
	}
	@Transactional
	public BorrowingDetails addBorrowingDetailsREST(BorrowingDetails borrowingDetail) {
		return borrowingDetailDAO.addBorrowingDetails(borrowingDetail);
	}

	@Transactional
	public void updateBorrowingDetails(BorrowingDetails borrowingDetail) {
		borrowingDetailDAO.updateBorrowingDetails(borrowingDetail);

	}
	@Transactional
	public BorrowingDetails updateBorrowingDetailsREST(BorrowingDetails borrowingDetail) {
		return borrowingDetailDAO.updateBorrowingDetailsREST(borrowingDetail);
	}

	@Transactional
	public void deleteBorrowingDetails(int id) {
		borrowingDetailDAO.deleteBorrowingDetails(id);
	}
	@Transactional
	public List<BorrowingDetails> getBorrowingDetailsFromBorrowingId(int id)
	{
//		Set<BorrowingDetails> result = new HashSet<BorrowingDetails>(borrowingDetailDAO.getBorrowingDetailsFromBorrowingId(id));
		return borrowingDetailDAO.getBorrowingDetailsFromBorrowingId(id);
	}
}
