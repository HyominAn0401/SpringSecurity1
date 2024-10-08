package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// spring security 필터가 스프링 필터체인(SecurityConfig)에 등록됨
@EnableWebSecurity
// preAuthorize라는 어노테이션 활성화
// preAuthorize, PrePostAuthorize 둘 다 활성화
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
// EnabledGlobalMethodSecurity deprecated 기본값이 true
// secured 활성화
//@EnableMethodSecurity
public class SecurityConfig {

    // 패스워드 암호화 빈으로 등록
    // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해줌
    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                //.csrf().disable()
                // CSRF 보호 비활성화
                .csrf((csrfConfig)->csrfConfig.disable())
                // 요청에 대한 보안 설정
                .authorizeHttpRequests(authz -> authz
                        // user 인증 필요
                        // 인증만 되면 들어갈 수 있음
                        .requestMatchers("/user/**").authenticated()
                        // manager는 ADMIN or MANAGER 역할 필요
                        .requestMatchers("/manager/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
                        // admin은 ADMIN 역할 필요
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        // 그 외 다른 요청은 모두 허용
                        .anyRequest().permitAll())
                // 폼 로그인 설정
                .formLogin(formLogin -> formLogin
                        // 사용자 정의 로그인 페이지 설정
                        .loginPage("/loginForm")
                        // /login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인을 진행해줌
                        // 컨트롤러에 /login을 만들지 않아도 됨
                        .loginProcessingUrl("/login")
                        // 메인 페이지로
                        .defaultSuccessUrl("/"));

        // 객체 생성 및 반환
        return http.build();
    }
}
//public class SecurityConfig extends WebSecurityConfigurerAdapter{
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        http.csrf().disable();
//        http.authorizeRequests()
//                .antMatchers("/user/**").authenticate()
//                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
//                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
//                .anyRequest().permitAll();
//    }
//}
