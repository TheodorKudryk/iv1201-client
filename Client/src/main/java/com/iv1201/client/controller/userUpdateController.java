
package com.iv1201.client.controller;

import com.iv1201.client.integration.DBHandler;
import com.iv1201.client.model.UserDTO;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.net.ConnectException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *  This serves as an controller for all views related to the the updating a 
 * account with new usernamn and password
 * @author Zarcez
 */
@Controller
public class UserUpdateController {

    /**
     * This is the controller for when the user first gets the page 
     * @param model used by Thymeleaf
     * @param token checks if the same token is in the database
     * @return 
     */
    @RequestMapping(value = "/userUpdate", method = RequestMethod.GET)
    public String checkToken(Model model, String token){
        if (LoginController.isAuthenticated()) { //Checks if the user is already logged in
            return "redirect:startpage";
        }
        try {
            if(token == null || DBHandler.validateToken(token).contains("No token")){ //Checks if the token is not valid
                return "redirect:error";
            }
        } catch (ConnectException ex) { //If there was no connection to the database
                return "redirect:login?db";
        }
        model.addAttribute("token", token);
        return "userupdate";
    }
    
    /**
     * This is the controller for when a user gotten to the page and has sent 
     * the new user info that will be updated in the database
     * @param model used by Thymeleaf
     * @param request the request made to the view
     * @param token the token that connects the account to the user that 
     * requested it 
     * @param username the username that will be entered to the database
     * @param password the password that will be entered to the database
     * @return if the user has been updated send it back to login and if couldn't
     * sent it back to login with an error
     */
    @RequestMapping(value = "/userUpdate", method = RequestMethod.POST)
    public String resetAccount(Model model, HttpServletRequest request,  @RequestParam("token") String token,
            @RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            UserDTO user = new UserDTO(username,password,"");
            String serverMsg = DBHandler.updateUser(user, token);
            if(serverMsg == null)
                return "redirect:error";
            model.addAttribute("reset",true);
            return "redirect:login";
        } catch (ConnectException ex) {
                return "redirect:login?db";
        }
    }

}
