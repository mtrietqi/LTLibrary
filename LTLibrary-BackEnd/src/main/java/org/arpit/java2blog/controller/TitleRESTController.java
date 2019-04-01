package org.arpit.java2blog.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.swing.JOptionPane;

import org.arpit.java2blog.model.Titles;
import org.arpit.java2blog.model.AuthorNameFromTitleId;
import org.arpit.java2blog.model.Publishers;
import org.arpit.java2blog.model.TitleAuthors;
import org.arpit.java2blog.model.TitleCategories;
import org.arpit.java2blog.service.PublisherService;
import org.arpit.java2blog.service.TitleAuthorService;
import org.arpit.java2blog.service.TitleService;
import org.arpit.java2blog.util.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TitleRESTController {
	@Autowired
	TitleService titleService;

	@Autowired
	private ServletContext servletContext;

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	// Get All Request
	@RequestMapping(value = "/getAllTitlesREST", method = RequestMethod.GET,
			produces = "application/json")
	public List<Titles> getTitles() {
		List<Titles> listOfTitles = titleService.getAllTitles();
		for (int i = 0; i < listOfTitles.size(); i++) {
			listOfTitles.get(i).getPublishers().setTitleses(null);
			listOfTitles.get(i)
						.setTitleauthorses(titleService
								.getTitleAuthorFromTitleId(listOfTitles
										.get(i).getTitleid()));
			
			listOfTitles.get(i).setBookses(null);
			
			listOfTitles.get(i)
						.setTitlecategorieses(titleService
							.getTitleCategoriesFromTitleId(listOfTitles
									.get(i).getTitleid()));
			
			listOfTitles.get(i).setCoursebookses(null);
		}
		return listOfTitles;
	}
	//add Request
	@RequestMapping(value = "/addTitlesREST", method = RequestMethod.POST,
			produces = "application/json;charset=UTF-8", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Titles addTitlesREST(@RequestParam(value = "bookImage", required = false) MultipartFile bookImage,
			@RequestParam(value = "tableContent", required = false) MultipartFile tableContent,
			@RequestParam("publisherId") Integer publisherId, @RequestParam("isbn") String isbn,
			@RequestParam("bookTitle") String bookTitle, @RequestParam("edition") String edition,
			@RequestParam("pubdate") String pubdate, @RequestParam("price") Float price,
			@RequestParam("description") String description) throws ParseException {

		Publishers pub = new Publishers();
		pub.setPubid(publisherId);
		Titles title = new Titles(isbn, bookTitle, edition, formatter.parse(pubdate), price, description);
		title.setPublishers(pub);

		// p.setImagePath(imagePath);
		Titles newTitles = titleService.addTitlesREST(title);

		// upload file
		if (bookImage != null) {
			FileUpload fileUpload = new FileUpload();
			fileUpload.processFileName(bookImage, servletContext.getRealPath("/"), "WEB-INF\\bookImages\\",
					newTitles.getTitleid());
			String fileName = bookImage.getOriginalFilename();
			fileName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			String bookImagePath = newTitles.getTitleid() + fileName;
			newTitles.setBookimage(bookImagePath);
		}

		// upload file
		if (tableContent != null) {
			FileUpload fileUpload2 = new FileUpload();
			fileUpload2.processFileName(tableContent, servletContext.getRealPath("/"), "WEB-INF\\tableOfContent\\",
					newTitles.getTitleid());
			String fileName2 = tableContent.getOriginalFilename();
			fileName2 = fileName2.substring(fileName2.lastIndexOf("."), fileName2.length());
			String tableContentPath = newTitles.getTitleid() + fileName2;
			newTitles.setTablecontent(tableContentPath);

		}
		titleService.updateTitles(newTitles);
		return newTitles;
	}
	//Delete Request
	@RequestMapping(value = "/deleteBookTitle/{id}",
			method = RequestMethod.GET, produces = "application/json")
	public List<Titles> deleteTitles(@PathVariable("id") int id) {
		Titles t = titleService.getTitles(id);
		// delete image
		String fileName = servletContext.getRealPath("/") +
				"WEB-INF\\bookImages\\" + t.getBookimage();
		if (t.getBookimage() != null) {
			File f = new File(fileName);
			if (f.isFile())
				f.delete();
		}
		String fileName2 = servletContext.getRealPath("/") +
				"WEB-INF\\tableOfContent\\" + t.getTablecontent();
		if (t.getTablecontent() != null) {
			File f = new File(fileName2);
			if (f.isFile())
				f.delete();
		}
		titleService.deleteTitles(id);
		List<Titles> listOfTitles = titleService.getAllTitles();
		for (int i = 0; i < listOfTitles.size(); i++) {
			listOfTitles.get(i).getPublishers().setTitleses(null);
			listOfTitles.get(i).setTitleauthorses(null);
			listOfTitles.get(i).setBookses(null);
			listOfTitles.get(i).setTitlecategorieses(null);
			listOfTitles.get(i).setCoursebookses(null);
		}
		return listOfTitles;
	}
	//Get By Id
	@RequestMapping(value = "/getTitlesREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public Titles getTitlesByIdREST(@PathVariable int id) {
		Titles t = titleService.getTitles(id);
		t.getPublishers().setTitleses(null);
		t.setTitleauthorses(null);
		t.setBookses(null);
		t.setTitlecategorieses(null);
		t.setCoursebookses(null);
		return t;
	}
	//update Request
	@RequestMapping(value = "/updateTitlesREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Titles updateTitlesREST(@RequestParam(value = "bookImage", required = false) MultipartFile bookImage,
			@RequestParam(value = "tableContent", required = false) MultipartFile tableContent,
			@RequestParam("titleId") Integer titleId, @RequestParam("publisherId") Integer publisherId,
			@RequestParam("isbn") String isbn, @RequestParam("bookTitle") String bookTitle,
			@RequestParam("edition") String edition, @RequestParam("pubdate") String pubdate,
			@RequestParam("price") Float price, @RequestParam("description") String description) throws ParseException {
		// upload file

		Publishers pub = new Publishers();
		pub.setPubid(publisherId);
		Titles currentTitles = titleService.getTitles(titleId);
		Titles t = new Titles(titleId, isbn, bookTitle, edition, formatter.parse(pubdate), price, description);
		t.setPublishers(pub);
		String bookImagePath = "";
		String tableContentPath = "";
		
		if (bookImage != null) {
			FileUpload fileUpload = new FileUpload();
			fileUpload.processFileName(bookImage, servletContext.getRealPath("/"), "WEB-INF\\bookImages\\", titleId);
			String fileName = bookImage.getOriginalFilename();
			fileName = fileName.substring(fileName.lastIndexOf("."));
			bookImagePath = t.getTitleid() + fileName;
			System.out.println(bookImagePath);
		}
		
		if (bookImage != null && bookImage.getSize() > 0)
			t.setBookimage(bookImagePath);
		else
			t.setBookimage(currentTitles.getBookimage());

		if (tableContent != null) {
			FileUpload fileUpload2 = new FileUpload();
			fileUpload2.processFileName(tableContent, servletContext.getRealPath("/"), "WEB-INF\\tableOfContent\\",
					titleId);
			String fileName2 = tableContent.getOriginalFilename();
			fileName2 = fileName2.substring(fileName2.lastIndexOf("."));
			tableContentPath = t.getTitleid() + fileName2;

		}
		if (tableContent != null && tableContent.getSize() > 0)
			t.setTablecontent(tableContentPath);
		else
			t.setTablecontent(currentTitles.getTablecontent());
		return titleService.updateTitlesREST(t);
	}
	// get TitleAuthor From Title Id Request
	@RequestMapping(value = "/getTitleAuthorFromTitleId/{id}", method = RequestMethod.GET, produces = "application/json")
	public Set<TitleAuthors> getAuthorNameFromTitleId(@PathVariable int id) {
		Set<TitleAuthors> listOfAuthorNameFromTitleId = titleService.getTitleAuthorFromTitleId(id);
		return listOfAuthorNameFromTitleId;
	}
	//get Title Category From Title Id
	@RequestMapping(value = "/getTitleCategoryFromTitleId/{id}", method = RequestMethod.GET, produces = "application/json")
	public Set<TitleCategories> getTitleCategoryFromTitleId(@PathVariable int id) {
		Set<TitleCategories> listOfAuthorNameFromTitleId = titleService.getTitleCategoriesFromTitleId(id);
		return listOfAuthorNameFromTitleId;
	}
}