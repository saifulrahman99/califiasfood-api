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
@Table(name = ConstantTable.CATEGORY)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "name",nullable = false, unique = true)
    private String name;
}
