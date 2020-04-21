package com.jeremiahseagraves.dou.k8s.helloworld.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jeremiahseagraves.dou.k8s.helloworld.component.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    @Qualifier("cognitoAuthenticator")
    private final AuthenticationService authenticationService;

    /**
     * Performs the call to the attempt of login
     *
     * @param username the username which wants to be logged
     * @param password the password related to the username
     * @return a map containing the JWT
     */
    public Map<String, String> login(String username, String password) {
        log.debug("Attempting to login with username: {} and password: {}", username, password);
        return authenticationService.authenticate(username, password);
    }

    public Map<String, String> getTokenMap(String code) throws URISyntaxException, JsonProcessingException {
        log.debug("Getting token with code: {}", code);
        return authenticationService.getTokenMap(code);
    }

    public Map<String, String> getUserInfoMap(String token) throws URISyntaxException, JsonProcessingException {
        log.debug("Getting user info with token: {}", token);
        return authenticationService.getUserInfo(token);
    }

}
