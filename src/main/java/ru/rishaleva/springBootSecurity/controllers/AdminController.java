package ru.rishaleva.springBootSecurity.controllers;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.rishaleva.springBootSecurity.model.User;
import ru.rishaleva.springBootSecurity.service.RoleService;
import ru.rishaleva.springBootSecurity.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService,
                           RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping({"", "/"})
    public ModelAndView showAllUsers(Principal principal) {
        ModelAndView mav = new ModelAndView("users");
        mav.addObject("user", userService.findByUsername(principal.getName()));
        mav.addObject("listOfUsers", userService.getAllUsers());
        mav.addObject("roles", roleService.getAllRoles());
        return mav;
    }

    @PostMapping("/users")
    public ModelAndView createUser(@ModelAttribute User user,
                                   @RequestParam(value = "roleIds", required = false) List<Long> roleIds) {
        userService.createWithRoles(user, roleIds);
        return new ModelAndView("redirect:/admin");
    }

    @PostMapping("/users/{id}")
    public ModelAndView updateUser(@PathVariable Long id,
                                   @ModelAttribute User user,
                                   @RequestParam(value = "roleIds", required = false) List<Long> roleIds) {
        user.setId(id);
        userService.updateWithRoles(user, roleIds);
        return new ModelAndView("redirect:/admin");
    }

    @PostMapping("/users/{id}/delete")
    public ModelAndView deleteUser(@PathVariable Long id) {
        userService.removeUser(id);
        return new ModelAndView("redirect:/admin");
    }
}

