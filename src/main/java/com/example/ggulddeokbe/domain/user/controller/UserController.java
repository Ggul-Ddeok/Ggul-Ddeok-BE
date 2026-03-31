package com.example.ggulddeokbe.domain.user.controller;

import com.example.ggulddeokbe.domain.user.dto.UserInfoResponse;
import com.example.ggulddeokbe.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserQueryService userQueryService;

    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> getMe() {
        return ResponseEntity.ok(userQueryService.getMe());
    }
}
