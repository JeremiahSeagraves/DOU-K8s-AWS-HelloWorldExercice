package com.jeremiahseagraves.dou.k8s.helloworld.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class User {
    @NotBlank
    @Size(min = 5, message = "Username must be at least 5 characters long")
    private String username;

    @NotBlank
    @Size(min = 8, message = "Password  must be at least 8 characters long")
    private String password;
}
