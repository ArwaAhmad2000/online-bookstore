package com.example.onlinebookstore.service;

import org.springframework.stereotype.Service;
import com.example.onlinebookstore.entity.Role;
import com.example.onlinebookstore.repository.RoleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name).orElseThrow();
    }

}
