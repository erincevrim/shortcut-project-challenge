package com.eei.urlshortener.service;

import com.eei.urlshortener.data.request.UserRequest;
import com.eei.urlshortener.data.response.AuthenticationResponse;
import com.eei.urlshortener.entity.User;
import com.eei.urlshortener.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JWTService jwtService;
  private final AuthenticationManager authenticationManager;

  @Transactional
  public AuthenticationResponse signUp(UserRequest request) {
    log.info("Sign up operation started.");
    userRepository
        .findByUsername(request.getUsername())
        .ifPresent(
            user -> {
              throw new IllegalArgumentException("Username is already taken.");
            });
    User user =
        User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .build();
    userRepository.save(user);
    String jwt = jwtService.generateToken(user);
    log.info("Sign up operation finished. User save successfully");
    return AuthenticationResponse.builder().token(jwt).build();
  }

  public AuthenticationResponse signIn(UserRequest request) {
    log.info("Sign in operation started.");
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    User user =
        userRepository
            .findByUsername(request.getUsername())
            .orElseThrow(() -> new IllegalArgumentException("Invalid username."));
    String jwt = jwtService.generateToken(user);
    log.info("Sign in operation finished. User retrieve successfully");
    return AuthenticationResponse.builder().token(jwt).build();
  }
}
