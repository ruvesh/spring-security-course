package io.github.ruvesh.springsecurityclient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.ruvesh.springsecurityclient.entity.VerificationToken;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

	Optional<VerificationToken> findByToken(String token);

	Optional<VerificationToken> findByUserId(Long id);

}
