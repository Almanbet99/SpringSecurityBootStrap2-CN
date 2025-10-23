package ru.rishaleva.springBootSecurity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.rishaleva.springBootSecurity.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"", "/"})
    public ModelAndView showAdminPage(Principal principal) {
        ModelAndView mav = new ModelAndView("users");
        mav.addObject("user", userService.findByUsername(principal.getName()));
        return mav;
    }
}

