package com.finance.model.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "Base_Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_id")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password_digest")
    private String passwordDigest;

    @Column(name = "is_staff")
    private boolean staff;

    @Column(name = "is_active")
    private boolean active;

    public void setFields(User in) {
        name = in.getName();
        email = in.getName();
        staff = in.isStaff();
        active = in.isActive();
    }
}
