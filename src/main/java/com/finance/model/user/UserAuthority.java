package com.finance.model.user;

import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority {

    static public final UserAuthority STAFF = new UserAuthority(true);
    static public final UserAuthority USER = new UserAuthority(false);

    private boolean staff;

    public UserAuthority(Boolean staff) {
        this.staff = staff;
    }

    @Override
    public String getAuthority() {
        return staff ? "staff" : "user";
    }
}
