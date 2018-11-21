package com.systelab.studies.repository;

import com.systelab.studies.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findById(@Param("id") UUID id);
    User findByLogin(@Param("login") String login);

}