package com.example.loginregister.service;

import com.example.loginregister.dto.request.RequestUpdate;
import com.example.loginregister.dto.response.ResponseData;

public interface UserService {
    ResponseData updateUser(RequestUpdate user);

    ResponseData findById(Integer id, String token);

    ResponseData delete(Integer id, String token);

    ResponseData getListCustomer();

    ResponseData getListStaff();
    ResponseData getListManager();
    ResponseData getListAdmin();
}
