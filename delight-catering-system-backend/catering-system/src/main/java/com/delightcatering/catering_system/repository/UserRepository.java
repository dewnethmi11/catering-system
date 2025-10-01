package com.delightcatering.catering_system.repository;

import com.delightcatering.catering_system.entity.User;
import com.delightcatering.catering_system.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByRole(UserRole role);
    List<User> findByActiveTrue();

    @Query("SELECT u FROM User u WHERE u.firstName LIKE %?1% OR u.lastName LIKE %?1%")
    List<User> findByNameContaining(String name);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}