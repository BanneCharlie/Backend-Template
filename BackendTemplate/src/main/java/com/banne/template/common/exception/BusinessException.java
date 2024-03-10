package com.banne.template.common.exception;

import com.banne.template.common.enumeration.ResultCodeEnum;

public class BusinessException extends RuntimeException {

  private int code;
  private String message;

    public int getCode() {
        return code;
    }

    public BusinessException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
