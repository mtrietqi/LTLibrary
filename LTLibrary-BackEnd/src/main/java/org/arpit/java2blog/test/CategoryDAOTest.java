package org.arpit.java2blog.test;

import static org.testng.Assert.assertEquals;

import org.arpit.java2blog.dao.AuthorDAO;
import org.arpit.java2blog.dao.CategoryDAO;
import org.arpit.java2blog.model.Authors;
import org.arpit.java2blog.model.Categories;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

public class CategoryDAOTest
{
	private Categories existingCategory; 
	private Categories newCategory; 
	private CategoryDAO categoryDAO;
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
        
		categoryDAO = new CategoryDAO();
		categoryDAO.setSessionFactory(sessionFactory);
        
		existingCategory= new Categories();
		existingCategory.setCatname("Kiem hiep");
		existingCategory.setTitlecategorieses(null);
		
		newCategory= new Categories();
		newCategory.setCatname("Kiem hiep");
		newCategory.setTitlecategorieses(null);
	}
	
	@Test
	public void addNewCategory()
	{
		categoryDAO.addCategoriesTEST(newCategory);
		assertEquals(categoryDAO.getCategoriesTEST(newCategory.getCatid()).getCatid(),newCategory.getCatid());
	}
	
	@Test
	public void getAllCategory()
	{
		int existingCount = categoryDAO.getAllCategoriesTEST().size();
		categoryDAO.addCategoriesTEST(newCategory);
		int currentCount = categoryDAO.getAllCategoriesTEST().size();
		assertEquals(existingCount+1,currentCount);
	}
	
	@Test
	public void getCategoryById()
	{
		Categories before = categoryDAO.addCategoriesTEST(newCategory);
		Categories after = categoryDAO.getCategoriesTEST(before.getCatid());
		assertEquals(before.getCatid(),after.getCatid());
	}
	
	@Test
	public void updateCategory()
	{
		Categories before = categoryDAO.addCategoriesTEST(newCategory);
		before.setCatname("test");
		
		Categories after = categoryDAO.updateCategoriesRESTTEST(before);
		assertEquals(before.getCatname(),after.getCatname());
	}
	
	@Test
	public void deleteAuthor()
	{
		Categories before = categoryDAO.addCategoriesTEST(newCategory);
		categoryDAO.deleteCategoriesTEST(before.getCatid());
		Categories after = categoryDAO.getCategoriesTEST(before.getCatid());
		assertEquals(after,null);
	}
}


