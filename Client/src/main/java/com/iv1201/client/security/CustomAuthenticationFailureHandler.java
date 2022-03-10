package com.iv1201.Client.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 *  Custom authentication failure handler that deals with the case that an user
 *  has tried to login and the login failed for some reason
 */
@Component
public class CustomAuthenticationFailureHandler  
  implements AuthenticationFailureHandler {
 

    @Override
    /**
     * Checks what kind of exception has been thrown and depending on the 
     * exception, redirects the user to different URL that display an 
     * error message
     */
    public void onAuthenticationFailure(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException exception) 
        throws IOException, ServletException {
            if(exception.getMessage().contains("No AuthenticationProvider"))
                response.sendRedirect(request.getContextPath() + "/login?db");
            else if(exception.getMessage().contains("invalid login"))
                response.sendRedirect(request.getContextPath() + "/login?login");
            else
                response.sendRedirect(request.getContextPath() + "/login?error");
        }
}
