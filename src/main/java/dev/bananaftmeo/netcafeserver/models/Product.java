package dev.bananaftmeo.netcafeserver.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String description;
    @Column(nullable = false, unique = false)
    private float price;
    @Column(nullable = false, unique = false)
    private int remainQuantity;
    @Column(nullable = false)
    private String productImageLink;

    @ManyToOne(fetch = FetchType.EAGER)
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "product")
    Set<OrderItem> orderItems;
}
