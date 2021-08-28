package com.example.springcat.security.web;

import static java.lang.Boolean.TRUE;
import static lombok.AccessLevel.PACKAGE;

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
