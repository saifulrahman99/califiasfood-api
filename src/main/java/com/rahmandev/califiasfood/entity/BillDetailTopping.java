package com.rahmandev.califiasfood.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rahmandev.califiasfood.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = ConstantTable.BILL_DETAIL_TOPPING)
public class BillDetailTopping {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "bill_detail_id", nullable = false)
    private BillDetail billDetail;

    @ManyToOne
    @JoinColumn(name = "topping_id", nullable = false)
    private Topping topping;

    @Column(name = "price")
    private Long price;
}
