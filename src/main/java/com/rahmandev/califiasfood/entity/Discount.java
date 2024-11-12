package com.rahmandev.califiasfood.entity;

import com.rahmandev.califiasfood.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.DISCOUNT)
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "discount_amount")
    private Long discountAmount;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "is_active")
    private Boolean isActive;
}
