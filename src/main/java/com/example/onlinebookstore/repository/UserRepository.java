package com.example.onlinebookstore.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.onlinebookstore.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    public Optional<UserEntity> findUserByEmail(String email);

    public Boolean existsByEmail(String email);

}
