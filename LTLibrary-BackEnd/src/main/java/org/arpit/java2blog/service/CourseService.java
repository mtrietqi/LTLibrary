package org.arpit.java2blog.service;

import java.util.List;

import org.arpit.java2blog.dao.CourseDAO;
import org.arpit.java2blog.model.Courses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("courseService")
public class CourseService {

	@Autowired
	CourseDAO courseDAO;
	
	@Transactional
	public List<Courses> getAllCourses() {
		return courseDAO.getAllCourses();
	}

	@Transactional
	public Courses getCourses(int id) {
		return courseDAO.getCourses(id);
	}

	@Transactional
	public void addCourses(Courses course) {
		courseDAO.addCourses(course);
	}

	@Transactional
	public void updateCourses(Courses course) {
		courseDAO.updateCourses(course);

	}

	@Transactional
	public void deleteCourses(int id) {
		courseDAO.deleteCourses(id);
	}
}
