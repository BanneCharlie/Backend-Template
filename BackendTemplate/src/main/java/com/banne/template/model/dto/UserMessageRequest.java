package com.banne.template.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserMessageRequest implements Serializable {

    /**
     * 用户id
     *
     */
     private Long id;
    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword = "12345678";

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户角色: user, admin
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}
