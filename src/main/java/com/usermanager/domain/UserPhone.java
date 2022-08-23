package com.usermanager.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserPhone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    private Long number;
    private int cityCode;
    private int countryCode;
    @ManyToOne
    private User user;
}
