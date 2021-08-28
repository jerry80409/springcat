package com.example.springcat.security.config;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "jwt")
class JwtProperties {

    /**
     * JWT secret for signature.
     */
    private @Valid @NotBlank String secret;
}
