package com.guard.repository;

import com.guard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // username으로 사용자 찾기
    User findByUsername(String username);
}
