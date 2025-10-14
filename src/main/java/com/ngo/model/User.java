package com.ngo.model;

import com.ngo.enums.UserRole;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    int id;
    String fName;
    String lName;
    String email;
    String contact_no;
    String address;
    String password;
    UserRole role;
    Date birthdate;
    Date created_at;
}
