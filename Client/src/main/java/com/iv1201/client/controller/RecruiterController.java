package com.iv1201.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author theok
 */
@Controller
public class RecruiterController {
    
    @RequestMapping(value = "/recruiter")
    public String Recruiter(ModelMap model){
        return "recruiter";
    }
   
}
