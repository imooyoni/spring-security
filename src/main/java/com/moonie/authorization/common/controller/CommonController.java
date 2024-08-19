package com.moonie.authorization.common.controller;

import com.moonie.authorization.common.reponse.CommonResponse;
import com.moonie.authorization.common.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CommonController {

    @Autowired
    private CommonService commonService;
    public CommonResponse success(){
        return commonService.getSuccessResult();
    }
    public CommonResponse fail(){
        return commonService.getFailResult();
    }
    public CommonResponse fail(int code, String msg){
        return commonService.getFailResult(code, msg);
    }
    public CommonResponse result(Object data){
        return commonService.getSingleResult(data);
    }
}
