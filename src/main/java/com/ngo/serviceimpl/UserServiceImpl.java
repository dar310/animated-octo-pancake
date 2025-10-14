package com.ngo.serviceimpl;

import com.ngo.entity.UserData;
import com.ngo.model.User;
import com.ngo.repository.UserDataRepository;
import com.ngo.service.UserService;
import com.ngo.util.Transform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Optional<UserData> optional  = userDataRepository.findById(user.getId());
        if(optional.isPresent()){
            UserData userData = transformUser.transform(user);
            UserData updaatedUserData = userDataRepository.save( userData);
            return transformUserData.transform(updaatedUserData);
        }
        else {
            log.error("User record with id: {} do not exist",user.getId());
        }
        return null;
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
}
