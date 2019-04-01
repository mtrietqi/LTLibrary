package org.arpit.java2blog.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import org.arpit.java2blog.dao.LibrarianDAO;
import org.arpit.java2blog.model.Librarians;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LibrarianDAOTest {
	private Librarians librarian;
	
	private LibrarianDAO libDAO;
	private  SessionFactory sessionFactory;
	
	@BeforeMethod
	public void init() {
		librarian= new Librarians();
		librarian.setFirstname("Long");
		librarian.setLastname("Bao");
		libDAO= new LibrarianDAO();
		
		 Configuration configuration = new Configuration().configure(LibrarianDAOTest.class.getResource("/spring-servlet.xml"));
		
		 configuration.addAnnotatedClass(org.arpit.java2blog.model.Librarians.class);

         StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
         serviceRegistryBuilder.applySettings(configuration.getProperties());
         ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
         sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		

         sessionFactory.openSession();
        
		libDAO.setSessionFactory(sessionFactory);
		
	}
	
	@Test
	public void addLibrarian() {
		libDAO.setSessionFactory(sessionFactory);
//		if(libDAO == null) {
//			System.out.println("LIBDAO is null");
//		}
//		else
//			System.out.println("Lib dao is not null");
//		
//		System.out.println(libDAO.getSessionFactory()==null);
		try {
//		System.out.println(librarian.getFirstname());
			libDAO.addLibrariansTEST(librarian);
		
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		assertEquals(libDAO.getLibrariansTEST(librarian.getLibid()).getLibid(),librarian.getLibid());
	}
	
	@Test
	public void getAllLibrarian() {
		libDAO.setSessionFactory(sessionFactory);
		
		Integer existingCount =libDAO.getAllLibrariansTEST().size();
		
		libDAO.addLibrariansTEST(librarian);
		
		Integer newCount= libDAO.getAllLibrariansTEST().size();
		System.out.println(newCount);
		assertNotEquals(existingCount,newCount);
	}
	
	@Test
	public void getLibrarianById()
	{
		libDAO.setSessionFactory(sessionFactory);
		librarian = libDAO.addLibrariansTEST(librarian);
		Librarians l = libDAO.getLibrariansTEST(librarian.getLibid());
		assertEquals(librarian.getLibid(), l.getLibid());
		
	}
	@Test
	public void deleteLibrarian() {
		libDAO.setSessionFactory(sessionFactory);
		librarian = libDAO.addLibrariansTEST(librarian);
		Librarians l = libDAO.getLibrariansTEST(librarian.getLibid());
		libDAO.deleteLibrariansTEST(librarian.getLibid());
		Librarians l1 = libDAO.getLibrariansTEST(librarian.getLibid());
		assertTrue(l1==null);
		
	}
	@Test
	public void updateLibrarian() {
		libDAO.setSessionFactory(sessionFactory);
		librarian = libDAO.addLibrariansTEST(librarian);
		Librarians libTemp= libDAO.getLibrariansTEST(librarian.getLibid());
		
		libTemp.setFirstname("triet");
		libTemp.setLastname("Minh");
		
		Librarians updateLib= libDAO.updateLibrariansRESTTEST(libTemp);
		
		assertNotEquals(librarian.getFirstname(), updateLib.getFirstname());
		assertNotEquals(librarian.getLastname(), updateLib.getLastname());
		
	}
	
	
}
