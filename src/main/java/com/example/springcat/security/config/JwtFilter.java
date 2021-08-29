package com.example.springcat.security.config;

import static com.example.springcat.security.jwt.JwtTokenProvider.BEARER_TOKEN;
import static lombok.AccessLevel.PACKAGE;

import com.example.springcat.security.jwt.JwtTokenProvider;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 繼承 OncePerRequestFilter 用來接收 Servlet 的 Request 過濾處理, 所有的 Request 只會被過濾一次
 * 依賴 {@link JwtTokenProvider} 是因為這邊是 Jwt token 實際檢核的入口點,
 * 因為將 Session / Cookie 的相關概念都交由 JWT 處理了, 故 WebSecurityConfig 的 SessionCreationPolicy 會設定為 STATELESS
 *
 * 實作參考
 * ref: https://waynestalk.com/spring-security-jwt-jpa-springdoc-explained/
 * ref: https://www.toptal.com/spring/spring-security-tutorial
 * ref: https://github.com/cloudtu/spring-security-jwt-auth-teach
 */
@Slf4j
@Component
@RequiredArgsConstructor(access = PACKAGE)
class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 所有 Servlet 的 Request 都會在此被攔截, 與此我們可以做一些 log 或是做 jwt 的 authentication 驗證,
     * 透過 {@link JwtTokenProvider} 將 token 解析回 spring security 的 {@link org.springframework.security.core.Authentication},
     * 並將其放置回 Spring security context ({@link SecurityContextHolder#getContext()})
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        val jwt = retrieveJwt(request);

        if (Strings.isNotBlank(jwt)) {
            val authentication = jwtTokenProvider.verify(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.trace("Authorized the request with JWT({})", authentication.getName());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 取得 Request 的 http AUTHORIZATION header 的 jwt
     *
     * @param request
     * @return
     */
    private String retrieveJwt(HttpServletRequest request) {
        val authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Strings.isNotBlank(authorization) && authorization.startsWith(BEARER_TOKEN)) {
            return authorization.split("\\s+")[1];
        }
        return "";
    }
}
