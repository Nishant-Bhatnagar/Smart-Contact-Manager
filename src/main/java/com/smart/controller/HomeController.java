package com.smart.controller;

import com.smart.helper.Message;
import com.smart.model.User;
import com.smart.service.IUserRepositoryImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class HomeController {

    @Autowired
    private IUserRepositoryImplementation iUserRepositoryImplementation;

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title", "Smart Contact Manager");
        modelAndView.setViewName("home");
        return modelAndView;
    }


    @GetMapping("/about")
    public ModelAndView about() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title", "Smart Contact Manager");
        modelAndView.setViewName("about");
        return modelAndView;
    }

    @GetMapping("/signup")
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title", "Smart Contact Manager");
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @PostMapping("/do_register")
    public ModelAndView registerUser(@Valid User user,  BindingResult result,boolean agreement, HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            System.out.println("Errors:- " + result.toString());
            if (!agreement) {
                throw new Exception(" Please check the box ");
            }
            if(result.hasErrors()) {
                System.out.println("Errors:- " + result.toString());
                modelAndView.addObject("user", user);
                modelAndView.setViewName("signup");
                return modelAndView;
            }
            String response = iUserRepositoryImplementation.addUser(user);

            modelAndView.addObject("user", new User());
            httpSession.setAttribute("message", new Message("Successfully Registered!!",
                    "alert-success"));


        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("user", user);
            httpSession.setAttribute("message", new Message("Something went wrong" +
                    e.getMessage(), "alert-danger"));
        }
        modelAndView.setViewName("signup");
        return modelAndView;

    }

    @GetMapping("/sigin")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title", "Smart Contact Manager");
        modelAndView.setViewName("sigin");
        return modelAndView;
    }
}
