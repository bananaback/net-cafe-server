package dev.bananaftmeo.netcafeserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bananaftmeo.netcafeserver.models.responses.UserSessionResponse;
import dev.bananaftmeo.netcafeserver.models.requests.ComputerIdsRequest;
import dev.bananaftmeo.netcafeserver.services.usersessionservices.UserSessionService;

@RestController
@RequestMapping("api/sessions")
public class UserSessionController {

    @Autowired
    private UserSessionService userSessionService;

    @PostMapping("/latest")
    public ResponseEntity<List<UserSessionResponse>> getLatestSessionsByComputerIds(
            @RequestBody ComputerIdsRequest request) {
        List<UserSessionResponse> latestSessions = userSessionService
                .getLatestSessionsByComputerIds(request.getComputerIds());
        return new ResponseEntity<>(latestSessions, HttpStatus.OK);
    }
}
