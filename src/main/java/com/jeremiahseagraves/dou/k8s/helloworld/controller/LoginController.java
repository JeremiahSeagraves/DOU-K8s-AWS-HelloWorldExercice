package com.jeremiahseagraves.dou.k8s.helloworld.controller;

import com.jeremiahseagraves.dou.k8s.helloworld.component.HelloLambdaInvoker;
import com.jeremiahseagraves.dou.k8s.helloworld.domain.User;
import com.jeremiahseagraves.dou.k8s.helloworld.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    private final HelloLambdaInvoker helloLambdaInvoker;

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
        modelAndView.setViewName("login");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        } else {
            final Map<String, String> tokenMap = loginService.login(user.getUsername(), user.getPassword());
            if (tokenMap.containsKey("token")) {
                modelAndView.addObject("token", tokenMap.get("token"));
                final String salutation = helloLambdaInvoker.invokeHello(user.getUsername());
                if (!salutation.isEmpty()) {
                    modelAndView.addObject("salutation", salutation);
                } else {
                    modelAndView.addObject("wrongSalutation", "Lambda invocation wasn't correct.");
                }
                modelAndView.setViewName("logged");
            } else {
                modelAndView.addObject("error", tokenMap.get("error"));
            }
        }
        return modelAndView;
    }
}
