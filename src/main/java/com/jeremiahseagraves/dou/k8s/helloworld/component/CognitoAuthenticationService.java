package com.jeremiahseagraves.dou.k8s.helloworld.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Component("cognitoAuthenticator")
@Slf4j
@RequiredArgsConstructor
public class CognitoAuthenticationService implements AuthenticationService {

    @Value("${spring.security.oauth2.client.registration.cognito.authorization-grant-type}")
    private String GRANT_TYPE;

    @Value("${spring.security.oauth2.client.registration.cognito.client-id}")
    private String CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.cognito.redirect-uri}")
    private String REDIRECT_URI;

    @Value("${spring.security.oauth2.client.registration.cognito.scope}")
    private String SCOPE;

    @Value("${spring.security.oauth2.client.provider.cognito.token-uri}")
    private String TOKEN_URI;

    @Value("${spring.security.oauth2.client.provider.cognito.user-info-uri}")
    private String USER_INFO_URI;

    @Override
    public Map<String, String> authenticate(String username, String password) {
        Map<String, String> tokenMap = new HashMap<>();
        ObjectReader objectReader = new ObjectMapper().readerFor(Map.class);
        try {
            tokenMap = objectReader.readValue("");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return tokenMap;
    }

    @Override
    public Map<String, String> getTokenMap(String code) throws URISyntaxException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        final URI tokenUri = new URI(TOKEN_URI);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", GRANT_TYPE);
        map.add("client_id", CLIENT_ID);
        map.add("code", code);
        map.add("redirect_uri", REDIRECT_URI);
        map.add("scope", SCOPE);

        System.out.println(map);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, httpHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(tokenUri, request, String.class);
        final String responseBody = response.getBody();
        log.info(responseBody);

        ObjectReader objectReader = new ObjectMapper().readerFor(Map.class);

        return objectReader.readValue(responseBody);
    }

    @Override
    public Map<String, String> getUserInfo(String token) throws JsonProcessingException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final URI userInfoUri = new URI(USER_INFO_URI);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(userInfoUri, HttpMethod.GET, entity, String.class);
        final String responseBody = response.getBody();
        log.info(responseBody);

        ObjectReader objectReader = new ObjectMapper().readerFor(Map.class);

        return objectReader.readValue(responseBody);
    }

}
