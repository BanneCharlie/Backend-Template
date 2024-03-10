package com.banne.template.controller;

import com.banne.template.annotation.Logging;
import com.banne.template.common.enumeration.ResultCodeEnum;
import com.banne.template.common.exception.BusinessException;
import com.banne.template.common.result.Result;
import com.banne.template.model.dto.UserLoginRequest;
import com.banne.template.model.dto.UserMessageRequest;
import com.banne.template.model.dto.UserRegisterRequest;
import com.banne.template.model.entity.User;
import com.banne.template.model.vo.LoginUserVO;
import com.banne.template.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Api(tags = "用管理模块")
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public Result<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (ObjectUtils.isEmpty(userLoginRequest)) {
            throw new BusinessException(ResultCodeEnum.LOGIN_CONTENT_NULL);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ResultCodeEnum.LOGIN_CONTENT_NULL);
        }

        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);

        return Result.build(loginUserVO,ResultCodeEnum.SUCCESS);
    }

    /**
     * 用户的注册
     *
     * @param userRegisterRequest
     * @param request
     */
    @PostMapping("/register")
    @ApiOperation(value = "用户的注册")
    public Result<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest, HttpServletRequest request) {
         if(ObjectUtils.isEmpty(userRegisterRequest)){
             throw new BusinessException(ResultCodeEnum.USER_REGISTER_NULL);
         }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        Long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return Result.build(result,ResultCodeEnum.SUCCESS);
    }


    @Logging
    @GetMapping("/add")
    @ApiOperation(value = "用户的添加")
    public Result<Long> userAdd(@RequestBody UserMessageRequest userMessageRequest, HttpServletRequest request) {
        if (ObjectUtils.isEmpty(userMessageRequest)){
            throw new BusinessException(ResultCodeEnum.ADD_NULL);
        }

        Long result = userService.userAdd(userMessageRequest);

        return Result.build(result,ResultCodeEnum.SUCCESS);
    }

    @Logging
    @PostMapping("/remove")
    @ApiOperation(value = "用户的删除")
    public Result<Long> userRemove(@RequestParam("id")long id, HttpServletRequest request) {
        Long result = userService.userRemove(id);
        return Result.build(result,ResultCodeEnum.SUCCESS);
    }

    @Logging
    @PostMapping("/modify")
    @ApiOperation(value = "用户的修改")
    public Result<Long> userUpdate(@RequestBody UserMessageRequest userMessageRequest, HttpServletRequest request) {
        if (ObjectUtils.isEmpty(userMessageRequest)){
            throw new BusinessException(ResultCodeEnum.MODIFY_NULL);
        }
        Long result = userService.userUpdate(userMessageRequest);

        return Result.build(result,ResultCodeEnum.SUCCESS);
    }
}
