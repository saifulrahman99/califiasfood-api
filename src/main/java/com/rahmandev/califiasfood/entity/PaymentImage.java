package com.rahmandev.califiasfood.entity;

import com.rahmandev.califiasfood.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.PAYMENT_IMAGE)
public class PaymentImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "path")
    private String path;
    @Column(name = "size")
    private Long size;
    @Column(name = "content_type")
    private String contentType;
}
