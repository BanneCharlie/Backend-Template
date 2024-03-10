package com.banne.template.mapper;


import com.banne.template.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86188
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2024-03-08 16:29:57
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




