package com.company.attendance.controller;

import com.company.attendance.entity.User;
import com.company.attendance.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user,
                           HttpServletRequest request) {

        String role = (String) request.getAttribute("userRole");

        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Access Denied: Only ADMIN can create users");
        }

        return userService.createUser(user);
    }

    // ✅ GET ALL USERS
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // ✅ GET USER BY ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // ✅ UPDATE USER
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id,
                           @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // ✅ DELETE USER
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id,
                             HttpServletRequest request) {

        String role = (String) request.getAttribute("userRole");

        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Access Denied: Only ADMIN can delete users");
        }

        return userService.deleteUser(id);
    }
}