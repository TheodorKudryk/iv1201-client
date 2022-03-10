package com.iv1201.client.controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.iv1201.client.integration.DBHandler;
import com.iv1201.client.model.ApplicationDTO;
import java.net.ConnectException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles users that are applicants and not recruiters
 * @author Zarcez
 */
@Controller
public class ApplicantController {
    
    @RequestMapping(value = "/applicant")
    public String Applicant(){
        return "redirect:/startpage";
    }
    
    /**
     * The controller for where the application is sent to be checked before 
     * it's sent to the database handler
     * @param applicationDTO the class with all the info for the application
     * @param bindingResult used for checking the values that was sent 
     * @param username the username of the current logged in applicant
     * @return the view used
     */
    @RequestMapping(value = "/application")
    public String Applicantion(@Valid ApplicationDTO applicationDTO, BindingResult bindingResult,
            @RequestParam("username") String username){
	if (bindingResult.hasErrors()) {
            return "form";
	}
        try {
            String serverMsg = DBHandler.application(applicationDTO, username);
            System.out.println("serverMsg: "+serverMsg);

        }   catch (ConnectException ex) {
        }
        return "redirect:/startpage";
    }
}
