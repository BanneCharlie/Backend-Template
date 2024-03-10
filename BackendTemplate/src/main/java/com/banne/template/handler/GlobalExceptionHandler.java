package com.banne.template.handler;

import com.banne.template.common.enumeration.ResultCodeEnum;
import com.banne.template.common.exception.BusinessException;
import com.banne.template.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理 RuntimeException
     * @param
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<Object> handleRunnableException() {
        return Result.build(null,ResultCodeEnum.SYSTEM_ERROR);
    }

    /**
     * 处理 自定义的 BusinessException
     * @param exception
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result<Object> handleBusinessException(BusinessException exception) {
        return Result.build(null,exception.getCode(),exception.getMessage());
    }
}
