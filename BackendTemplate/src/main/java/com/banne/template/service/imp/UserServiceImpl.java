package com.banne.template.service.imp;


import com.banne.template.common.enumeration.ResultCodeEnum;
import com.banne.template.common.exception.BusinessException;
import com.banne.template.mapper.UserMapper;
import com.banne.template.model.entity.User;
import com.banne.template.model.vo.LoginUserVO;
import com.banne.template.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;

/**
* @author 86188
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-03-08 16:29:57
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    /**
     * 盐值，混淆密码
     */
    public static final String SALT = "banne";


    /**
     * 用户登录
     *
     * @param userAccount userPassword
     * @param request
     * @return LoginUserVO
     */
    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验  前端可以添加对于数据输入的规则
        if (userAccount.length() < 4) {
            throw new BusinessException(ResultCodeEnum.LOGIN_ERROR);
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ResultCodeEnum.LOGIN_ERROR);
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = this.baseMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ResultCodeEnum.LOGIN_ERROR);
        }
        // 3. 记录用户的登录态  todo
        //request.getSession().setAttribute(USER_LOGIN_STATE, user);

        return this.getLoginUserVO(user);
    }

    /**
     * 获取登录用户信息 封装返回给前台
     *
     * @param user
     * @return
     */
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }
}




