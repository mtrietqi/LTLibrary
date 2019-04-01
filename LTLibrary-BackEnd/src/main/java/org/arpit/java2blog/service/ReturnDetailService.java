package org.arpit.java2blog.service;

import java.util.List;

import org.arpit.java2blog.dao.ReturnDetailDAO;
import org.arpit.java2blog.model.Borrowers;
import org.arpit.java2blog.model.BorrowingDetails;
import org.arpit.java2blog.model.Returndetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("returnDetailService")
public class ReturnDetailService {

	@Autowired
	ReturnDetailDAO returnDetailDAO;
	
	@Transactional
	public List<Returndetails> getAllReturndetails() {
		return returnDetailDAO.getAllReturndetails();
	}

	@Transactional
	public Returndetails getReturndetails(int id) {
		return returnDetailDAO.getReturndetails(id);
	}

	@Transactional
	public void addReturndetails(Returndetails returnDetail) {
		returnDetailDAO.addReturndetails(returnDetail);
	}
	@Transactional
	public Returndetails addReturndetailsREST(Returndetails returnDetail) {
		return returnDetailDAO.addReturndetails(returnDetail);
	}

	@Transactional
	public void updateReturndetails(Returndetails returnDetail) {
		returnDetailDAO.updateReturndetails(returnDetail);

	}
	@Transactional
	public Returndetails updateReturndetailsREST(Returndetails returnDetail) {
		return returnDetailDAO.updateReturndetailsREST(returnDetail);
	}

	@Transactional
	public void deleteReturndetails(int id) {
		returnDetailDAO.deleteReturndetails(id);
	}
	@Transactional
	public List<Returndetails> getReturndetailsFromReturningId(int id)
	{

		return returnDetailDAO.getReturndetailsFromReturningId(id);
	}
}
