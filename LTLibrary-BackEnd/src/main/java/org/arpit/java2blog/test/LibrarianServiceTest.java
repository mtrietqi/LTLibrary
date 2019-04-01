package org.arpit.java2blog.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import org.arpit.java2blog.dao.LibrarianDAO;
import org.arpit.java2blog.model.Librarians;
import org.arpit.java2blog.service.LibrarianService;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LibrarianServiceTest {
	private Librarians librarian;
	private LibrarianDAO libDAO;
	private LibrarianService libService;
	private  SessionFactory sessionFactory;
	
	@BeforeMethod
	public void init() {
		librarian= new Librarians();
		libService= new LibrarianService();
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
         libService.setLibrarianDAO(libDAO);
         libDAO.setSessionFactory(sessionFactory);
		
	}
	
	@Test
	public void addLibrarian() {

		try {
		libService.addLibrariansTEST(librarian);
		
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		assertEquals(libService.getLibrariansTEST(librarian.getLibid()).getLibid(),librarian.getLibid());
	}
	@Test
	public void getAllLibrarian() {

		
		Integer existingCount =libService.getAllLibrariansTEST().size();
		
		libService.addLibrariansTEST(librarian);
		
		Integer newCount= libService.getAllLibrariansTEST().size();
		assertNotEquals(existingCount,newCount);
	}
	@Test
	public void getLibrarianById() {
		librarian = libService.addLibrariansRESTTEST(librarian);
		Librarians l = libService.getLibrariansTEST(librarian.getLibid());
		assertEquals(librarian.getLibid(), l.getLibid());
	}
	@Test
	public void deleteLibrarian() {
		librarian = libService.addLibrariansRESTTEST(librarian);
		Librarians l = libService.getLibrariansTEST(librarian.getLibid());
		libService.deleteLibrariansTEST(librarian.getLibid());
		Librarians l1 = libService.getLibrariansTEST(librarian.getLibid());
		assertTrue(l1==null);	
	}
	
	@Test
	public void updateLibrarian() {
		librarian = libService.addLibrariansRESTTEST(librarian);
		Librarians libTemp= libService.getLibrariansTEST(librarian.getLibid());
		
		libTemp.setFirstname("triet");
		libTemp.setLastname("Minh");
		
		Librarians updateLib= libService.updateLibrariansRESTTEST(libTemp);
		
		assertNotEquals(librarian.getFirstname(), updateLib.getFirstname());
		assertNotEquals(librarian.getLastname(), updateLib.getLastname());
		
	}
	
	
}
