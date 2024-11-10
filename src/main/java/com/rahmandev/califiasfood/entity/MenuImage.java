package com.rahmandev.califiasfood.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rahmandev.califiasfood.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.MENU_IMAGE)
public class MenuImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "path",nullable = false)
    private String path;
    @Column(name = "size",nullable = false)
    private Long size;
    @Column(name = "content_type",nullable = false)
    private String contentType;

    @ManyToOne
    @JoinColumn(name = "menu_id",nullable = false)
    private Menu menu;
}
