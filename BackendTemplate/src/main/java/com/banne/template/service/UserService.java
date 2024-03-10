package com.banne.template.service;

import com.banne.template.model.dto.UserMessageRequest;
import com.banne.template.model.entity.User;
import com.banne.template.model.vo.LoginUserVO;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<User>{
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    Long userRegister(String userAccount, String userPassword, String checkPassword);

    Long userAdd(UserMessageRequest userMessageRequest);

    Long userRemove(long id);

    Long userUpdate(UserMessageRequest userMessageRequest);
}
