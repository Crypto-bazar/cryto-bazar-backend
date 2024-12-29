package com.ororura.cryptobazar.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "person")
public class Person implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_id_gen")
    @SequenceGenerator(name = "person_id_gen", sequenceName = "person_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false, length = 30)
    private String phoneNumber;

    @ColumnDefault("false")
    @Column(name = "account_verified")
    private Boolean accountVerified;

    @ColumnDefault("false")
    @Column(name = "document_verified")
    private Boolean documentVerified;

    @PrePersist
    public void setDefaultRole() {
        if (this.role == null) {
            Role defaultRole = new Role();
            defaultRole.setId(1);
            this.role = defaultRole;
        }
    }

    @ColumnDefault("1")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @ColumnDefault("now()")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("now()")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    public void setUser(String username) {
        this.login = username;
    }

    public String getUser() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}