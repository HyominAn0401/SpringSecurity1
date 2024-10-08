package com.cos.security1.repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD 함수를 JpaRepository가 들고 있음
// @Repository 없어도 IoC가 됨 -> JpaRepository를 상속했기 때문에 가능
// 자동 빈으로 등록
public interface UserRepository extends JpaRepository<User, Integer> {
    // findBy규칙 -> Username 문법
    // select * from user where username = 1?
    // ?에 username이 들어감

    // Jpa name함수
    public User findByUsername(String username);

    // select * from user where email =?
    //public User findByEmail();
}
