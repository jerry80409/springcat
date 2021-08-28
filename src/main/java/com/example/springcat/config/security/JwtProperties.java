package com.example.springcat.config.security;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "jwt")
class JwtProperties {

    /**
     * JWT secret for signature.
     */
    private @Valid @NotBlank String secret;
}
