package com.nowcoder.community.dao;

import com.nowcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.stream.IntStream;

//接口，不需要写实现类，底层会自动实现这个接口
// 只需要提供一个配置文件，写上所需用的sql语句即可  文件放在resources的mapper下

@Mapper  //mybatis的注解，其实用@repository也可以
public interface UserMapper {

    User selectById(int id);

    User selectByName(String username);

    User selectByEmail(String email);

    // 插入用户，返回插入数据的行数
    int insertUser(User user);

    // 修改user状态
    int updateStatus(int id, int status);

    // 更新头像路径
    int updateHeaderUrl(int id, String headerUrl);

    int updatePassword(int id, String password);

}
