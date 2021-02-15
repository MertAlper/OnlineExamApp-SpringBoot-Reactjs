package com.example.examapp.demo.config.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers( "/api/registration/**").permitAll()
                .antMatchers(HttpMethod.POST, "/examApp/api/exams/**").hasRole(ApplicationUserRole.INSTRUCTOR.name())
                .antMatchers(HttpMethod.PUT, "/examApp/api/exams/**").hasRole(ApplicationUserRole.INSTRUCTOR.name())
                .antMatchers(HttpMethod.DELETE, "/examApp/api/exams/**").hasRole(ApplicationUserRole.INSTRUCTOR.name())
                .antMatchers(HttpMethod.POST, "/examApp/api/students/**").hasRole(ApplicationUserRole.STUDENT.name())
                .antMatchers(HttpMethod.PUT, "/examApp/api/students/**").hasRole(ApplicationUserRole.STUDENT.name())
                .antMatchers(HttpMethod.DELETE, "/examApp/api/students/**").hasRole(ApplicationUserRole.STUDENT.name())
                .antMatchers(HttpMethod.GET, "/examApp/api/**").hasAnyRole(ApplicationUserRole.STUDENT.name(), ApplicationUserRole.INSTRUCTOR.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
}

