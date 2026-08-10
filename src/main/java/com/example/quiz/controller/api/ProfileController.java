package com.example.quiz.controller.api;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quiz.config.ApiController;
import com.example.quiz.service.UserService;

@ApiController
@RequestMapping("/api/profile")
public class ProfileController {
    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Map<String, Object> profile(Principal principal) {
        Map<String, Object> response = new HashMap<>();
        response.put("username", principal.getName());
        response.put("admin", userService.isAdmin(principal));
        return response;
    }
}
