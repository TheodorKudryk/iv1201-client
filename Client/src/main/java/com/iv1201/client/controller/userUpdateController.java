/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iv1201.client.controller;

import com.iv1201.client.integration.DBHandler;
import com.iv1201.client.model.UserDTO;
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
public class userUpdateController {

    @RequestMapping(value = "/userUpdate", method = RequestMethod.GET)
    public String checkToken(Model model, String token){
        if (LoginController.isAuthenticated()) {
            return "redirect:startpage";
        }
        try {
            if(token == null || DBHandler.validateToken(token)== null){
                return "redirect:error";
            }
        } catch (ConnectException ex) {
                return "redirect:login?db";
        }
        System.out.println("set token");
        model.addAttribute("token", token);
        return "userupdate";
    }
    
    @RequestMapping(value = "/userUpdate", method = RequestMethod.POST)
    public String resetAccount(Model model, HttpServletRequest request,  @RequestParam("token") String token,
            @RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            UserDTO user = new UserDTO(username,password,"");
            DBHandler.updateUser(user, token);
            model.addAttribute("reset",true);
            return "redirect:login";
        } catch (ConnectException ex) {
                return "redirect:login?db";
        }
    }

}
