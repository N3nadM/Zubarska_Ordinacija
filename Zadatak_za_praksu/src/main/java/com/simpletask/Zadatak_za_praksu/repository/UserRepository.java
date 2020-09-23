package com.simpletask.Zadatak_za_praksu.repository;

import com.simpletask.Zadatak_za_praksu.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findByUsername(String username);
}
