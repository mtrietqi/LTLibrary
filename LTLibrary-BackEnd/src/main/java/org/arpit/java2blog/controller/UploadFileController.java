package org.arpit.java2blog.controller;

import javax.servlet.ServletContext;

import org.arpit.java2blog.util.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class UploadFileController {
	
	@Autowired
    private ServletContext servletContext;
	
	@RequestMapping(value = { "upload" }, method = RequestMethod.GET)
	public String handleFileUpload(ModelMap model) {
	
		model.addAttribute("message", "Spring 4 MVC File Upload Example");
		return "uploadfile";
	}
    
    @RequestMapping(value="upload", method=RequestMethod.POST)
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file) {
        
        ModelAndView model = new ModelAndView();
        FileUpload fileUpload = new FileUpload();
        model.addObject("message", fileUpload.process(file,servletContext.getRealPath("/")));
        model.setViewName("uploadfile");
        System.out.println(servletContext.getRealPath("/"));
        return model;
    }
}
