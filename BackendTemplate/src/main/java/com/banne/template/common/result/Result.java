package com.banne.template.common.result;

import com.banne.template.common.enumeration.ResultCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 后端统一返回结果
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {

    private Integer code;
    private String msg; //错误信息
    private T data; //数据


    // 返回数据
    public static <T> Result<T> build(T data,Integer code,String msg) {
        Result<T> result = new Result<T>();
        result.setData(data);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    // 通过枚举构造Result对象
    public static <T> Result build(T data , ResultCodeEnum resultCodeEnum) {
        return build(data, resultCodeEnum.getCode() , resultCodeEnum.getMessage()) ;
    }

}
