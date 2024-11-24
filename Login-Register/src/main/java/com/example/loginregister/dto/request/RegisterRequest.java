package com.example.loginregister.dto.request;

import com.example.loginregister.entity.Role;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterRequest {
    private String name;
    private Date birthday;
    private String address;
    private Boolean gender;
    private String email;
    private String password;
    private Role role;
}
