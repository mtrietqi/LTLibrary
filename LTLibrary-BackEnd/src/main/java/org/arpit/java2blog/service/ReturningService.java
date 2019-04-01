package org.arpit.java2blog.service;

import java.util.List;

import org.arpit.java2blog.dao.ReturningDAO;
import org.arpit.java2blog.model.Borrowers;
import org.arpit.java2blog.model.Returnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("returningService")
public class ReturningService {

	@Autowired
	ReturningDAO returningDAO;
	
	@Transactional
	public List<Returnings> getAllReturnings() {
		return returningDAO.getAllReturnings();
	}

	@Transactional
	public Returnings getReturnings(int id) {
		return returningDAO.getReturnings(id);
	}

	@Transactional
	public void addReturnings(Returnings returning) {
		returningDAO.addReturnings(returning);
	}
	@Transactional
	public Returnings addReturningsREST(Returnings returning) {
		return returningDAO.addReturnings(returning);
	}

	@Transactional
	public void updateReturnings(Returnings returning) {
		returningDAO.updateReturnings(returning);

	}
	@Transactional
	public Returnings updateReturningsREST(Returnings returning) {
		return returningDAO.updateReturningsREST(returning);
	}

	@Transactional
	public void deleteReturnings(int id) {
		returningDAO.deleteReturnings(id);
	}
}
