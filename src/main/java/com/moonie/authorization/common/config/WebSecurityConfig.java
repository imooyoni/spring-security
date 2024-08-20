package com.moonie.authorization.common.config;

import com.moonie.authorization.jwt.JwtAccessDeniedHandler;
import com.moonie.authorization.jwt.JwtAuthenticationEntryPoint;
import com.moonie.authorization.jwt.JwtSecurityConfig;
import com.moonie.authorization.jwt.JwtTokenUtil;
import com.moonie.authorization.user.service.CustomUserDetailService;
import com.moonie.authorization.util.Sha256PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomUserDetailService customUserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Sha256PasswordEncoder();
//        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {
       // H2 DB 사용시 mvcRequestMatcher option으로 예외 url 설정해 주어야 사용가능
        MvcRequestMatcher h2RequestMatcher = new MvcRequestMatcher(introspector, "/**");
        h2RequestMatcher.setServletPath("/h2-console");
        httpSecurity
                    .csrf((csrf) -> csrf.disable()) // h2 console 접근 시 필요 조건
                    .exceptionHandling(exceptionHandling -> exceptionHandling
                            .accessDeniedHandler(jwtAccessDeniedHandler)
                            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    )
                    .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                                                .requestMatchers(h2RequestMatcher).permitAll()
                                                .requestMatchers( "/","/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                                .requestMatchers( "/api/authenticate").permitAll()
                                                .requestMatchers("/user/**").permitAll()
                                                .requestMatchers("/admin").hasRole("admin")
//                                                .requestMatchers("/user").hasRole("user")
                                                .anyRequest().permitAll())
                    .cors(c -> {
                        CorsConfigurationSource source = request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(List.of(
                                                            "http://localhost:3000"
                                                          , "test.com"
                                                            )
                                                    );
                            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                            return config;
                        };
                        c.configurationSource(source);
                    })
                    .headers((headers) -> headers.frameOptions((frame) -> frame.sameOrigin()))
                    .formLogin((formLogin) -> formLogin
                            .loginPage("/login") // 커스텀 로그인 페이지 경로
                            .usernameParameter(("username"))
                            .passwordParameter(("password"))
                            .defaultSuccessUrl("/",true)
                    )
                    .with(new JwtSecurityConfig(jwtTokenUtil), customizer -> {}); //jwt
//                    .formLogin(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        return new CustomUserDetailService(userBasicRepository);
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("admin").password("1234").roles("admin").build());
//        return manager;
//    }

    //ref1. https://www.elancer.co.kr/blog/view?seq=235
    //ref2. https://this-circle-jeong.tistory.com/162
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
}
