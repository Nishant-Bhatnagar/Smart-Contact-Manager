package com.smart.controller;

import com.smart.helper.Message;
import com.smart.model.Contact;
import com.smart.model.User;
import com.smart.service.ContactRepositoryImplementation;
import com.smart.service.IContactRepositoryImplementation;
import com.smart.service.IUserRepositoryImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
//Adding new contact to db
    @PostMapping("/adding-contact")
    public ModelAndView addContactToDB(
            Contact contact,
            @RequestParam("profileImage") MultipartFile file,
            Principal principal,
            HttpSession session) {
        ModelAndView mv = null;
        try {
            String name = principal.getName();
            User user = this.iUserRepositoryImplementation.getUserLogin(name);

             //processing and uploading file...
            contact.setImage(file.getOriginalFilename());
            iUserRepositoryImplementation.addContact(user, contact);
            if(file.isEmpty())
            {

            }
            else {


                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image is upload");

            }

            mv = addCommonData(principal);
            mv.addObject("title", "Add Contact");
            mv.addObject("contact", new Contact());
            mv.setViewName("normal/add_contact_form");
//            Success Message
            session.setAttribute("message",new Message("Your contact added !! Add more...","success"));
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
            e.printStackTrace();
            //            Error Message
            session.setAttribute("message",new Message("Something went wrong !! Try again...","danger"));
        }

        return mv;
    }
}
