package com.rahmandev.califiasfood.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rahmandev.califiasfood.constant.ConstantTable;
import com.rahmandev.califiasfood.constant.PaymentStatus;
import com.rahmandev.califiasfood.constant.PaymentType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.PAYMENT)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "payment_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @Column(name = "payment_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_image_id", unique = true, nullable = false)
    private PaymentImage proofOfPayment;

    @OneToOne(mappedBy = "payment")
    @JsonBackReference
    private Bill bill;
}
