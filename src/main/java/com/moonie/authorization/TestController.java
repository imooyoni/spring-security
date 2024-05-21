package com.moonie.authorization;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "TEST API", description = "SECURITY 인가 TEST API")
@RestController
public class TestController {
    @RequestMapping("/admin")
    public ResponseEntity admin(){
        return new ResponseEntity<>("admin 입니다.", HttpStatus.OK);
    }

    @RequestMapping("/user")
    public ResponseEntity user(){
        return new ResponseEntity<>("user 입니다.", HttpStatus.OK);
    }
}
