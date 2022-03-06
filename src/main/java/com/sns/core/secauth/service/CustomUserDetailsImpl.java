package com.sns.core.secauth.service;

import com.sns.core.secauth.model.User;
import com.sns.core.secauth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class CustomUserDetailsImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> exceptionSupplier =
                () -> new UsernameNotFoundException(
                        "Problem during authentication!");
        User user = userRepository
                .findUserByUsername(username)
                .orElseThrow(exceptionSupplier);

        return new CustomUserDetails(user);
    }

}