package com.moonie.authorization.common.service;

import com.moonie.authorization.common.reponse.CommonResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListResult<T> extends CommonResponse {
    private List<T> list;
}
