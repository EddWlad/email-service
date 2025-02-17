package com.tidsec.mail_service.security;

import com.tidsec.mail_service.entities.User;
import com.tidsec.mail_service.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final IUserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findOneByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("Username not found: " + username);
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        user.getRoles().forEach(rol -> {
            roles.add(new SimpleGrantedAuthority(rol.getName()));
        });

        System.out.println("User: " + user.getUsername() + " Roles: " + roles);
        System.out.println("User: " + user.getUsername() + " Password: " + user.getPassword());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
    }
}
