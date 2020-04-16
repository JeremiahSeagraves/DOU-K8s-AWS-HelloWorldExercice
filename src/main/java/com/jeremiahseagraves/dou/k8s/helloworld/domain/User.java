package com.jeremiahseagraves.dou.k8s.helloworld.domain;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class User {
    @NotBlank
    @Min(5)
    private String username;

    @NotBlank
    @Min(8)
    private String password;
}
