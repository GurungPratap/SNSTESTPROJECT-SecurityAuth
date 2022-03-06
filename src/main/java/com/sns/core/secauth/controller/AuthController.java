package com.sns.core.secauth.controller;

import com.sns.core.secauth.model.User;
import com.sns.core.secauth.model.UserAuthority;
import com.sns.core.secauth.payloads.JwtResponse;
import com.sns.core.secauth.payloads.LoginRequest;
import com.sns.core.secauth.payloads.MessageResponse;
import com.sns.core.secauth.payloads.SignUpRequest;
import com.sns.core.secauth.repository.UserAuthorityRepository;
import com.sns.core.secauth.repository.UserRepository;
import com.sns.core.secauth.security.JwtUtils;
import com.sns.core.secauth.service.CustomUserDetails;
import com.sns.core.secauth.service.UserAuthenticationProviderService;
import com.sns.core.secauth.util.EAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserAuthenticationProviderService authenticationProvider;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserAuthorityRepository userAuthorityRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    /**
     *
     * @param loginRequest
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User user = userRepository.findUserByUsername(authentication.getPrincipal().toString()).get();
        CustomUserDetails userDetails = new CustomUserDetails(user);
        List<String> authorities = userDetails.getAuthorities().stream()
                                        .map(item -> item.getAuthority())
                                        .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUsername(),
                authorities));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        // Create new user's account
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .algorithm(signUpRequest.getEncryptionAlgorithm())
                .build();
        userRepository.save(user);
        List<String> strAuthorities = signUpRequest.getAuthorities();
        List<UserAuthority> userAuthorities = new ArrayList<>();
        if (strAuthorities == null) {
            UserAuthority userAuthority = UserAuthority.builder()
                                                        .description(EAuthority.READ)
                                                        .user(user)
                                                        .build();
            userAuthorityRepository.save(userAuthority);
        } else {
            strAuthorities.forEach(authority -> {
                switch (authority.toLowerCase()) {
                    case "read":
                        UserAuthority readAuthority = UserAuthority.builder()
                                .description(EAuthority.READ)
                                .user(user)
                                .build();
                        userAuthorityRepository.save(readAuthority);
                        break;
                    case "write":
                        UserAuthority writeAuthority = UserAuthority.builder()
                                .description(EAuthority.WRITE)
                                .user(user)
                                .build();
                        userAuthorityRepository.save(writeAuthority);
                        break;
                }
            });
        }
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}