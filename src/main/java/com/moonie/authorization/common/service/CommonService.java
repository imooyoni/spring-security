package com.moonie.authorization.common.service;

import com.moonie.authorization.common.reponse.CommonResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonService {
    public enum CommonResponseEnum {
        SUCCESS(0, "성공하였습니다."),
        FAIL(-1, "실패하였습니다.");
        int code;
        String msg;
        CommonResponseEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
        public int getCode() {
            return code;
        }
        public String getMsg() {
            return msg;
        }
    }
    // 단일건 결과를 처리하는 메소드
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }
    // 다중건 결과를 처리하는 메소드
    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result);
        return result;
    }
    // 성공 결과만 처리하는 메소드
    public CommonResponse getSuccessResult() {
        CommonResponse result = new CommonResponse();
        setSuccessResult(result);
        return result;
    }
    // 실패 결과만 처리하는 메소드
    public CommonResponse getFailResult() {
        CommonResponse result = new CommonResponse();
        result.setSuccess(false);
        result.setCode(CommonResponseEnum.FAIL.getCode());
        result.setMsg(CommonResponseEnum.FAIL.getMsg());
        return result;
    }
    public CommonResponse getFailResult(int code, String msg) {
        CommonResponse result = new CommonResponse();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    private void setSuccessResult(CommonResponse result) {
        result.setSuccess(true);
        result.setCode(CommonResponseEnum.SUCCESS.getCode());
        result.setMsg(CommonResponseEnum.SUCCESS.getMsg());
    }

}
