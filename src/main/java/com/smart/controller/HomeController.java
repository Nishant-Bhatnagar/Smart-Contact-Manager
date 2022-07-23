package com.smart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {
    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title","Smart Contact Manager");
        modelAndView.setViewName("home");
        return modelAndView;
    }


    @GetMapping("/about")
    public ModelAndView about() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title","Smart Contact Manager");
        modelAndView.setViewName("about");
        return modelAndView;
    }
}
