package com.crud.crud_operation.dto;

import com.crud.crud_operation.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    private String name;

    private String email;

    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

}
