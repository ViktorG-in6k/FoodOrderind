package com.serviceLayer.implementation;

import com.model.User;
import com.serviceLayer.service.UserService;
import org.osgi.service.component.annotations.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class AuthenticationSuccessFilter extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    UserService userService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        request.getSession().setAttribute("user", userService.getUserByEmail(user.getEmail()));

        request.getSession().setMaxInactiveInterval((int) TimeUnit.HOURS.toSeconds(48));
        super.onAuthenticationSuccess(request, response, authentication);
    }
}