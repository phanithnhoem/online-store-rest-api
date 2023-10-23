package com.academy.onlinestore.api.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String uuid;
    @Column(nullable = false, unique = true)
    private String code;
    @Column(nullable = false)
    private String name;
    private String slug;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String image;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cate_id")
    Category category;

}
