package org.sekiro.InventoryManagementSystem.controller;

import jakarta.validation.Valid;
import org.sekiro.InventoryManagementSystem.dto.LoginRequest;
import org.sekiro.InventoryManagementSystem.dto.RegisterRequest;
import org.sekiro.InventoryManagementSystem.dto.Response;
import org.sekiro.InventoryManagementSystem.dto.UserDTO;
import org.sekiro.InventoryManagementSystem.entities.User;
import org.sekiro.InventoryManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable(name = "id") Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteUser(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping("/transactions/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getUserAndTransactions(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserTransactions(id));
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentLoggedInUser());
    }
}
