package com.jeremiahseagraves.dou.k8s.helloworld.component;

import com.jeremiahseagraves.dou.k8s.helloworld.util.AuthenticationHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component("cognitoAuthenticator")
public class CognitoAuthenticationService implements AuthenticationService {

    @Value("${POOL_ID}")
    private String POOL_ID;

    @Value("${CLIENT_APP_ID}")
    private String CLIENT_APP_ID;

    @Value("${REGION}")
    private String REGION;

    @Value("${CLIENT_APP_SECRET}")
    private String CLIENT_APP_SECRET;

    @Override
    public Map<String, String> authenticate(String username, String password) {
        Map<String, String> tokenMap = new HashMap<>();
        String jwt = getJWT(username, password).orElse("none");
        if (jwt.compareTo("none") == 0) {
            tokenMap.put("fail", "Couldn't authenticate with given credentials");
        } else {
            tokenMap.put("token", jwt);
        }
        return tokenMap;
    }

    /**
     * Helper method to validate the user
     *
     * @param username represents the username in the cognito user pool
     * @param password represents the password in the cognito user pool
     * @return returns the JWT token after the validation
     */
    Optional<String> getJWT(String username, String password) {
        AuthenticationHelper helper = new AuthenticationHelper(POOL_ID, CLIENT_APP_ID, CLIENT_APP_SECRET, REGION);
        return Optional.ofNullable(helper.PerformSRPAuthentication(username, password));
    }
}