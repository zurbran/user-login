package com.usermanager.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "application_user")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;
    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    private OffsetDateTime created;
    private OffsetDateTime lastLogin;
    private Boolean isActive;
    @OneToMany(mappedBy = "user")
    private Set<UserPhone> phones;
}
