package com.example.loginregister.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RequestUpdatePassword {
    private String email;
    private String password;
}
