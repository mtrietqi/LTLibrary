package org.arpit.java2blog.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.arpit.java2blog.model.BookNotYetReturn;
import org.arpit.java2blog.model.Books;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	public List<Books> getAllBooks() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Books> bookList = session.createQuery("from Books b "
				+ "order by b.bookid desc").list();
		return bookList;
	}

	public Books getBooks(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Books book = (Books) session.get(Books.class, new Integer(id));
		return book;
	}

	public Books addBooks(Books book) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(book);
		return book;
	}

	public void updateBooks(Books book) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(book);
	}
	
	public Books updateBooksREST(Books book) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(book);
		return book;
	}

	public void deleteBooks(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Books p = (Books) session.load(Books.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}	
	//get All Book Not Yet Return function
	public List<BookNotYetReturn> getAllBookNotYetReturn() {
		List<BookNotYetReturn> list = new ArrayList();
		
		Session session = this.sessionFactory.getCurrentSession();
		Query myQuery = session.createQuery("select t.booktitle,t.bookimage,b.barcode,bd.isreturn,"
				+ "b.bookid, bd.bordetailid,"
				+ "bwer.firstname,bwer.lastname,bwin.bordate,bwer.borrowerid,bt.maxdayissue "
				+ "from BorrowingDetails bd "
				+ "inner JOIN bd.books b "
				+ "inner JOIN b.titles t "
				+ "inner JOIN bd.borrowings bwin "
				+ "inner JOIN bwin.borrowers bwer "
				+ "inner JOIN bwer.borrowertype bt "
				+ "where bd.isreturn=false ");

		List l = myQuery.list();
		
		for (int i = 0; i<l.size(); i++) {
			Object[] singleRowValues = (Object[])l.get(i);
			String bookTitle = (String)singleRowValues[0];
			String bookImage = (String)singleRowValues[1];
			String barCode = (String)singleRowValues[2];
			Boolean isReturn= (boolean)singleRowValues[3];
			Integer bookId= (Integer)singleRowValues[4];
			Integer borDetailId= (Integer)singleRowValues[5];
			String borrowerFirstName = (String)singleRowValues[6];
			String borrowerLastName = (String)singleRowValues[7];
			Date borDate=(Date)singleRowValues[8];
			Integer borrowerId= (Integer)singleRowValues[9];
			Integer maxDayIssue= (Integer)singleRowValues[10];
			list.add(new BookNotYetReturn(bookTitle, bookImage,barCode, 
					isReturn,bookId,borDetailId,borrowerFirstName,
					borrowerLastName,borDate,borrowerId,maxDayIssue));
		}
		
		return list;
	}
	// get All Available Book function
	public List<Books> getAllAvailableBook() {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Books> bookList = session.createQuery("from Books b "
				+ "where b.bookid not in (select b2.bookid "
				+ "from BorrowingDetails bd "
				+ "inner JOIN bd.books b2 "
				+ "where bd.isreturn=false)").list();

		return bookList;
	}
	
	// get Book Not Yet Return Amount function
	public Long getBookNotYetReturnAmount() {
		Session session = this.sessionFactory.getCurrentSession();
		Long bookNotYetReturn =null;
		Query myQuery = session.createQuery("select count(b.barcode),bd.isreturn "
				+ "from BorrowingDetails bd "
				+ "inner JOIN bd.books b "
				+ "where bd.isreturn=false "
				+ "group by bd.isreturn "
				);
		List l = myQuery.list();
		for (int i = 0; i<l.size(); i++) {
			Object[] singleRowValues = (Object[])l.get(i);
			bookNotYetReturn = (Long)singleRowValues[0];
		}
		return bookNotYetReturn;
	}
	//get Book Amount
	public Long getBookAmount() {
		Session session = this.sessionFactory.getCurrentSession();
		Long bookAmount =null;
		Query myQuery = session.createQuery("select count(b.bookid) "
				+ "from Books b "
				);

		Object singleRowValues = (Object)myQuery.uniqueResult();
		bookAmount = (Long)singleRowValues;

		return bookAmount;
	}
	//get Book Not Yet Return By Borrwer
	public List<BookNotYetReturn> getBookNotYetReturnByBorrwer(int id) {
		List<BookNotYetReturn> list = new ArrayList();
		
		Session session = this.sessionFactory.getCurrentSession();
		Query myQuery = session.createQuery("select t.booktitle,t.bookimage,b.barcode,bd.isreturn,"
				+ "b.bookid, bd.bordetailid,bwer.firstname,bwer.lastname,bwin.bordate,bwer.borrowerid "
				+ "from BorrowingDetails bd "
				+ "inner JOIN bd.books b "
				+ "inner JOIN b.titles t "
				+ "inner JOIN bd.borrowings bwin "
				+ "inner JOIN bwin.borrowers bwer "
				+ "where bwer.borrowerid="+id);

		List l = myQuery.list();
		
		for (int i = 0; i<l.size(); i++) {
			Object[] singleRowValues = (Object[])l.get(i);
			String bookTitle = (String)singleRowValues[0];
			String bookImage = (String)singleRowValues[1];
			String barCode = (String)singleRowValues[2];
			Boolean isReturn= (boolean)singleRowValues[3];
			Integer bookId= (Integer)singleRowValues[4];
			Integer borDetailId= (Integer)singleRowValues[5];
			String borrowerFirstName = (String)singleRowValues[6];
			String borrowerLastName = (String)singleRowValues[7];
			Date borDate=(Date)singleRowValues[8];
			Integer borrowerId= (Integer)singleRowValues[9];
			list.add(new BookNotYetReturn(bookTitle, bookImage,barCode, 
					isReturn,bookId,borDetailId,borrowerFirstName,borrowerLastName,borDate,borrowerId));
		}
		
		return list;
	}
	//get All Book By Status
	public List<Books> getAllBookByStatus(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Books> bookList = session.createQuery("from Books b "
				+ "where b.bookstatus="+id).list();
		return bookList;
	}
	
	
	
}
