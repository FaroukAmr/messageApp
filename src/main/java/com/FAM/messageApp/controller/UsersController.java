package com.FAM.messageApp.controller;

import com.FAM.messageApp.storage.UserStorage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class UsersController {
    @GetMapping("/registration/{userName}")
    public ResponseEntity<Void> register(@PathVariable String userName, HttpServletResponse response) {
        System.out.println("handling register user request: " + userName);

        try {
            UserStorage.getInstance().setUser(userName);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/fetchAllUsers")
    public Set<String> fetchAll() {
        return UserStorage.getInstance().getUsers();
    }
}