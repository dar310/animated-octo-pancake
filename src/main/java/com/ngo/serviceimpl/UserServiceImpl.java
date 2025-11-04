package com.ngo.serviceimpl;

import com.ngo.entity.UserData;
import com.ngo.model.User;
import com.ngo.repository.UserDataRepository;
import com.ngo.service.UserService;
import com.ngo.util.Transform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDataRepository userDataRepository;

    Transform<UserData, User> transformUserData = new Transform<>(User.class);

    Transform<User, UserData> transformUser = new Transform<>(UserData.class);


    public List<User> getAllUsers() {
        List<UserData>userDataRecords = new ArrayList<>();
        List<User> users =  new ArrayList<>();

        userDataRepository.findAll().forEach(userDataRecords::add);
        Iterator<UserData> it = userDataRecords.iterator();

        while(it.hasNext()) {
            User user = new User();
            UserData userData = it.next();
            user = transformUserData.transform(userData);
            users.add(user);
        }
        return users;
    }


    @Override
    public User[] getAll() {
        List<UserData> usersData = new ArrayList<>();
        List<User> users = new ArrayList<>();
        userDataRepository.findAll().forEach(usersData::add);
        Iterator<UserData> it = usersData.iterator();
        while(it.hasNext()) {
            UserData userData = it.next();
            User user =  transformUserData.transform(userData);
            users.add(user);
        }
        User[] array = new User[users.size()];
        for  (int i=0; i<users.size(); i++){
            array[i] = users.get(i);
        }
        return array;
    }
//    @Override
//    public User get(Integer id) {
//        log.info(" Input id >> "+  Integer.toString(id) );
//        User user = null;
//        Optional<UserData> optional = userDataRepository.findById(id);
//        if(optional.isPresent()) {
//            log.info(" Is present >> ");
//            user = new User();
//            user.setId(optional.get().getId());
//            user.setFName(optional.get().getFName());
//            user.setLName(optional.get().getLName());
//        }
//        else {
//            log.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
//        }
//        return user;
//    }
@Override
public User get(Integer id) {
    log.info("Input id >> {}", id);
    Optional<UserData> optional = userDataRepository.findById(id);

    if (optional.isPresent()) {
        UserData userData = optional.get();
        // Transform to model with all matching fields
        return transformUserData.transform(userData);
    } else {
        log.warn("User with id {} not found", id);
        return null;
    }
}
    @Override
    public User create(User user) {
        log.info(" add:Input " + user.toString());
        UserData userData = transformUser.transform(user);
        UserData updatedUserData = userDataRepository.save(userData);
        log.info(" add:Input {}", userData.toString());
        return  transformUserData.transform(updatedUserData);
    }

    @Override
    public User update(User user) {
        Optional<UserData> optional = userDataRepository.findById(user.getId());
        if (optional.isPresent()) {
            UserData existing = optional.get();

            UserData updated = transformUser.transform(user);

            updated.setId(existing.getId());
            updated.setCreated_at(existing.getCreated_at());

            UserData saved = userDataRepository.save(updated);
            return transformUserData.transform(saved);
        } else {
            log.error("User record with id: {} does not exist", user.getId());
            return null;
        }
    }
    @Override
    public void delete(Integer id) {
        log.info(" Input >> {}",id);
        Optional<UserData> optional = userDataRepository.findById(id);
        if( optional.isPresent()) {
            UserData userDatum = optional.get();
            userDataRepository.delete(optional.get());
            log.info(" Successfully deleted User record with id: {}",id);
        }
        else {
            log.error(" Unable to locate user with id: {}", id);
        }
    }
    @Override
    public User partialUpdate(Integer id, Map<String, Object> updates) {
        Optional<UserData> optional = userDataRepository.findById(id);

        if (optional.isEmpty()) {
            log.warn("User with id {} not found for PATCH", id);
            return null;
        }

        UserData existing = optional.get();

        updates.forEach((field, value) -> {
            try {
                Field declaredField = UserData.class.getDeclaredField(field);
                declaredField.setAccessible(true);

                // Special case: handle enums
                if (declaredField.getType().isEnum()) {
                    Object enumValue = Enum.valueOf((Class<Enum>) declaredField.getType(), value.toString());
                    declaredField.set(existing, enumValue);
                }
                // Handle date strings
                else if (declaredField.getType().equals(Date.class) && value instanceof String) {
                    try {
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                        declaredField.set(existing, sdf.parse(value.toString()));
                    } catch (Exception e) {
                        log.warn("Invalid date format for field {}: {}", field, value);
                    }
                }
                // Default case
                else {
                    declaredField.set(existing, value);
                }

            } catch (NoSuchFieldException e) {
                log.warn("Unknown field '{}' ignored for user {}", field, id);
            } catch (IllegalAccessException e) {
                log.error("Failed to set field '{}' for user {}: {}", field, id, e.getMessage());
            }
        });

        UserData saved = userDataRepository.save(existing);
        return transformUserData.transform(saved);
    }
    @Override
    public User findByEmail(String email) {
        log.info("Finding user by email: {}", email);
        Optional<UserData> optional = userDataRepository.findByEmail(email);

        if (optional.isPresent()) {
            User user = transformUserData.transform(optional.get());
            return user; // includes password field
        } else {
            log.warn("User not found for email: {}", email);
            return null;
        }
    }

    @Override
    public User login(String email, String password) {
        User user = findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            log.info("Login successful for {}", email);
            return user;
        } else {
            log.warn("Login failed for {}", email);
            return null;
        }
    }





}
