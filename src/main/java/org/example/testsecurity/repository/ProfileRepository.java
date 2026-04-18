package org.example.testsecurity.repository;

import org.example.testsecurity.jpa.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    boolean existsByEmail(String email);
    Optional<Profile> findByEmail(String email);
}
