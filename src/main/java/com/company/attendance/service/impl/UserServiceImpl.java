package com.company.attendance.service.impl;

import com.company.attendance.entity.User;
import com.company.attendance.exception.CustomException;
import com.company.attendance.repository.UserRepository;
import com.company.attendance.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new CustomException("Email already exists");
        }

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found"));
    }

    @Override
    public User updateUser(Long id, User updatedUser) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found"));

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setRole(updatedUser.getRole());

        return userRepository.save(user);
    }

    @Override
    public String deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found"));

        userRepository.delete(user);

        return "User deleted successfully";
    }
}