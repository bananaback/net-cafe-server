package dev.bananaftmeo.netcafeserver.models;

import java.time.LocalDateTime;

import dev.bananaftmeo.netcafeserver.enums.UserSessionEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_session")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, unique = false)
    private ApplicationUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, unique = false)
    private Computer computer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = false)
    private UserSessionEnum status;

    @Column(nullable = false, unique = false)
    private LocalDateTime checkinAt;

    @Column(nullable = true, unique = false)
    private LocalDateTime checkoutAt;

}
