package com.finance.security;

import com.finance.model.user.UserAuthority;
import com.finance.model.user.UserSecurityAdapter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;

public class WithStaffUserSecurityContextFactory implements WithSecurityContextFactory<WithStaffUser> {

    @Override
    public SecurityContext createSecurityContext(WithStaffUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UserSecurityAdapter principal = new UserSecurityAdapter(annotation.id(), annotation.username(),
                annotation.password(), Arrays.asList(UserAuthority.STAFF));
        Authentication auth =
                new UsernamePasswordAuthenticationToken(principal, annotation.password(), principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}
