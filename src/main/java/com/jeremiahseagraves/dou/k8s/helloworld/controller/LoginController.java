package com.jeremiahseagraves.dou.k8s.helloworld.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginController {

    @GetMapping("")
    public ModelAndView index(Model model) {
        model.addAttribute("name", "a name");
        return new ModelAndView("index");
    }

    @GetMapping("/hello/{name}")
    public String greeting(@PathVariable String name, final Model model) {
        model.addAttribute("name", name);
        return "index";
    }


}
