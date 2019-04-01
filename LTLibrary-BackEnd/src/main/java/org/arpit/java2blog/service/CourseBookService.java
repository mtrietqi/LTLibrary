package org.arpit.java2blog.service;

import java.util.List;

import org.arpit.java2blog.dao.CourseBookDAO;
import org.arpit.java2blog.model.Coursebooks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("courseBookService")
public class CourseBookService {

	@Autowired
	CourseBookDAO courseBookDAO;
	
	@Transactional
	public List<Coursebooks> getAllCoursebooks() {
		return courseBookDAO.getAllCoursebooks();
	}

	@Transactional
	public Coursebooks getCoursebooks(int id) {
		return courseBookDAO.getCoursebooks(id);
	}

	@Transactional
	public void addCoursebooks(Coursebooks courseBook) {
		courseBookDAO.addCoursebooks(courseBook);
	}

	@Transactional
	public void updateCoursebooks(Coursebooks courseBook) {
		courseBookDAO.updateCoursebooks(courseBook);

	}

	@Transactional
	public void deleteCoursebooks(int id) {
		courseBookDAO.deleteCoursebooks(id);
	}
}
