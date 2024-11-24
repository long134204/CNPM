package com.example.loginregister.repo;

import com.example.loginregister.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Integer> {
    @Query("SELECT t FROM Token t INNER JOIN User u ON t.user.id = u.id WHERE u.id = :userId AND (t.expired = false OR t.revoked = false)")
    List<Token> findAllValidTokenByUser(int userId);
    Optional<Token> findByToken(String token);

    @Query("DELETE from Token t where t.token =: token")
    void deleteByToken(String token);
}
