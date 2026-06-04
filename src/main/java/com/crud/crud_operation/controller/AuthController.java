package com.crud.crud_operation.controller;

import com.crud.crud_operation.dto.AuthRequest;
import com.crud.crud_operation.dto.AuthResponse;
import com.crud.crud_operation.dto.LoginRequest;
import com.crud.crud_operation.dto.LoginResponse;
import com.crud.crud_operation.model.User;
import com.crud.crud_operation.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/auth/register")
    public AuthResponse registerUsers(@RequestBody AuthRequest request) {
        return authService.registerUsers(request);
    }

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
     return authService.login(request);
    }



    @GetMapping("/users/{id}")
    public User getById(@PathVariable Long id) {
        return authService.getUserByID(id);
    }


    @GetMapping("/users/email/{email}")
    public User getByEmail(@PathVariable String email) {
        return authService.getUserByEmail(email);
    }


    @PutMapping("/users/{id}")
    public User update(@PathVariable Long id, @RequestBody AuthRequest user) {
        return authService.updateUser(id, user);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/delete/{id}")
    public void delete(@PathVariable Long id) {
        authService.deletUser(id);
    }


    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    public List<User> getAll() {
        return authService.getAllUsers();
    }
}
