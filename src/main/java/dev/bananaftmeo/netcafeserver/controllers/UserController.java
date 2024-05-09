package dev.bananaftmeo.netcafeserver.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bananaftmeo.netcafeserver.models.responses.ErrorResponse;
import dev.bananaftmeo.netcafeserver.models.responses.UserInfoResponse;
import dev.bananaftmeo.netcafeserver.services.userservices.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserInfoById(@PathVariable Long id) {
        try {
            UserInfoResponse userInfo = userService.getUserInfoById(id);
            return ResponseEntity.ok(userInfo);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUserInfo() {
        List<UserInfoResponse> allUserInfo = userService.getAllUserInfo();
        return ResponseEntity.ok(allUserInfo);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserInfo(@PathVariable Long userId, @RequestBody UserInfoResponse userInfoResponse) {

        try {
            UserInfoResponse updatedUserInfo = userService.updateUserInfo(userId, userInfoResponse);
            return ResponseEntity.ok(updatedUserInfo);

        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
        }
    }
}
