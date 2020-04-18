package com.jeremiahseagraves.dou.k8s.helloworld.component;

import com.jeremiahseagraves.dou.k8s.helloworld.util.AuthenticationHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component("cognitoAuthenticator")
public class CognitoAuthenticationService implements AuthenticationService {

    @Value("${aws.cognito.poolId}")
    private String POOL_ID;

    @Value("${aws.cognito.clientAppId}")
    private String CLIENT_APP_ID;

    @Value("${aws.region}")
    private String REGION;

    @Value("${aws.cognito.clientAppSecret}")
    private String CLIENT_APP_SECRET;

    @Override
    public Map<String, String> authenticate(String username, String password) {
        Map<String, String> tokenMap = new HashMap<>();
        Optional<String> jwt = getJWT(username, password);
        if (!jwt.isPresent()) {
            tokenMap.put("error", "Couldn't authenticate with given credentials");
        } else {
            tokenMap.put("token", jwt.get());
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
