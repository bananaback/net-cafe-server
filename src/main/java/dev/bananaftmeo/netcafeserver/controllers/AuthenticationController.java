package dev.bananaftmeo.netcafeserver.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bananaftmeo.netcafeserver.exceptions.UserAuthenticationException;
import dev.bananaftmeo.netcafeserver.exceptions.UserRegistrationException;
import dev.bananaftmeo.netcafeserver.models.requests.LoginRequest;
import dev.bananaftmeo.netcafeserver.models.requests.RefreshRequest;
import dev.bananaftmeo.netcafeserver.models.requests.RegisterRequest;
import dev.bananaftmeo.netcafeserver.models.responses.AuthenticatedUserResponse;
import dev.bananaftmeo.netcafeserver.models.responses.ErrorResponse;
import dev.bananaftmeo.netcafeserver.services.IAuthenticationService;
import dev.bananaftmeo.netcafeserver.services.IRefreshTokenService;
import dev.bananaftmeo.netcafeserver.services.IUserService;
import dev.bananaftmeo.netcafeserver.utils.tokenvalidators.RefreshTokenValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IAuthenticationService authenticationService;
    @Autowired
    private RefreshTokenValidator refreshTokenValidator;
    @Autowired
    private IRefreshTokenService refreshTokenService;

    @GetMapping("/hittest")
    public ResponseEntity<?> testReached() {
        return new ResponseEntity<>("Authentication controller reached!", HttpStatus.OK);
    }

    @GetMapping("/protected")
    public ResponseEntity<?> getProtectedEndpoint() {
        return new ResponseEntity<>("Protected endpoint reached!", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody RegisterRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Validation error: " + bindingResult.getAllErrors()));
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Password does not match"));
        }

        try {
            userService.registerUser(request);
        } catch (UserRegistrationException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getErrorMessage()));
        }

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Validated @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Validation error: " + bindingResult.getAllErrors()));
        }
        try {
            AuthenticatedUserResponse authenticatedUserResponse = authenticationService.loginUser(loginRequest);
            if (authenticatedUserResponse.isSuccess()) {
                return ResponseEntity.ok().body(authenticatedUserResponse);
            } else {
                return ResponseEntity.badRequest().body(new ErrorResponse("Login failed: invalid credentials."));
            }
        } catch (UserAuthenticationException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Login failed: " + e.getErrrorMessage()));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> rotateToken(@Validated @RequestBody RefreshRequest refreshRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Validation error: " + bindingResult.getAllErrors()));
        }

        boolean isValidRefreshToken = refreshTokenValidator.validateToken(refreshRequest.getRefreshToken());
        if (!isValidRefreshToken) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid refresh token."));
        }

        try {
            AuthenticatedUserResponse authenticatedUserResponse = refreshTokenService
                    .rotateToken(refreshRequest.getRefreshToken());
            return ResponseEntity.ok().body(authenticatedUserResponse);
        } catch (UserAuthenticationException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getErrrorMessage()));
        }
    }
}
