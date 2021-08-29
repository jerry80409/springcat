package com.example.springcat.security.jwt;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    private @Valid @NotBlank String signKey;

    /**
     * JWT token effective time.
     */
    private @Valid @NotNull Long expireTimeSec;
}
