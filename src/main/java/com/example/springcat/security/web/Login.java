package com.example.springcat.security.web;

import static java.lang.Boolean.TRUE;
import static lombok.AccessLevel.PACKAGE;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = PACKAGE)
class Login {

    /**
     * email
     */
    @Email
    @NotBlank
    String email;

    /**
     * password
     */
    String paswrd;

    /**
     * remember me
     */
    Boolean rememberMe = TRUE;
}
