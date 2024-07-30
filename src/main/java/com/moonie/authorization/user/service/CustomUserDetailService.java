package com.moonie.authorization.user.service;

import com.moonie.authorization.user.entity.UserBasicEntity;
import com.moonie.authorization.user.domain.UserBasicRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailService")
public class CustomUserDetailService implements UserDetailsService {
    private final UserBasicRepository userBasicRepository;

    public CustomUserDetailService(UserBasicRepository userBasicRepository) {
        this.userBasicRepository = userBasicRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userBasicRepository.findOneWithRolesByUserName(username)
                .map(user -> createUser(username, user))
                .orElseThrow(()-> new UsernameNotFoundException(username + "-> 데이터 베이스에서 찾을 수 없습니다."));
    }

    private User createUser(String username, UserBasicEntity user){
//        if(!user.isActivated()){
//            throw new RuntimeException(username + "-> 활성화되어 있지 않습니다.");
//        }
        List<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getRoleName()))
                .collect(Collectors.toList());
        return new User(user.getUserName(), user.getUserPassword(), grantedAuthorities);
    }
}