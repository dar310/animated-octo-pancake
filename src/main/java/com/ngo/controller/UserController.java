package com.ngo.controller;

import com.ngo.model.User;
import com.ngo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
//
//    @RequestMapping("/api/user")
//    public ResponseEntity<?> getUserCategories()
//    {
//        HttpHeaders headers = new HttpHeaders();
//        ResponseEntity<?> response;
//        try {
//            List<UserCategory> mappedUsers = userService.listUserCategories();
//            //Map<String,List<User>> mappedUsers = userService.getCategoryMappedUsers();
//            log.warn("User Categories Count:::::::" + mappedUsers.size());
//            response = ResponseEntity.ok(mappedUsers);
//        }
//        catch( Exception ex)
//        {
//            log.error("Failed to retrieve user with id : {}", ex.getMessage(), ex);
//            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
//        }
//        return response;
//    }
    @GetMapping("/api/users")
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


    @PutMapping("/api/user")
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
    @PostMapping("/api/user")
    public ResponseEntity<?> update(@RequestBody User user){
        log.info("Update Input >> user.toString() ");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            User newUser = userService.update(user);
            response = ResponseEntity.ok(user);
        }
        catch( Exception ex)
        {
            log.error("Failed to retrieve user with id : {}", ex.getMessage(), ex);
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
}
