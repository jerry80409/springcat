package com.example.springcat.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableConfigurationProperties(JwtProperties.class)
public class JwtTokenProvider {

    private static final String USER_ROLES = "userRoles";
    private static final long ADMIN_EXPIRE_TIME_SEC = 60 * 60 * 24 * 1000;

    @Value("${spring.application.name}")
    private String applicationName;

    private final JwtProperties properties;

    @Autowired JwtTokenProvider(JwtProperties properties) {
        this.properties = properties;
    }

    /**
     * Create the JWT
     *
     * @param auth {@link org.springframework.security.authentication.UsernamePasswordAuthenticationToken}
     */
    public String createToken(Authentication auth) {
        val isAdmin = ((UsernamePasswordAuthenticationToken) auth).getAuthorities()
            .contains(new SimpleGrantedAuthority("ADMIN"));
        return createToken(auth, isAdmin);
    }

    /**
     * Verify the JWT
     */
    public Authentication verify(String token) {
        val jwt = parseToken(token);
        val userRoles = Stream.of(jwt.getClaim(USER_ROLES).asArray(String.class))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
        // Credentials 就用 jwt token 取代
        return new UsernamePasswordAuthenticationToken(jwt.getSubject(), token, userRoles);
    }

    /**
     * Create the JWT
     */
    private String createToken(Authentication auth, Boolean isAdmin) {
        val alg = Algorithm.HMAC256(properties.getSignKey());
        val userRoles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
            .toArray(String[]::new);
        val jwtBuilder = JWT.create()
            .withIssuer(applicationName)    // 簽發者
            .withSubject(auth.getName())    // 對象
            .withAudience(auth.getName())   // 收件人
            .withNotBefore(null)            // 某段時間點前該 JWT 是不可用的
            .withIssuedAt(new Date(System.currentTimeMillis())) // 簽發時間
            .withArrayClaim(USER_ROLES, userRoles);   // 對象 payload


        val expired = System.currentTimeMillis() + properties.getExpireTimeSec();
        val adminExpired = System.currentTimeMillis() + ADMIN_EXPIRE_TIME_SEC;
        if (isAdmin) {
            jwtBuilder.withExpiresAt(new Date(Math.max(expired, adminExpired)));
        } else {
            jwtBuilder.withExpiresAt(new Date(expired));
        }

        val jwt = jwtBuilder.sign(alg);
        log.debug("user({}) token: {}", auth.getName(), jwt);
        return jwt;
    }

    /**
     * Parse the JWT
     *
     * @throws SignatureVerificationException if the signature is invalid.
     */
    private DecodedJWT parseToken(String token) {
        val alg = Algorithm.HMAC256(properties.getSignKey());
        val verifier = JWT.require(alg).build();
        val jwt = verifier.verify(token);
        log.debug("user({}) token verify", jwt.getSubject());
        return jwt;
    }
}