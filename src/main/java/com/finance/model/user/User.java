package com.finance.model.user;

import com.finance.model.offer.Offer;
import com.finance.model.request.Request;
import jakarta.persistence.*;
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
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password_digest")
    private String passwordDigest;

    @Column(name = "is_staff")
    private boolean staff;

    @Column(name = "is_active")
    private boolean active;

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
