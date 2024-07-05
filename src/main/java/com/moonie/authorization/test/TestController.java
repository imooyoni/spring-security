package com.moonie.authorization.test;

import com.moonie.authorization.test.entity.UserEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Tag(name = "# TEST API", description = "SECURITY 인가 TEST API_2")
@RestController
@NoArgsConstructor( access = AccessLevel.PROTECTED, force = true)
public class TestController {
    private final UserEntity userEntity;
    @RequestMapping("/admin")
    public ResponseEntity admin(){
        return new ResponseEntity<>("admin 입니다.", HttpStatus.OK);
    }

    @RequestMapping("/user")
    public ResponseEntity user(){
        return new ResponseEntity<>("user 입니다.", HttpStatus.OK);
    }

    @GetMapping("/test")
    public String getResponse(Locale locale) {
        // locale에 따라 다른 응답을 생성
        if (locale.getLanguage().equals("ko")) {
            return "안녕하세요!";
        } else {
            return "Hello!";
        }
    }
}
