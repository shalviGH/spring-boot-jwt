package dev.SpringSecurityJWT.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRoleController {


    @GetMapping("/accessAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public String accessAdmin(){
        return "Access admin";
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/accessUser")
    public String accessUser(){
        return "Access user ----------";
    }

    @PreAuthorize("hasRole('INVITED')")
    @GetMapping("/accessInvited")
    public String accessInvited(){
        return "Access invited -----";
    }
}
