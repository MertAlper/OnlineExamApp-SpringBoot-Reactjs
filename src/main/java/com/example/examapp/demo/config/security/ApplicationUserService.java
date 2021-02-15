package com.example.examapp.demo.config.security;

import com.example.examapp.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService {

    @Autowired
    private ApplicationUserRepository appUserRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("user with username %s doesn't exist", username))
                );
    }

    /**
     * Checks if a user with the username already exists
     * If user exists, throws IllegalStateException.
     *
     * @param user
     */
    public void signUpUser(ApplicationUser user){
        boolean userExists = appUserRepository.findByUsername(user.getUsername())
                .isPresent();

        if (userExists) {
            throw new IllegalStateException("user already exists");
        }

        String encodedPasswod = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPasswod);

        appUserRepository.save(user);
    }

}
