package com.smart.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/index")
    public ModelAndView dashborad() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("normal/user_dashboard");
        return modelAndView;
    }
}
