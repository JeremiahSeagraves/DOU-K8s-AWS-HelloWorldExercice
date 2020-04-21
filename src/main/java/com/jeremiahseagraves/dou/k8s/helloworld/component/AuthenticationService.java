package com.jeremiahseagraves.dou.k8s.helloworld.component;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.URISyntaxException;
import java.util.Map;

public interface AuthenticationService {
    Map<String, String> authenticate(String username, String password);

    Map<String, String> getTokenMap(String code) throws URISyntaxException, JsonProcessingException;

    Map<String, String> getUserInfo(String token) throws JsonProcessingException, URISyntaxException;
}
