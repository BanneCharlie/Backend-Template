package com.banne.template.common.enumeration;

import lombok.Getter;

@Getter // 提供获取属性值的getter方法
public enum ResultCodeEnum {

    SUCCESS(200 , "操作成功") ,

    LOGIN_CONTENT_NULL(202,"用户名或密码未输入"),
    LOGIN_ERROR(201 , "用户名或者密码错误"),
    CAPTCHA_ERROR(221,"验证码错误"),
    LOGIN_AUTH(208 , "用户未登录"),
    USER_NOT_EXIST(222,"用户不存在"),
    USER_NAME_IS_EXISTS(209 , "用户名已经存在"),

    USER_REGISTER_NULL(220,"请输入正确的用户注册信息"),
    USER_ACCOUNT_FORMAT(220,"请输入正确的账号"),
    USER_PASSWORD_FORMAT(220,"请输入正确的密码"),
    USER_CHECK_PASSWORD_FORMAT(220,"请输入正确的确认密码"),

    SYSTEM_ERROR(9999 , "您的网络有问题请稍后重试"),
    NODE_ERROR( 217, "该节点下有子节点，不可以删除"),
    DATA_ERROR(204, "数据异常"),
    ACCOUNT_STOP( 216, "账号已停用"),
    STOCK_LESS( 219, "库存不足"),

    SAVE_DB_ERROR(223,"存储数据库失败"),

    ADD_NULL(301,"添加的信息为空" ),
    ADD_FAIL(302, "添加失败"),

    REMOVE_ERROR(400,"删除失败" ),

    MODIFY_NULL(401,"修改的信息为空" ),
    MODIFY_ERROR(402, "修改操作失败"), LOGOUT(200,"用户登出" ), USER_NOT_EXISTS(333,"用户不存在" );

    private Integer code ;      // 业务状态码
    private String message ;    // 响应消息

    private ResultCodeEnum(Integer code , String message) {
        this.code = code ;
        this.message = message ;
    }
}
