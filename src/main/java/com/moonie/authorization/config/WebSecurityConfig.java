package com.moonie.authorization.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    // spring security 5.7 이상 WebSecurityConfigurerAdapter 지원 중단
    // 1. security 인증 설정(유저 확인)
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeHttpRequests((authorizeRequests) ->
//                authorizeRequests.anyRequest().authenticated())
//                .formLogin((formLogin) ->
//                        formLogin.usernameParameter(("username"))
//                                 .passwordParameter(("password"))
//                                .defaultSuccessUrl("/",true)
//            );
//        return httpSecurity.build();
//    }

    //2. security  인가 설정(권한)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/admin").hasRole("admin")
                                .requestMatchers("/user").hasRole("user")
                                .anyRequest().authenticated())
                .formLogin((formLogin) ->
                        formLogin.usernameParameter(("username"))
                                .passwordParameter(("password"))
                                .defaultSuccessUrl("/",true)
                );
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password("1234").roles("admin").build());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    //ref1. https://www.elancer.co.kr/blog/view?seq=235
    //ref2. https://this-circle-jeong.tistory.com/162

}
