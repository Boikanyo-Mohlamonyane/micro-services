package com.crud.crud_operation.service;

import com.crud.crud_operation.config.PasswordConfig;
import com.crud.crud_operation.dto.AuthRequest;
import com.crud.crud_operation.dto.AuthResponse;
import com.crud.crud_operation.dto.LoginRequest;
import com.crud.crud_operation.dto.LoginResponse;
import com.crud.crud_operation.model.User;
import com.crud.crud_operation.repository.UserRepository;
import com.crud.crud_operation.security.Jutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordConfig passwordConfig;
    @Autowired
    private Jutils jutils;

    public AuthResponse registerUsers(AuthRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setPassword(passwordConfig.passwordEncoder().encode(request.getPassword()));
        userRepository.save(user);

        return new AuthResponse(user.getName(), user.getEmail(), user.getRole(), user.getCreatedAt());
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("No such user in our database."));

        if (!passwordConfig.passwordEncoder().matches(request.getPassword(), user.getPassword())) {

            throw new RuntimeException("Invalid Credentials");
        }
        String token = jutils.generateToken(user.getEmail(),"ROLE_" + user.getRole().name());


        return new LoginResponse(token);

    }

    public User getUserByID(Long id) {
        User user = userRepository.findById(String.valueOf(id)).orElseThrow(() -> new RuntimeException("User Not Found"));

        return user;
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        return user;
    }

    public List<User> getAllUsers(){
        List<User> users =userRepository.findAll();
        return  users;
    }

    public String deletUser(Long id){

        User user = getUserByID( id);
        userRepository.delete(user);

        return "User with :"+user.getId()+" ,has been deleted successfully";

    }

    public User updateUser(Long id, AuthRequest request){
        User user =getUserByID(id);
        user.setName(request.getName());
        user.setPassword(request.getPassword());

        return userRepository.save(user);
    }

}
