package com.eei.urlshortener.controller;

import com.eei.urlshortener.data.request.UserRequest;
import com.eei.urlshortener.data.response.AuthenticationResponse;
import com.eei.urlshortener.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/auth")
@RequiredArgsConstructor
public class LoginController {

    private final UserAuthService userAuthService;
    @PostMapping("/sign-up")
    public ResponseEntity<AuthenticationResponse> signUp(@RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(userAuthService.signUp(request));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(userAuthService.signIn(request));
    }
}