
package com.iv1201.Client.controller;

import static com.iv1201.client.controller.LoginController.isAuthenticated;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * If an user tries to go to a nonexistent page, they will be redirected to the
 * front page with an error
 * @author Zarcez
 */
@Controller
public class CustomErrorController implements ErrorController  {

        @RequestMapping("/error")
        public String handleError() {
            if (isAuthenticated())
                return "redirect:/startpage?error";
            return "redirect:/login?error";
    }
}

