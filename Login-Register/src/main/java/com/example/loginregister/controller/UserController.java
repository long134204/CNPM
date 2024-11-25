package com.example.loginregister.controller;

import com.example.loginregister.dto.request.RefreshRequest;
import com.example.loginregister.dto.request.RequestUpdate;
import com.example.loginregister.dto.response.ResponseData;
import com.example.loginregister.service.UserService;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {
    UserService userService;

    @PostMapping("/update")
    ResponseData<?> update(@RequestBody RequestUpdate requestUpdate) {
        return userService.updateUser(requestUpdate);
    }

    @GetMapping("/get-infor/{id}")
    ResponseData<?> findById(@RequestHeader("Authorization")  String token, @PathVariable Integer id) {
        return userService.findById(id,token);
    }

    @PostMapping("/delete/{id}")
    ResponseData<?> delete(@RequestHeader("Authorization")  String token, @PathVariable Integer id){
        return userService.delete(id,token);
    }

    @GetMapping("/list-customer")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER') or hasAnyAuthority('STAFF')")
    ResponseData<?> listCustomer(){
        return userService.getListCustomer();
    }

    @GetMapping("/list-staff")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    ResponseData<?> listStaff(){
        return userService.getListStaff();
    }

    @GetMapping("/list-manager")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseData<?> listManager(){
        return userService.getListManager();
    }

    @GetMapping("/list-admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseData<?> listAdmin(){
        return userService.getListAdmin();
    }

    @GetMapping("/refresh-password")
    void refresh(@RequestBody RefreshRequest request) throws MessagingException, IOException {
         userService.refresh(request);
    }
}
