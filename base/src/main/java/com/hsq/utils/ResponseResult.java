package com.hsq.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
/**
 * 返回给前台的对象
 */
public class ResponseResult {
    private Integer code = 20000;//返回给前端的状态码，20000表示成功
    private Map<String,Object> data = new HashMap<>();
    private String message = "默认的错误信息";
}
