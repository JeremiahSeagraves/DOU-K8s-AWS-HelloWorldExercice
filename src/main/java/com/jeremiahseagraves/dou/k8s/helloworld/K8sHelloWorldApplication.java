package com.jeremiahseagraves.dou.k8s.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class K8sHelloWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(K8sHelloWorldApplication.class, args);
    }

}
