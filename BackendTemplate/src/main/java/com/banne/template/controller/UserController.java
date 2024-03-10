package com.banne.template.controller;

import com.banne.template.common.enumeration.ResultCodeEnum;
import com.banne.template.common.exception.BusinessException;
import com.banne.template.common.result.Result;
import com.banne.template.model.dto.UserLoginRequest;
import com.banne.template.model.vo.LoginUserVO;
import com.banne.template.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
        if (userLoginRequest == null) {
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

/*    @GetMapping("/test")
    public void test(){
        System.out.println("测试ing");
    }*/
}
