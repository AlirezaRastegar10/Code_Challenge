package com.alireza.java_code_challenge.annotations.authorization;

import com.alireza.java_code_challenge.exceptions.api.ApiException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

@Aspect
@Component
public class HasEndpointAuthoritiesAspect {

    @Before("within(@org.springframework.web.bind.annotation.RestController *) && @annotation(authorities)")
    public void hasAuthorities(final HasEndpointAuthorities authorities) {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        if (!Objects.isNull(securityContext)) {
            final Authentication authentication = securityContext.getAuthentication();
            if (!Objects.isNull(authentication)) {
                final String username = authentication.getName();

                final Collection<? extends GrantedAuthority> userAuthorities = authentication.getAuthorities();

                if (Stream.of(authorities.authorities()).noneMatch(authorityName -> userAuthorities.stream().anyMatch(userAuthority ->
                        authorityName.name().equals(userAuthority.getAuthority())))) {

                    throw new ApiException("User {" + username + "} does not have the correct authorities required by endpoint");
                }
            } else {
                throw new ApiException("The authentication is null when checking endpoint access for user request");
            }
        } else {
            throw new ApiException("The security context is null when checking endpoint access for user request");
        }
    }
}
