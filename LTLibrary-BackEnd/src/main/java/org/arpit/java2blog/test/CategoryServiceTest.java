package org.arpit.java2blog.test;

import static org.testng.Assert.assertEquals;

import org.arpit.java2blog.dao.CategoryDAO;
import org.arpit.java2blog.model.Categories;
import org.arpit.java2blog.service.CategoryService;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CategoryServiceTest {
	
	private Categories category;
	private CategoryDAO categoryDAO;
	private CategoryService categoryService;
	private SessionFactory sessionFactory;
	
	@BeforeMethod
	public void init() {
		category= new Categories();
		categoryService= new CategoryService();
		categoryDAO= new CategoryDAO();
		
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
        
		category= new Categories();
		category.setCatname("Kiem hiep");
		category.setTitlecategorieses(null);
		
		

         sessionFactory.openSession();
         categoryService.setCategoryDAO(categoryDAO);
         categoryDAO.setSessionFactory(sessionFactory);
		
	}
	
	@Test
	public void addNewCategory()
	{
		categoryService.addCategoriesTEST(category);
		assertEquals(categoryService.getCategoriesTEST(category.getCatid()).getCatid(),category.getCatid());
	}
	
	@Test
	public void getAllCategory()
	{
		int existingCount = categoryService.getAllCategoriesTEST().size();
		categoryService.addCategoriesTEST(category);
		int currentCount = categoryService.getAllCategoriesTEST().size();
		assertEquals(existingCount+1,currentCount);
	}
	
	@Test
	public void getCategoryById()
	{
		Categories before = categoryService.addCategoriesRESTTEST(category);
		Categories after = categoryService.getCategoriesTEST(before.getCatid());
		assertEquals(before.getCatid(),after.getCatid());
	}
	
	@Test
	public void updateCategory()
	{
		Categories before = categoryService.addCategoriesRESTTEST(category);
		before.setCatname("test");
		
		Categories after = categoryService.updateCategoriesRESTTEST(before);
		assertEquals(before.getCatname(),after.getCatname());
	}
	
	@Test
	public void deleteAuthor()
	{
		Categories before = categoryService.addCategoriesRESTTEST(category);
		categoryService.deleteCategoriesTEST(before.getCatid());
		Categories after = categoryService.getCategoriesTEST(before.getCatid());
		assertEquals(after,null);
	}

}
