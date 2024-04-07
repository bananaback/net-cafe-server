package dev.bananaftmeo.netcafeserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.bananaftmeo.netcafeserver.models.RefreshToken;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findById(Long id);

    RefreshToken findByToken(String token);

    List<RefreshToken> findByUserId(Long userId);
}