package dev.SpringSecurityJWT.service;

import dev.SpringSecurityJWT.model.UserEntity;
import dev.SpringSecurityJWT.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDatailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.getName(username)
                .orElseThrow(() -> new UsernameNotFoundException("El user con "+ username + " no existe"));

        Collection<? extends GrantedAuthority> authorities = userEntity.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getName().name())))
                .collect(Collectors.toSet());

        //devolviendo un nuevo usuario
        return new User(userEntity.getUser_name(),
                userEntity.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }
}
