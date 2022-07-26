package com.smart.controller;

import com.smart.helper.Message;
import com.smart.model.Contact;
import com.smart.model.User;
import com.smart.service.ContactRepositoryImplementation;
import com.smart.service.IContactRepositoryImplementation;
import com.smart.service.IUserRepositoryImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
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
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserRepositoryImplementation iUserRepositoryImplementation;

    @Autowired
    IContactRepositoryImplementation iContactRepositoryImplementation;
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
            if(file.isEmpty()){

                contact.setImage("contact.png");
                iUserRepositoryImplementation.addContact(user, contact);

            }

            if(!file.isEmpty())
            {   contact.setImage(file.getOriginalFilename());
                iUserRepositoryImplementation.addContact(user, contact);
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image is upload");
            }
// Success Message

            session.setAttribute("message",new Message("Your contact added !! Add more...","success"));
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
            e.printStackTrace();
            //            Error Message
            session.setAttribute("message",new Message("Something went wrong !! Try again...","danger"));
        }

        finally {

            mv = addCommonData(principal);
            mv.addObject("title", "Add Contact");
            mv.addObject("contact", new Contact());
            mv.setViewName("normal/add_contact_form");
        }

        return mv;
    }

    //show contacts handler

    @GetMapping("/show-contacts/{page}")
    public ModelAndView showContacts(@PathVariable("page") Integer page, Principal principal)
    {
        String name = principal.getName();
        User userLogin = iUserRepositoryImplementation.getUserLogin(name);
        Page<Contact> contact = iContactRepositoryImplementation.getContact(userLogin.getId(), page);
        ModelAndView mv  = addCommonData(principal);
        mv.addObject("title","Show User Contacts");
        mv.addObject("contacts",contact);
        mv.addObject("currentPage",page);
        mv.addObject("totalPages",contact.getTotalPages());
        mv.setViewName("normal/show_contacts");
        return mv;
    }

//    Showing particular contact
    @GetMapping("/{cId}/contact")
    public ModelAndView showContactsDetails(@PathVariable("cId") Integer cId, Principal principal)
    {
        Contact contactDetail = iContactRepositoryImplementation.getContactDetail(cId);
        ModelAndView mv  = addCommonData(principal);
        User user = (User) mv.getModel().get("user");
        if(user.getId() == contactDetail.getUser().getId())
        {
            mv.addObject("contact",contactDetail);
        }
        mv.addObject("title","Show User Contacts");
        mv.setViewName("normal/contact_detail");

        return mv;
    }

//    Deleting the contact id
    @GetMapping("/delete/{cId}")
    public ModelAndView deleteContact(@PathVariable("cId") Integer cId,Principal principal,HttpSession session)
    {

        Contact contactDetail = iContactRepositoryImplementation.getContactDetail(cId);
        ModelAndView mv  = addCommonData(principal);
        User user = (User) mv.getModel().get("user");

        if(user.getId() == contactDetail.getUser().getId())

        {

            contactDetail.setUser(null);
            iContactRepositoryImplementation.deleteContactDetails(cId);
            session.setAttribute("message", new Message("Contact deleted succesfully...","success"));
        }

        return new ModelAndView("redirect:/user/show-contacts/0");


    }

//    Open update from handler
    @PostMapping("/update-contact/{cId}")
    public ModelAndView updateForm(@PathVariable("cId") Integer cId,Principal principal) {
        Contact contactDetail = iContactRepositoryImplementation.getContactDetail(cId);
        ModelAndView mv  = addCommonData(principal);
        mv.addObject("contact",contactDetail);
        mv.setViewName("normal/update_form");
        return mv;
    }

//    Update the contact details in DB
    @PostMapping("/process-update")
    public ModelAndView updateContact(Principal principal,Contact contact,@RequestParam("profileImage") MultipartFile file,HttpSession session) {
        ModelAndView mv;
        try {
            mv = null;
            System.out.println(contact.toString());
            String userName = principal.getName();
            User user = iUserRepositoryImplementation.getUserLogin(userName);
            String image = iContactRepositoryImplementation.getContactDetail(contact.getcId()).getImage();
            if (!file.isEmpty()) {
                File deleteFile = new ClassPathResource("static/img").getFile();
                File file1 = new File(deleteFile, image);
                file1.delete();
                contact.setImage(file.getOriginalFilename());
                iUserRepositoryImplementation.addContact(user, contact);
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image is upload");
            } else {
                contact.setImage(image);
                iUserRepositoryImplementation.addContact(user, contact);
            }
            session.setAttribute("message", new Message("Your contact was updated", "success"));
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
            e.printStackTrace();
            //            Error Message
            session.setAttribute("message", new Message("Something went wrong !! Try again...", "danger"));
        } finally {

            mv = addCommonData(principal);
            mv.addObject("title","User DashBoard");
            mv.setViewName("normal/user_dashboard");
        }

        return mv;


    }

    @GetMapping("/profile")
    public ModelAndView profileView(Principal principal){
        ModelAndView mv = addCommonData(principal);
        mv.addObject("title","Profile");
        mv.setViewName("normal/profile");
        return  mv;
    }

}
