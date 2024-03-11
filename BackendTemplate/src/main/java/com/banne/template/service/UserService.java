package com.banne.template.service;

import com.banne.template.model.dto.PageQueryRequest;
import com.banne.template.model.dto.UserMessageRequest;
import com.banne.template.model.entity.User;
import com.banne.template.model.vo.LoginUserVO;
import com.banne.template.model.vo.UserVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService extends IService<User>{
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    Long userRegister(String userAccount, String userPassword, String checkPassword);

    Long userAdd(UserMessageRequest userMessageRequest);

    Long userRemove(long id);

    Long userUpdate(UserMessageRequest userMessageRequest);


    UserVO userQueryById(long id, HttpServletRequest request);

    PageInfo<UserVO> userQuery(PageQueryRequest pageQueryRequest);
}
