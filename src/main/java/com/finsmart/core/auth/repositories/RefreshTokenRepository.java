package com.finsmart.core.auth.repositories;

import com.finsmart.core.auth.entities.RefreshToken;
import com.finsmart.core.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);

    @Transactional
    @Modifying
    @Query("delete from RefreshToken r where r.user = ?1")
    void deleteByUserTokenTable(User user);
}