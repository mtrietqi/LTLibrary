package org.arpit.java2blog.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JOptionPane;

import org.springframework.web.multipart.MultipartFile;
public class FileUpload {

    private static final String[] ALLOWED_FILE_TYPES = {"image/jpeg", "image/jpg", "image/gif","image/png", "application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document",
    		"application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"};
    private static final Long MAX_FILE_SIZE = 9048576L; 
    public static String UPLOAD_FILE_PATH = "";
    public String newFile;
    public String fileNameId;
    public String processFileName(MultipartFile file, String rootAppPath, String uploadFilePath, int id) {
    
        if (!file.isEmpty()) {
        	this.UPLOAD_FILE_PATH = uploadFilePath;
            String contentType = file.getContentType().toString().toLowerCase();      
            if (isValidContentType(contentType)) {
                if (belowMaxFileSize(file.getSize())) {
                	String fileName = file.getOriginalFilename();
                	fileName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                    newFile =rootAppPath+ UPLOAD_FILE_PATH + id + 
                    		fileName;
                    fileNameId = id + fileName;
                    System.out.println("File path: " + newFile);
                    try {
                        file.transferTo(new File(newFile));
                        return "You have successfully uploaded " + file.getOriginalFilename() + "!";
                    } catch (IllegalStateException e) {
                        return "There was an error uploading " + file.getOriginalFilename() + " => " + e.getMessage();
                    } catch (IOException e) {
                        return "There was an error uploading " + file.getOriginalFilename() + " => " + e.getMessage();
                    }
                } else {
                    return "Error. " + file.getOriginalFilename() + " file size (" + file.getSize() + ") exceeds " + MAX_FILE_SIZE + " limit.";
                }
            } else {
                return "Error. " + contentType + " is not a valid content type.";
            }
        } else {
            return "Error. No file choosen.";
        }
    }
    public String process(MultipartFile file, String rootAppPath) {
    	
        if (!file.isEmpty()) {
            String contentType = file.getContentType().toString().toLowerCase();
            if (isValidContentType(contentType)) {
                if (belowMaxFileSize(file.getSize())) {
                    newFile =rootAppPath+ UPLOAD_FILE_PATH + file.getOriginalFilename();   
                    System.out.println("File path: " + newFile);
                    try {
                        file.transferTo(new File(newFile));
                        return "You have successfully uploaded " + file.getOriginalFilename() + "!";
                    } catch (IllegalStateException e) {
                        return "There was an error uploading " + file.getOriginalFilename() + 
                        		" => " + e.getMessage();
                    } catch (IOException e) {
                        return "There was an error uploading " + file.getOriginalFilename() + 
                        		" => " + e.getMessage();
                    }
                } else {
                    return "Error. " + file.getOriginalFilename() + 
                    		" file size (" + file.getSize() + ") exceeds " + MAX_FILE_SIZE + " limit.";
                }
            } else {
                return "Error. " + contentType + " is not a valid content type.";
            }
        } else {
            return "Error. No file choosen.";
        }
    }
    
public String processExcel(MultipartFile file, String rootAppPath) {
    	
        if (!file.isEmpty()) {
            String contentType = file.getContentType().toString().toLowerCase();
            
            if (isValidContentType(contentType)) {
                if (belowMaxFileSize(file.getSize())) {
                    newFile =rootAppPath+ UPLOAD_FILE_PATH + file.getOriginalFilename();   
                    System.out.println("File path: " + newFile);
                    try {
                        file.transferTo(new File(newFile));
                        return newFile;
                    } catch (IllegalStateException e) {
                        return "There was an error uploading " + file.getOriginalFilename() + " => " + e.getMessage();
                    } catch (IOException e) {
                        return "There was an error uploading " + file.getOriginalFilename() + " => " + e.getMessage();
                    }
                } else {
                    return "Error. " + file.getOriginalFilename() + " file size (" + file.getSize() + ") exceeds " + MAX_FILE_SIZE + " limit.";
                }
            } else {
                return "Error. " + contentType + " is not a valid content type.";
            }
        } else {
            return "Error. No file choosen.";
        }
    }
    
    private Boolean isValidContentType(String contentType) {
//        if (!Arrays.asList(ALLOWED_FILE_TYPES).contains(contentType)) {
//            return false;
//        }
        
        return true;
    }
    
    private Boolean belowMaxFileSize(Long fileSize) {
        if (fileSize > MAX_FILE_SIZE) {
            return false;
        }
        
        return true;
    }
    
    public String getFilePath()
    {
    	return newFile;
    }
    
    public String getFileNameId()
    {
    	return fileNameId;
    }
}
