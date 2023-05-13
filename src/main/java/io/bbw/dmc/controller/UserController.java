package io.bbw.dmc.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.bbw.dmc.pojo.User;
import io.bbw.dmc.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/v1/register")
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody User user) {
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/v1/categories")
    public ResponseEntity<String[]> getCategories(Principal principal) {
        return ResponseEntity.ok(userService.getCategories(principal.getName()));
    }

    @PutMapping("/v1/categories")
    public ResponseEntity<HttpStatus> updateCategories(@RequestBody String[] categories, Principal principal) {
        userService.updateCategories(categories, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
