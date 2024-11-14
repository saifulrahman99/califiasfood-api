package com.rahmandev.califiasfood.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rahmandev.califiasfood.constant.ConstantTable;
import com.rahmandev.califiasfood.constant.MenuStatus;
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
@Table(name = ConstantTable.MENU)
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "price", nullable = false)
    private Long price;
    @Column(name = "topping_is_active", nullable = false)
    private Boolean toppingIsActive;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "menu_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MenuStatus menuStatus;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuImage> images;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "discount_id", nullable = false, unique = true,referencedColumnName = "id")
    @JsonManagedReference
    private Discount discount;

    @Column(name = "delete_at", nullable = true)
    private Date deleteAt;
}


