package com.smart.controller;

import com.smart.model.User;
import com.smart.service.IUserRepositoryImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserRepositoryImplementation iUserRepositoryImplementation;
    @RequestMapping("/index")
    public ModelAndView dashborad(Principal principal) {
        String userName = principal.getName();
        User user = iUserRepositoryImplementation.getUserLogin(userName);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user",user);
        modelAndView.setViewName("normal/user_dashboard");
        return modelAndView;
    }
}
