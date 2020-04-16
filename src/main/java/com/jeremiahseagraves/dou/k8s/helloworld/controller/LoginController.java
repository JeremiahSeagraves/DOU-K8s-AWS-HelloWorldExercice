package com.jeremiahseagraves.dou.k8s.helloworld.controller;

import com.jeremiahseagraves.dou.k8s.helloworld.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public ModelAndView login(Model model) {
        model.addAttribute("user", new User());
        return new ModelAndView("login");
    }


    @PostMapping("/login")
    public ModelAndView tryLogin(@Valid User user, BindingResult bindingResult) {
        log.info("Attempting to login with credentials: {}", user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.setViewName(bindingResult.hasErrors() ? "login" : "logged");
        return modelAndView;
    }
}
