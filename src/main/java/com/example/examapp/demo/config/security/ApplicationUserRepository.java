package com.example.examapp.demo.config.security;

import java.util.Optional;

public interface ApplicationUserRepository {

    public Optional<ApplicationUser> findByUsername(String username);
    long save(ApplicationUser user);

}
