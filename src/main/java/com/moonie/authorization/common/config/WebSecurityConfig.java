package com.moonie.authorization.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
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
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

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
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeHttpRequests((authorizeRequests) ->
//                        authorizeRequests
//                                //권한 체크 X
//                                .requestMatchers("/swagger-ui/**", "/h2-console/**").permitAll()
//                                .requestMatchers(PathRequest.toH2Console()).permitAll()
//                                //권한 체크 O
////                                .requestMatchers("/admin").hasRole("admin")
////                                .requestMatchers("/user").hasRole("user")
//                                .anyRequest().authenticated())
//                    .formLogin((formLogin) ->
//                            formLogin.usernameParameter(("username"))
//                                    .passwordParameter(("password"))
//                                    .defaultSuccessUrl("/",true)
//                    );
//        return httpSecurity.build();
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {
       // H2 DB 사용시 mvcRequestMatcher option으로 예외 url 설정해 주어야 사용가능
        
        MvcRequestMatcher h2RequestMatcher = new MvcRequestMatcher(introspector, "/**");
        h2RequestMatcher.setServletPath("/h2-console");
        httpSecurity.authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                                                .requestMatchers(h2RequestMatcher).permitAll()
                                                .requestMatchers("/admin").hasRole("admin")
                                                .requestMatchers("/user").hasRole("user")
                                                .anyRequest().authenticated())
                    .csrf((csrf) -> csrf.disable()) // h2 console 접근 시 필요 조건
                    .headers((headers) -> headers.frameOptions((frame) -> frame.sameOrigin()))
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
