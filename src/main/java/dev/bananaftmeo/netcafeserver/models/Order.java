package dev.bananaftmeo.netcafeserver.models;

import java.time.LocalDateTime;
import java.util.Set;

import dev.bananaftmeo.netcafeserver.enums.OrderStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "user_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime timeCreated;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    ApplicationUser user;

    @OneToMany(mappedBy = "order")
    Set<OrderItem> orderItems;
}
