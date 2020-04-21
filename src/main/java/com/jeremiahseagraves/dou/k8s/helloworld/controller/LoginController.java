package com.jeremiahseagraves.dou.k8s.helloworld.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jeremiahseagraves.dou.k8s.helloworld.component.HelloLambdaInvoker;
import com.jeremiahseagraves.dou.k8s.helloworld.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.net.URISyntaxException;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    private final HelloLambdaInvoker helloLambdaInvoker;

    @GetMapping("/logged")
    public ModelAndView logged(String code, String state) throws URISyntaxException, JsonProcessingException {
        log.info("Logged in with code: {}, state: {}", code, state);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("logged");

        final Map<String, String> tokenMap = loginService.getTokenMap(code);
        final String bearerToken = tokenMap.get("access_token");

        final Map<String, String> userInfoMap = loginService.getUserInfoMap(bearerToken);
        final String username = userInfoMap.get("nickname");

        final String salutation = helloLambdaInvoker.invokeHello(username);
        if (!salutation.isEmpty()) {
            modelAndView.addObject("salutation", salutation);
        } else {
            modelAndView.addObject("wrongSalutation", "Lambda invocation wasn't correct.");
        }
        return modelAndView;
    }
}
