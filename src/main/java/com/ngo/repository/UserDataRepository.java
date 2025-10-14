package com.ngo.repository;

import com.ngo.entity.UserData;
import org.springframework.data.repository.CrudRepository;

public interface UserDataRepository extends CrudRepository<UserData, Integer> {
}
