package com.iv1201.client.controller;


import com.iv1201.client.integration.DBHandler;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    /**
     * Controller for login page
     * @param model used for setting attributes in the view
     * @param login used for login errors
     * @param db used for when database is unreachable
     * @param error catch all error in case of unknown error
     * @return the view used
     */
    @RequestMapping(value = "/login")
    public String login(Model model, String login, String db, String error){
        if (isAuthenticated()) {
            return "redirect:startpage";
        }
        if(login != null){
            model.addAttribute("loginError", true);
        }
        else if(db != null){
            model.addAttribute("dbError", true);
        }
        else if(error != null){
            model.addAttribute("unknownError",true);
        }
        return "login";
    }
    
    /**
     * Startpage for all users, depending on role user is sent to different views
     * @param request 
     * @param language Used for the loading competence
     * @param model Used by Thymeleaf
     * @return the view used 
     */
    @RequestMapping(value = "/startpage")
    public String startpage(HttpServletRequest request, @RequestHeader("accept-language") String language,ModelMap model){
        if (request.isUserInRole("ROLE_recruiter")) {
            return "recruiter";
        }   
        String[] langarray = language.split(",", 2);
        model.addAttribute("competences", DBHandler.loadCompetence(langarray[0]));
        model.addAttribute("username", request.getRemoteUser());
        model.addAttribute("competence_profile", DBHandler.loadApplications(request.getRemoteUser()));
        return "applicant";
    }
    
    /**
     * Checks if the user is logged in, used to redirect users if they are already 
     * logged in 
     * @return true if user is logged in
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
            isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
