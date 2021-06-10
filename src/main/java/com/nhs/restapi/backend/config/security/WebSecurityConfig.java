package com.nhs.restapi.backend.config.security;

import com.nhs.restapi.backend.service.ClientAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    ClientAppService clientAppService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Authentication configuration.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(clientAppService)
                .passwordEncoder(passwordEncoder);
    }

    /**
     * Authorization configuration.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()

                .authorizeRequests()
                .anyRequest().authenticated()
                .and()

                .csrf().disable()
                .headers().frameOptions().disable();
    }
}
