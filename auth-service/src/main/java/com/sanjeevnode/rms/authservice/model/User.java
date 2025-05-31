package com.sanjeevnode.rms.authservice.model;

import com.sanjeevnode.rms.authservice.enums.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'USER'")
    private Role role = Role.USER;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public String getCreatedAt() {
        if (createdAt == null) return null;
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getUpdatedAt() {
        if (updatedAt == null) return null;
        return updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
