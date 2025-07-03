// service/UserService.java
package com.example.taskmanager.service;

import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepo;

   @Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            // ✅ Explicitly map the full ROLE_XXX name
            java.util.List.of(new SimpleGrantedAuthority(user.getRole().name()))
    );
}

}
