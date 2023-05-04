package com.FAM.messageApp.chat.controller;

import com.FAM.messageApp.chat.storage.UserStorage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsersController {

    @GetMapping("/registration/{userName}")
    public ResponseEntity<Void> register(@PathVariable String userName, HttpServletResponse response) {
        System.out.println("handling register user request: " + userName);

        // Create a new cookie
//        Cookie myCookie = new Cookie("userName", userName);
//        myCookie.setPath("/");
//        myCookie.setMaxAge(3600);

        try {
            UserStorage.getInstance().setUser(userName);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        // Attach the cookie to the response
//        response.addCookie(myCookie);

        // Return a response
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fetchAllUsers")
    public Set<String> fetchAll() {
        return UserStorage.getInstance().getUsers();
    }
}
