package dev.bananaftmeo.netcafeserver.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bananaftmeo.netcafeserver.exceptions.UserRegistrationException;
import dev.bananaftmeo.netcafeserver.models.requests.RegisterRequest;
import dev.bananaftmeo.netcafeserver.services.IUserService;

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

    @GetMapping("/hittest")
    public ResponseEntity<?> testReached() {
        return new ResponseEntity<>("Authentication controller reached!", HttpStatus.OK);
    }

    @GetMapping("/protected")
    public ResponseEntity<?> getProtectedEndpoint() {
        return new ResponseEntity<>("Protected endpoint reached!", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Validated @RequestBody RegisterRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest().body("Validation error: " + bindingResult.getAllErrors());
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Password does not match");
        }

        try {
            userService.registerUser(request);
        } catch (UserRegistrationException e) {
            return ResponseEntity.badRequest().body(e.getErrorMessage());
        }

        return ResponseEntity.ok("User registered successfully");
    }

}
