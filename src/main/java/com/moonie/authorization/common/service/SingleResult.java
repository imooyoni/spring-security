package com.moonie.authorization.common.service;

import com.moonie.authorization.common.reponse.CommonResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResult<T> extends CommonResponse {
    private T data;
}
