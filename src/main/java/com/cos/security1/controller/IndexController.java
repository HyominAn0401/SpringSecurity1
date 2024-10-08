package com.cos.security1.controller;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // View를 리턴하겠다
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // locatlhost:8082/
    // localhost:8082
    @GetMapping({"", "/"})
    public String index(){
        // mustache 기본 폴더 src/main/resources/
        // view resolver설정 : templates (prefix), .mustache(suffix) -> 생략 가능
        // 이게 view가 됨
        return "index"; //src/main/resources/templates/index.mustache로 찾음
        // 그러나 Spring에서 index.html 파일을 templates 내에서 찾지 못하는 오류 발생
        // 오류 해결을 위해 mustache로 설정
    }

    @GetMapping("/user")
    public @ResponseBody String user(){
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }

    // 스프링 시큐리티가 해당 주소를 낚아챔 -> 수정 필요
    // 권한 설정에 따라 정상 작동됨
//    @GetMapping("/login")
//    public @ResponseBody String login(){
//        return "login";
//    }
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user){
        System.out.println(user);
        // 역할 부여
        // 회원가입은 잘 되나 비밀번호가 1234인 경우 패스워드가 암호화되어있지 않아 시큐리티로 로그인 불가능
        // 비밀번호 암호화 필요
        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);

        userRepository.save(user);
        return "redirect:/loginForm";
    }

//    @GetMapping("/joinProc")
//    public @ResponseBody String joinProc(){
//        return "회원가입 완료됨!";
//    }

    // 메서드 수준의 접근 제어 제공
    // 특정 권한 가진 사용자만 메서드를 실행할 수 있도록 제한
    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }

    // data 메서드 직전에 실행됨
    // 여러 권한 걸고 싶으면
    // 하나만 걸고 싶으면 Secured
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터정보";
    }
}
