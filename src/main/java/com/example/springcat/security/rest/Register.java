package com.example.springcat.security.rest;

import static lombok.AccessLevel.PACKAGE;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = PACKAGE)
public class Register {

    /**
     * user name
     */
    @NotNull
    String name;

    /**
     * user email for authentication
     */
    @Email
    @NotBlank
    String email;

    /**
     * password for authentication
     */
    @NotBlank
    String paswrd;

    /**
     * avatar
     */
    String avatar;

}
