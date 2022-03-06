package com.sns.core.secauth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationProviderService implements AuthenticationProvider {
    Logger logger = LoggerFactory.getLogger(CustomUserDetailsImpl.class);

    @Autowired
    private CustomUserDetailsImpl userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private SCryptPasswordEncoder sCryptPasswordEncoder;

    /**
     * The method first loads the user by its username and then verifies if
     * the password matches the hash stored in the database . The verification
     * depends on the algorithm used to hash the user’s password.
     * @param authentication Authentication object
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName().trim();
        String password = authentication.getCredentials().toString().trim();
        CustomUserDetails user = userDetailsService.loadUserByUsername(username);
        switch (user.getUser().getAlgorithm()) {
            case BCRYPT:
                return checkPassword(user, password, bCryptPasswordEncoder);
            case SCRYPT:
                return checkPassword(user, password, sCryptPasswordEncoder);
        }
        return null;
    }

    /**
     * the method specify that the supported Authentication
     * implementation type is UsernamePasswordAuthenticationToken.
     */
    @Override
    public boolean supports(Class<?> authentication) {
       return UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(authentication);
    }
    /**
     * the method uses the password encoder sent as a parameter to validate
     * that the raw password received from the user input matches the encoding in the database.
     * If the password is valid, it returns an instance of an implementation
     * of the Authentication contract.
     */
    private Authentication checkPassword(CustomUserDetails user,String rawPassword, PasswordEncoder encoder) {
        if (encoder.matches(rawPassword, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),user.getAuthorities());
        } else {
            logger.error("Bad credentials");
            throw new BadCredentialsException("Bad credentials");
        }
    }
}
