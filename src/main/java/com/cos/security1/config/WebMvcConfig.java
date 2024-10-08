//package com.cos.security1.config;
//
//import org.springframework.boot.web.servlet.view.MustacheViewResolver;
////import org.springframework.boot.web.reactive.result.view.MustacheViewResolver;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//import org.springframework.web.servlet.view.JstlView;
//
//// 해당 작업 파일을 ioc로 등록하기 위해 configuration 붙이기
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        // 오버라이딩 해서 재설정
//        MustacheViewResolver resolver = new MustacheViewResolver();
//        //InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setCharset("UTF-8");
//        // 주는 데이터는 html 파일, UTF-8이다 알림
//        resolver.setContentType("text/html; charset=UTF-8");
//        //resolver.setPrefix("/templates/");
//        resolver.setPrefix("classpath:/templates");
//        // html로 만들어도 mustache가 인식
//        resolver.setSuffix(".html");
//        //resolver.setViewClass(JstlView.class);
//        registry.viewResolver(resolver);
//    }
//}
