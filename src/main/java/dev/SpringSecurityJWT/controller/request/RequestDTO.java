package dev.SpringSecurityJWT.controller.request;

import dev.SpringSecurityJWT.model.RoleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String user_name;

    @NotBlank
    private String password;

    private Set<String> roles;
}
