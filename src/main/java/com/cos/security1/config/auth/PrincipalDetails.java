package com.cos.security1.config.auth;

// Security가 /login 주소 요청이 오면 낚아채서 로그인 진행시킴
// 로그인 진행 완료되면 시큐리티가 가지고 있는 세션을 만들어줌
// 세션 공간은 같으나 시큐리티는 자신만의 시큐리티 공간을 가짐
// Key값으로 구분 (Security ContextHolder) 안에 세션 정보 저장
// 세션에 들어갈 수 잇는 정보 : 시큐리티가 가지고 있는 세션에 들어갈 수 있는 오브젝트 정해져 있다
// -> 오브젝트 타입 : Authentication 타입 객체
// Authentication 안에는 User 정보가 있어야 됨
// User 오브젝트 타입 => UserDetails 타입 객체
//
// 시큐리티 세션 영역 존재 <- 세션 정보 저장
// 안에 들어갈 수 있는 객체가 Authentication 객체
// Authentication 안에 UserDetails
// 해당 객체를 꺼내면 유저 오브젝트에 접근 가능
// Security Session => PrincipalDetails => UserDetails(PrincipalDetails)

import com.cos.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// UserDetails 인터페이스를 구현하는 커스텀 클래스
// SpringSecurity가 사용자 인증 과정에서 사용자의 정보를 다루는 방식 정의
// 사용자 인증 시 사용자의 정보를 어떻게 저장하고 관리할지 커스터마이징
public class PrincipalDetails implements UserDetails {

    // 사용자 정보를 담는 객체
    private User user;// 컴포지션

    // 생성자
    public PrincipalDetails(User user){
        this.user = user;
    }

    // 해당 User의 권한을 리턴하는 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        // 리턴 타입이 String이기 때문에 user.getRole 리턴 불가능
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    // password 리턴
    @Override
    public String getPassword(){
        return user.getPassword();
    }

    @Override
    public String getUsername(){
        return user.getUsername();
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    // 계정 잠김 여부
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    // 계정 비밀번호 1년 지났는가
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    // 계정 활성화 여부
    @Override
    public boolean isEnabled(){

        // 예를 들어
        // 사이트에서 회원이 1년동안 로그인을 안하면 휴먼 계정으로 돌리기로 함
        //user.getLoginDate()를 가져와서 현재 시간-로그인 시간 => 1년을 초과하면 return false로 하기

        return true;
    }
}
