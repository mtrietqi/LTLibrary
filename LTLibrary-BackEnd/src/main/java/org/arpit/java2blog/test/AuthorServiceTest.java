package org.arpit.java2blog.test;


import static org.testng.Assert.assertEquals;

import org.arpit.java2blog.dao.AuthorDAO;
import org.arpit.java2blog.model.Authors;
import org.arpit.java2blog.service.AuthorService;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AuthorServiceTest {
	
	private Authors author;
	private AuthorDAO authorDAO;
	private AuthorService authorService;
	private  SessionFactory sessionFactory;
	
	@BeforeMethod
	public void init() {
		author= new Authors();
		authorService= new AuthorService();
		author.setFirstname("Long");
		author.setLastname("Bao");
		authorDAO= new AuthorDAO();
		
		 Configuration configuration = new Configuration().configure(AuthorDAOTest.class.getResource("/spring-servlet.xml"));
		
		 configuration.addAnnotatedClass(org.arpit.java2blog.model.Authors.class);
			configuration.addAnnotatedClass(org.arpit.java2blog.model.TitleAuthors.class);
			configuration.addAnnotatedClass(org.arpit.java2blog.model.Titles.class);
			configuration.addAnnotatedClass(org.arpit.java2blog.model.Publishers.class);
			configuration.addAnnotatedClass(org.arpit.java2blog.model.Books.class);
			configuration.addAnnotatedClass(org.arpit.java2blog.model.Coursebooks.class);
			configuration.addAnnotatedClass(org.arpit.java2blog.model.Courses.class);
			configuration.addAnnotatedClass(org.arpit.java2blog.model.TitleCategories.class);
			configuration.addAnnotatedClass(org.arpit.java2blog.model.Categories.class);
			configuration.addAnnotatedClass(org.arpit.java2blog.model.BorrowingDetails.class);
			configuration.addAnnotatedClass(org.arpit.java2blog.model.Borrowings.class);
			configuration.addAnnotatedClass(org.arpit.java2blog.model.Borrowers.class);
			configuration.addAnnotatedClass(org.arpit.java2blog.model.Borrowertype.class);
			configuration.addAnnotatedClass(org.arpit.java2blog.model.Returndetails.class);
			configuration.addAnnotatedClass(org.arpit.java2blog.model.Returnings.class);

         StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
         serviceRegistryBuilder.applySettings(configuration.getProperties());
         ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
         sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		

         sessionFactory.openSession();
         authorService.setAuthorDao(authorDAO);
         authorDAO.setSessionFactory(sessionFactory);
		
	}
	
	@Test
	public void addLibrarian() {
		authorService.addAuthorsTEST(author);
		assertEquals(authorService.getAuthorsTEST(author.getAuid()).getAuid(),author.getAuid());
	}
	@Test
	public void getAllAuthor()
	{
		int existingCount = authorService.getAllAuthorsTEST().size();
		authorService.addAuthorsTEST(author);
		int currentCount = authorService.getAllAuthorsTEST().size();
		assertEquals(existingCount+1,currentCount);
	}
	
	@Test
	public void getAuthorById()
	{
		Authors before = authorService.addAuthorsRESTTEST(author);
		Authors after = authorService.getAuthorsTEST(before.getAuid());
		assertEquals(before.getAuid(),after.getAuid());
	}
	
	@Test
	public void updateAuthor()
	{
		Authors before = authorService.addAuthorsRESTTEST(author);
		before.setFirstname("My author");
		before.setCountry("Singapore");
		Authors after = authorService.updateAuthorsRESTTEST(before);
		assertEquals(before.getFirstname(),after.getFirstname());
		assertEquals(before.getCountry(),after.getCountry());
		assertEquals(before.getLastname(),after.getLastname());
		assertEquals(before.getGender(),after.getGender());
	}
	
	@Test
	public void deleteAuthor()
	{
		Authors before = authorService.addAuthorsRESTTEST(author);
		authorService.deleteAuthorsTEST(before.getAuid());
		Authors after = authorService.getAuthorsTEST(before.getAuid());
		assertEquals(after,null);
	}

}
