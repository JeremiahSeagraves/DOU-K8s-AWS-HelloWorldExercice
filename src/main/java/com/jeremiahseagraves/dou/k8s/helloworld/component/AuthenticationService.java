package com.jeremiahseagraves.dou.k8s.helloworld.component;

import java.util.Map;

public interface AuthenticationService {
    Map<String, String> authenticate(String username, String password);
}
