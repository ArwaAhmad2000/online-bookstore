package com.example.onlinebookstore.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.onlinebookstore.entity.UserEntity;
import com.example.onlinebookstore.exception.UserNotFoundException;
import com.example.onlinebookstore.repository.UserRepository;
import lombok.Data;

@Service
@Data
public class UserService {

    private final UserRepository userRepository;

    public UserEntity getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found with that id :" + id));
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow();
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

}
