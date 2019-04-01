package org.arpit.java2blog.model;

public class AuthorNameFromTitleId {
	private Integer titleId;
	private String authorFirstName;
	private String authorLastName;
	
	public AuthorNameFromTitleId() {}
	
	public AuthorNameFromTitleId(Integer titleId, String authorFirstName, String authorLastName) {
		this.titleId = titleId;
		this.authorFirstName = authorFirstName;
		this.authorLastName = authorLastName;
	}

	public Integer getTitleId() {
		return titleId;
	}

	public void setTitleId(Integer titleId) {
		this.titleId = titleId;
	}

	public String getAuthorFirstName() {
		return authorFirstName;
	}

	public void setAuthorFirstName(String authorFirstName) {
		this.authorFirstName = authorFirstName;
	}

	public String getAuthorLastName() {
		return authorLastName;
	}

	public void setAuthorLastName(String authorLastName) {
		this.authorLastName = authorLastName;
	}
	
	
	
	

}
