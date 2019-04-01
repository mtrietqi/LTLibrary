package org.arpit.java2blog.test;

import static org.testng.Assert.assertEquals;

import org.arpit.java2blog.dao.AuthorDAO;
import org.arpit.java2blog.model.Authors;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

public class AuthorDAOTest
{
	private Authors existingAuthor; 
	private Authors newAuthor; 
	private AuthorDAO authorDAO;
	private static final int EXISTING_AUTHOR_ID = 4;
	private static final int NEW_AUTHOR_ID = 5;
	private  SessionFactory sessionFactory;
	
	
	@BeforeMethod
	public void init()
	{
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
        
		authorDAO = new AuthorDAO();
		authorDAO.setSessionFactory(sessionFactory);
        
		existingAuthor = new Authors();
		existingAuthor.setFirstname("Author");
		existingAuthor.setLastname("4");
		existingAuthor.setGender(false);
		existingAuthor.setCountry("Vietnam");
		existingAuthor.setTitleauthorses(null);
		
		newAuthor = new Authors();
		newAuthor.setFirstname("Author");
		newAuthor.setLastname("5");
		newAuthor.setGender(false);
		newAuthor.setCountry("Vietnam");
		newAuthor.setTitleauthorses(null);
	}
	
	@Test
	public void addNewAuthor()
	{
		authorDAO.addAuthorsTEST(newAuthor);
		assertEquals(authorDAO.getAuthorsTEST(newAuthor.getAuid()).getAuid(),newAuthor.getAuid());
	}
	
	@Test
	public void getAllAuthor()
	{
		int existingCount = authorDAO.getAllAuthorsTEST().size();
		authorDAO.addAuthorsTEST(newAuthor);
		int currentCount = authorDAO.getAllAuthorsTEST().size();
		assertEquals(existingCount+1,currentCount);
	}
	
	@Test
	public void getAuthorById()
	{
		Authors before = authorDAO.addAuthorsTEST(newAuthor);
		Authors after = authorDAO.getAuthorsTEST(before.getAuid());
		assertEquals(before.getAuid(),after.getAuid());
	}
	
	@Test
	public void updateAuthor()
	{
		Authors before = authorDAO.addAuthorsTEST(newAuthor);
		before.setFirstname("My author");
		before.setCountry("Singapore");
		Authors after = authorDAO.updateAuthorsRESTTEST(before);
		assertEquals(before.getFirstname(),after.getFirstname());
		assertEquals(before.getCountry(),after.getCountry());
		assertEquals(before.getLastname(),after.getLastname());
		assertEquals(before.getGender(),after.getGender());
	}
	
	@Test
	public void deleteAuthor()
	{
		Authors before = authorDAO.addAuthorsTEST(newAuthor);
		authorDAO.deleteAuthorsTEST(before.getAuid());
		Authors after = authorDAO.getAuthorsTEST(before.getAuid());
		assertEquals(after,null);
	}
}


