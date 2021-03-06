package com.jeremiahseagraves.dou.k8s.helloworld.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {

    @GetMapping("")
    public ModelAndView root() {
        return new ModelAndView("index");
    }

    @GetMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
