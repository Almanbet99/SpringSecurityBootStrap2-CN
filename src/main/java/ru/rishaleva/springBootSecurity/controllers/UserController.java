package ru.rishaleva.springBootSecurity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.rishaleva.springBootSecurity.service.UserService;

import java.security.Principal;

@Controller
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/user")
    public ModelAndView userPage(Principal principal) {
        ModelAndView mav = new ModelAndView("user");
        mav.addObject("user", userService.findByUsername(principal.getName()));
        return mav;
    }
}





