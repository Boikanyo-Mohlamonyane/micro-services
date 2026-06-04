package com.crud.crud_operation.dto;

import com.crud.crud_operation.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String name;

    private String email;

    private Role role;
    private LocalDateTime createdAt;
}
