package com.ngo.service;

import com.ngo.entity.UserData;
import com.ngo.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User[] getAll();
    User get(Integer id);
    User create(User user);
    User update(User user);
    void delete(Integer id);
    User partialUpdate(Integer id, Map<String, Object> updates);
    User findByEmail(String email);
    User login(String email, String password);


}
