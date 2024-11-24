package com.example.loginregister.repo;

import com.example.loginregister.dto.response.ResponseData;
import com.example.loginregister.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    void deleteUserById(Integer id);

    @Query("SELECT u from User u where u.role =3")
    List<User> getListCustomer();

    @Query("SELECT u from User u where u.role =2")
    List<User> getListStaff();
    @Query("SELECT u from User u where u.role =1")
    List<User> getListManger();

    @Query("SELECT u from User u where u.role =0")
    List<User> getListAdmin();
}
