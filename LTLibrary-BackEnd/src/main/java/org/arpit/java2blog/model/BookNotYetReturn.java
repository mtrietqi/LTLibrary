package org.arpit.java2blog.model;

import java.util.Date;

public class BookNotYetReturn {
	private Integer bookId;
	private Integer bordetailId;
	private String bookTitle;
	private String bookImage;
	private String barCode;
	private boolean isReturn;
	private String borrowerFirstName;
	private String borrowerLastName;
	private Date borDate;
	private Integer borrowerId;
	private Integer maxDayIssue;
	
	public BookNotYetReturn() {}
	
	public BookNotYetReturn(String bookTitle, String bookImage, String barCode,
			boolean isReturn,Integer bookId,Integer bordetailId,
			String borrowerFirstName,String borrowerLastName,
			Date borDate,Integer borrowerId,Integer maxDayIssue) {
		this.bookTitle = bookTitle;
		this.bookImage = bookImage;
		this.barCode = barCode;
		this.isReturn = isReturn;
		this.bookId= bookId;
		this.bordetailId=bordetailId;
		this.borrowerFirstName=borrowerFirstName;
		this.borrowerLastName=borrowerLastName;
		this.borDate=borDate;
		this.borrowerId=borrowerId;
		this.maxDayIssue=maxDayIssue;
	}
	
	public BookNotYetReturn(String bookTitle, String bookImage, String barCode,
			boolean isReturn,Integer bookId,Integer bordetailId,
			String borrowerFirstName,String borrowerLastName,
			Date borDate,Integer borrowerId) {
		this.bookTitle = bookTitle;
		this.bookImage = bookImage;
		this.barCode = barCode;
		this.isReturn = isReturn;
		this.bookId= bookId;
		this.bordetailId=bordetailId;
		this.borrowerFirstName=borrowerFirstName;
		this.borrowerLastName=borrowerLastName;
		this.borDate=borDate;
		this.borrowerId=borrowerId;

	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getBookImage() {
		return bookImage;
	}

	public void setBookImage(String bookImage) {
		this.bookImage = bookImage;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public boolean isReturn() {
		return isReturn;
	}

	public void setReturn(boolean isReturn) {
		this.isReturn = isReturn;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getBordetailId() {
		return bordetailId;
	}

	public void setBordetailId(Integer bordetailId) {
		this.bordetailId = bordetailId;
	}

	public String getBorrowerFirstName() {
		return borrowerFirstName;
	}

	public void setBorrowerFirstName(String borrowerFirstName) {
		this.borrowerFirstName = borrowerFirstName;
	}

	public String getBorrowerLastName() {
		return borrowerLastName;
	}

	public void setBorrowerLastName(String borrowerLastName) {
		this.borrowerLastName = borrowerLastName;
	}

	public Date getBorDate() {
		return borDate;
	}

	public void setBorDate(Date borDate) {
		this.borDate = borDate;
	}

	public Integer getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(Integer borrowerId) {
		this.borrowerId = borrowerId;
	}

	public Integer getMaxDayIssue() {
		return maxDayIssue;
	}

	public void setMaxDayIssue(Integer maxDayIssue) {
		this.maxDayIssue = maxDayIssue;
	}
	
	
	
	
	
	
	
	
	
	
	
}
