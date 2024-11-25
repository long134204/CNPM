package com.example.loginregister.service;

import com.example.loginregister.dto.request.RefreshRequest;
import com.example.loginregister.dto.request.RequestUpdate;
import com.example.loginregister.dto.request.RequestUpdatePassword;
import com.example.loginregister.dto.response.ResponseData;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface UserService {
    ResponseData updateUser(RequestUpdate user);

    ResponseData findById(Integer id, String token);

    ResponseData delete(Integer id, String token);

    ResponseData getListCustomer();

    ResponseData getListStaff();
    ResponseData getListManager();
    ResponseData getListAdmin();

    void refresh(RefreshRequest request) throws MessagingException, IOException;
    ResponseData updatePassword( RequestUpdatePassword requestUpdatePassword);

    ResponseData checkRefreshPasswordToken(String token, String tokenCheck);
}
