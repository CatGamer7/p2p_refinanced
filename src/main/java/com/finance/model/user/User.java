package com.finance.model.user;

import com.finance.model.offer.Offer;
import com.finance.model.request.Request;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "Base_Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_id")
    private Long userId;

    @Column(name = "name")
    @NotBlank(message = "Must provide a name for user")
    private String name;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Must provide unique email for user")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Column(name = "password_digest")
    @NotBlank(message = "Must provide a password for user")
    private String passwordDigest;

    @Column(name = "is_staff")
    private boolean staff = false;

    @Column(name = "is_active")
    private boolean active = true;

    @CreationTimestamp
    @Column(name="created_timestamp")
    private LocalDateTime createdTimestamp = null;

    @OneToMany(mappedBy = "borrower")
    private List<Request> requests = null;

    @OneToMany(mappedBy = "lender")
    private List<Offer> offers = null;

    public void setFields(User in) {
        name = in.getName();
        email = in.getEmail();
        staff = in.isStaff();
        active = in.isActive();
    }
}
