package com.rahmandev.califiasfood.entity;

import com.rahmandev.califiasfood.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.USER_ACCOUNT)
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "is_enabled")
    private Boolean isEnabled;
    @Column(name = "otp")
    private Integer otp;
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
    @Column(name = "deleted_at", nullable = true)
    private Date deletedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> role;
}
