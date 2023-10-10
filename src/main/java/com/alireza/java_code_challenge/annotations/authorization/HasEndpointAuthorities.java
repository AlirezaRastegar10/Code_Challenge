package com.alireza.java_code_challenge.annotations.authorization;

import com.alireza.java_code_challenge.entity.enumeration.Role;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface HasEndpointAuthorities {

    Role[] authorities();

}
