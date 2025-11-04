package com.ngo.controller;

import com.ngo.entity.UserData;
import com.ngo.model.User;
import com.ngo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/api/user")
    public ResponseEntity<?> getAllUsers() {
        log.info("Fetching all users...");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            List<User> users = userService.getAllUsers();
            log.info("Total users found: {}", users.size());
            response = ResponseEntity.ok(users);
        } catch (Exception ex) {
            log.error("Failed to retrieve all users: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }


    @PostMapping("/api/user")
    public ResponseEntity<?> add(@RequestBody User user){
        log.info("Input >> " + user.toString() );
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            User newUser = userService.create(user);
            log.info("created user >> " + newUser.toString() );
            response = ResponseEntity.ok(newUser);
        }
        catch( Exception ex)
        {
            log.error("Failed to retrieve user with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
    @PatchMapping("/api/user/{id}")
    public ResponseEntity<?> partialUpdateUser(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        log.info("PATCH request for user id {} with updates: {}", id, updates);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            User updatedUser = userService.partialUpdate(id, updates);
            if (updatedUser != null) {
                response = ResponseEntity.ok(updatedUser);
            } else {
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with id: " + id);
            }
        } catch (Exception ex) {
            log.error("Failed to partially update user {}: {}", id, ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
    @PutMapping("/api/user/{id}")
    public ResponseEntity<?> update(@PathVariable final Integer id, @RequestBody User user){
        log.info("Update Input >> user.toString() ");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            // Force the correct ID (from URL, not payload)
            user.setId(id);

            User updatedUser = userService.update(user);
            response = ResponseEntity.ok(updatedUser);
        } catch (Exception ex) {
            log.error("Failed to update user with id {}: {}", id, ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @GetMapping("api/user/{id}")
    public ResponseEntity<?> get(@PathVariable final Integer id){
        log.info("Input user id >> " + Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            User user = userService.get(id);
            response = ResponseEntity.ok(user);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer id){
        log.info("Input >> " + Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            userService.delete(id);
            response = ResponseEntity.ok(null);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
    @PostMapping("/api/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        User user = userService.login(email, password);
        if (user != null) {
            user.setPassword(null); // don’t expose password in response
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
