package ru.rishaleva.springBootSecurity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView mav = new ModelAndView("login");
        if (error != null) {
            mav.addObject("errorMessage", "Неверный логин или пароль");
        }
        if (logout != null) {
            mav.addObject("logoutMessage", "Вы вышли из системы");
        }
        return mav;
    }
}

