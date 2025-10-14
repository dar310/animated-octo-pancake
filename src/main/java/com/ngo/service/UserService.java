package com.ngo.service;

import com.ngo.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User[] getAll();
    User get(Integer id);
    User create(User user);
    User update(User user);
    void delete(Integer id);

}
