package org.arpit.java2blog.controller;

import java.util.List;
import org.arpit.java2blog.model.Publishers;
import org.arpit.java2blog.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublisherRESTController 
{
	@Autowired
	 PublisherService publisherService;
	// Get All Request
	@RequestMapping(value = "/getAllPublishersREST",method=RequestMethod.GET,produces = "application/json")
	public List<Publishers> getPublishers()
	{
		List<Publishers> publisher = publisherService.getAllPublishers();
		for (int i = 0; i < publisher.size(); i++)
		{
			publisher.get(i).setTitleses(null);
		}
		return publisher;
	}
	//Delete Request
	@RequestMapping(value = "/deletePublishersREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<Publishers> deletePublishers(@PathVariable("id") int id) {
		publisherService.deletePublishers(id);
		List<Publishers> publisher = publisherService.getAllPublishers();
		for (int i = 0; i < publisher.size(); i++)
		{
			publisher.get(i).setTitleses(null);
		}
		return publisher;	 
	}
	//Get By Id
	@RequestMapping(value = "/getPublishersByIdREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public Publishers getCountryByID(@PathVariable("id") int id) {
		 Publishers publisher = this.publisherService.getPublishers(id);
		 publisher.setTitleses(null);
		 return publisher;
	}
	//add Request
	@RequestMapping(value = "/addPublishersREST", method = RequestMethod.POST, produces = "application/json")
	public Publishers addPublishers(@RequestBody Publishers publisher) {	
			return publisherService.addPublishersREST(publisher);
	}
	// Update Request
	@RequestMapping(value = "/updatePublishersREST", method = RequestMethod.POST, produces = "application/json")
	public Publishers updatePublishers(@RequestBody Publishers publisher) {	
			return publisherService.updatePublishersREST(publisher);
	}
}
