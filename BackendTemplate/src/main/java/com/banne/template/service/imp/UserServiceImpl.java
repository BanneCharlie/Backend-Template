package com.banne.template.service.imp;


import com.banne.template.common.enumeration.ResultCodeEnum;
import com.banne.template.common.exception.BusinessException;
import com.banne.template.common.properties.JwtProperties;
import com.banne.template.common.utils.JwtUtil;
import com.banne.template.mapper.UserMapper;
import com.banne.template.model.dto.UserMessageRequest;
import com.banne.template.model.entity.User;
import com.banne.template.model.vo.LoginUserVO;
import com.banne.template.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
* @author 86188
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-03-08 16:29:57
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private JwtProperties jwtProperties;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 盐值，混淆密码
     */
    public static final String SALT = "banne";


    /**
     * 用户登录
     *
     * @param userAccount
     * @param userPassword
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
        // 进行登录校验
        User user = this.baseMapper.selectOne(queryWrapper);
        // 用户不存在 null / ""
        if (ObjectUtils.isEmpty(user)) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ResultCodeEnum.LOGIN_ERROR);
        }
        // 3. 记录用户的登录态  todo
        // request.getSession().setAttribute(USER_LOGIN_STATE, user);

        // 登录成功后,生成JWT令牌,并存放到Redis数据库中(当令牌泄漏时,可以通过撤销Redis中的令牌避免他人违规操作)

        // 将当前登录的用户id存放入令牌中
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        String jwtToken = JwtUtil.createJWT(jwtProperties.getKey(), jwtProperties.getTtlMillis(), claims);

        // 存放入Redis中
        redisTemplate.opsForValue().set("jwtToken: ",jwtToken);

        // 4. 返回结果
        return this.getLoginUserVO(user,jwtToken);
    }

    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (userAccount.length() < 4) {
            throw new BusinessException(ResultCodeEnum.USER_ACCOUNT_FORMAT);
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ResultCodeEnum.USER_PASSWORD_FORMAT);
        }
        if (checkPassword.length() < 8) {
            throw new BusinessException(ResultCodeEnum.USER_CHECK_PASSWORD_FORMAT);
        }
        if (!ObjectUtils.equals(userPassword,checkPassword)) {
            throw new BusinessException(ResultCodeEnum.USER_CHECK_PASSWORD_FORMAT);
        }
        // 2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 3.用户的账号密码存入到数据库中

        // 用户账号不可重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        User user = this.baseMapper.selectOne(queryWrapper);
        if (!ObjectUtils.isEmpty(user)) {
            throw new BusinessException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        // 插入数据库中  使用链式编程
        User realUser = User.builder().userAccount(userAccount)
                .userPassword(encryptPassword)
                .build();
        boolean saveResult = this.save(realUser);
        // 存储数据库失败
        if (!saveResult) {
            throw new BusinessException(ResultCodeEnum.SAVE_DB_ERROR);
        }
        // 4. 返回存储用户的id
        return realUser.getId();
    }

    @Override
    public Long userAdd(UserMessageRequest userMessageRequest) {
        // 1.获取需要插入的数据  默认密码为 12345678
        userMessageRequest.setUserPassword("12345678");
        User user = User.builder()
                .userAccount(userMessageRequest.getUserAccount())
                .userPassword(DigestUtils.md5DigestAsHex((SALT + (userMessageRequest.getUserPassword())).getBytes()))
                .userName(userMessageRequest.getUserName())
                .userAvatar(userMessageRequest.getUserAvatar())
                .userRole(userMessageRequest.getUserRole()).build();
        boolean result = this.save(user);
        if (!result){
            throw new BusinessException(ResultCodeEnum.ADD_FAIL);
        }
        return user.getId();
    }

    @Override
    public Long userRemove(long id) {
        // 根据当前id查询当前用户是否存在
        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("id", id);
        User user = this.baseMapper.selectOne(objectQueryWrapper);
        if (ObjectUtils.isEmpty(user)){
            throw new BusinessException(ResultCodeEnum.REMOVE_ERROR);
        }

        boolean result = this.removeById(user.getId());

        if (!result){
            throw new BusinessException(ResultCodeEnum.REMOVE_ERROR);
        }

        return user.getId();
    }

    @Override
    public Long userUpdate(UserMessageRequest userMessageRequest) {
        // 根据传递的Id 获取修改的用户信息 将不为空的数据进行修改
        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("id", userMessageRequest.getId());
        User user = this.baseMapper.selectOne(objectQueryWrapper);
        if (ObjectUtils.isEmpty(user)){
            throw new BusinessException(ResultCodeEnum.MODIFY_NULL);
        }

        if(userMessageRequest.getUserAccount() != null){
            user.setUserAccount(userMessageRequest.getUserAccount());
        }
        if (userMessageRequest.getUserRole() != null){
            user.setUserRole(userMessageRequest.getUserRole());
        }
        if (userMessageRequest.getUserName() != null){
            user.setUserName(userMessageRequest.getUserName());
        }
        if (userMessageRequest.getUserPassword() != null){
            user.setUserPassword(DigestUtils.md5DigestAsHex((SALT + (userMessageRequest.getUserPassword())).getBytes()));
        }
        if (userMessageRequest.getUserAvatar()!= null){
            user.setUserAvatar(userMessageRequest.getUserAvatar());
        }

        boolean result = this.updateById(user);
        if (!result){
            throw new BusinessException(ResultCodeEnum.MODIFY_ERROR);
        }
        return user.getId();
    }


    /**
     * 获取登录用户信息 封装返回给前台
     *
     * @param user
     * @return
     */
    public LoginUserVO getLoginUserVO(User user,String jwtToken) {
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        loginUserVO.setJwtToken(jwtToken);
        return loginUserVO;
    }
}




