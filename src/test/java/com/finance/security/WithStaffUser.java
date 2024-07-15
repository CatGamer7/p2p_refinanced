package com.finance.security;

import com.finance.model.user.UserAuthority;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithStaffUserSecurityContextFactory.class)
public @interface WithStaffUser {
    String username() default "email";
    String password() default "digest";
    long id() default 0L;
}
