/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iv1201.Client.controller;

import com.iv1201.client.controller.LoginController;
import com.iv1201.client.integration.DBHandler;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.net.ConnectException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 *
 * @author Zarcez
 */
@Controller
public class ResetAccountController {

    /**
     * Start view for page when resetting users without username or password
     * @param model Used by Thymeleaf
     * @return view used
     */
    @RequestMapping(value = "/resetAccount", method = RequestMethod.GET)
    public String resetAccount(Model model){
        if (LoginController.isAuthenticated()) {
            return "redirect:startpage";
        }
        return "resetaccount";
    }
    
    /**
     * Post method used for getting a token for which the user can reset the account
     * @param model Used by Thymeleaf
     * @param request 
     * @param userEmail The user resets based on email
     * @return the view with a message depending on how it went 
     */
    @RequestMapping(value = "/resetAccount", method = RequestMethod.POST)
    public String resetPassword(Model model, HttpServletRequest request, @RequestParam("email") String userEmail) {
        String serverMsg = "";
        try {
            serverMsg = DBHandler.validateEmail(userEmail);
        } catch (ConnectException ex) {
            return "redirect:login?db";
        }
        if(serverMsg.contains("ok"))
                model.addAttribute("ok",true);
        else if(serverMsg.contains("invalid email"))
                model.addAttribute("invalid",true);
        else if(serverMsg.contains("already been reset"))
                model.addAttribute("reset",true);
        else
            model.addAttribute("error",true);
        return "resetaccount";
    }

}
