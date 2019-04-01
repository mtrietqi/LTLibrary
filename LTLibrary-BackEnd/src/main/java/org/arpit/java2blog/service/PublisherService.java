package org.arpit.java2blog.service;

import java.util.List;

import org.arpit.java2blog.dao.PublisherDAO;
import org.arpit.java2blog.model.Publishers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("publisherService")
public class PublisherService {

	@Autowired
	PublisherDAO publisherDAO;
	
	@Transactional
	public List<Publishers> getAllPublishers() {
		return publisherDAO.getAllPublishers();
	}

	@Transactional
	public Publishers getPublishers(int id) {
		return publisherDAO.getPublishers(id);
	}

	@Transactional
	public void addPublishers(Publishers publisher) {
		publisherDAO.addPublishers(publisher);
	}
	@Transactional
	public Publishers addPublishersREST(Publishers publisher) {
		return publisherDAO.addPublishers(publisher);
	}

	@Transactional
	public void updatePublishers(Publishers publisher) {
		publisherDAO.updatePublishers(publisher);

	}
	@Transactional
	public Publishers updatePublishersREST(Publishers publisher) {
		return publisherDAO.updatePublishersREST(publisher);
	}

	@Transactional
	public void deletePublishers(int id) {
		publisherDAO.deletePublishers(id);
	}
}
