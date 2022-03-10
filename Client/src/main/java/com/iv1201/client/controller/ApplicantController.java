package com.iv1201.client.controller;


import com.iv1201.client.integration.DBHandler;
import com.iv1201.client.model.ApplicationDTO;
import java.net.ConnectException;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
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
    public String applicant(){
        return "redirect:/startpage";
    }
    
    /**
     * The controller for where the sendApplication is sent to be checked before 
 it's sent to the database handler
     * @param applicationDTO the class with all the info for the sendApplication
     * @param bindingResult used for checking the values that was sent 
     * @param username the username of the current logged in applicant
     * @return the view used
     */
    @RequestMapping(value = "/application")
    public String applicantion(@Valid ApplicationDTO applicationDTO, BindingResult bindingResult,
            @RequestParam("username") String username){
	if (bindingResult.hasErrors()) {
            return "form";
	}
        try {
            String serverMsg = DBHandler.sendApplication(applicationDTO, username);
            System.out.println("serverMsg: "+serverMsg);

        }   catch (ConnectException ex) {
        }
        return "redirect:/startpage";
    }
}
