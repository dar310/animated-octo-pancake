package com.ngo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ngo.enums.UserRole;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name ="user_table")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String f_name;
    String l_name;
    @Column(unique = true, nullable = false)
    String email;
    String contact_no;
    @Column(columnDefinition = "TEXT")
    String address;
    String password;
    @Enumerated(EnumType.STRING)
    UserRole role;
    Date birthdate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date lastUpdated;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date created_at;
}
