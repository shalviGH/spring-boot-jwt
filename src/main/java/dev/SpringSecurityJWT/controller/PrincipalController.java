package dev.SpringSecurityJWT.controller;

import dev.SpringSecurityJWT.controller.request.RequestDTO;
import dev.SpringSecurityJWT.model.ERole;
import dev.SpringSecurityJWT.model.RoleEntity;
import dev.SpringSecurityJWT.model.UserEntity;
import dev.SpringSecurityJWT.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
//@RequestMapping("api/test")
public class PrincipalController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/hello")
    public String hello(){
        return "hello spring";
    }

    @GetMapping("/security")
    public String security(){
        return "hello spring security";
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody RequestDTO requestDTO){
        Set<RoleEntity> roles = requestDTO.getRoles().stream()
                .map(role -> RoleEntity.builder()
                        .name(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        UserEntity user = UserEntity.builder()
                .user_name(requestDTO.getUser_name())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .email(requestDTO.getEmail())
                .roles(roles).build();

        userRepository.save(user);

        return new ResponseEntity<>(user, HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable String id){
        userRepository.deleteById(Long.parseLong(id));

        return "Usuario eliminado con el id ".concat(id);
    }
}
