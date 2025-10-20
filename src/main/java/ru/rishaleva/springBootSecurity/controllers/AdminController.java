package ru.rishaleva.springBootSecurity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.rishaleva.springBootSecurity.model.User;
import ru.rishaleva.springBootSecurity.service.RoleService;
import ru.rishaleva.springBootSecurity.service.UserService;

import java.security.Principal;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public ModelAndView showAllUsers(Principal principal) {
        ModelAndView mav = new ModelAndView("users");
        User currentUser = userService.findByUsername(principal.getName());
        mav.addObject("user", currentUser);
        mav.addObject("listOfUsers", userService.getAllUsers());
        mav.addObject("roles", roleService.getAllRoles());
        return mav;
    }
}

