package com.smart.controller;

import com.smart.model.Contact;
import com.smart.model.User;
import com.smart.service.IUserRepositoryImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserRepositoryImplementation iUserRepositoryImplementation;
    public ModelAndView addCommonData(Principal principal)
    {
        String userName = principal.getName();
        User user = iUserRepositoryImplementation.getUserLogin(userName);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user",user);
        return modelAndView;
    }
    @GetMapping("/index")
    public ModelAndView dashBoard(Principal principal) {
        ModelAndView mv = addCommonData(principal);
        mv.addObject("title","User DashBoard");
        mv.setViewName("normal/user_dashboard");
        return mv;
    }

    @GetMapping("/add-contact")
    public ModelAndView openAddContactForm(Principal principal)
    {
        ModelAndView mv = addCommonData(principal);
         mv.addObject("title","Add Contact");
         mv.addObject("contact",new Contact());
        mv.setViewName("normal/add_contact_form");
        return mv;
    }
}
