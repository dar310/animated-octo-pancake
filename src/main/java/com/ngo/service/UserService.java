package com.ngo.service;

import com.ngo.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getAllUsers();
    User[] getAll();
    User get(Integer id);
    User create(User user);
    User update(User user);
    void delete(Integer id);
    User partialUpdate(Integer id, Map<String, Object> updates);

}
