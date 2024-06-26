package com.moonie.authorization.common.controller;

import com.moonie.authorization.common.reponse.CommonResponse;
import com.moonie.authorization.common.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CommonController {
    private CommonService commonService;
    private CommonResponse success(){
        return commonService.getSuccessResult();
    }
    private CommonResponse fail(){
        return commonService.getFailResult();
    }
    private CommonResponse fail(int code, String msg){
        return commonService.getFailResult(code, msg);
    }
    private CommonResponse result(Object data){
        return commonService.getSingleResult(data);
    }
}
