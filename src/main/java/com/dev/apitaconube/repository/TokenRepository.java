package com.dev.apitaconube.repository;

import com.dev.apitaconube.domain.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    // Util para cuando armemos validacion contra revocados / logout.
    Optional<Token> findByToken(String token);
}
