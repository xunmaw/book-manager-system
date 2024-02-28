package com.xunmaw.book.dao;

import com.xunmaw.book.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : xunmaw
 * @create 2022/7/16 13:50
 */
@Repository
@Mapper
public interface UserMapper {

    List<User> getUsers();

    User getUserById(int id);

    List<User> selectAccuracyUser(@Param("username") String username, @Param("name") String name, @Param("dept_id") Integer dept_id, @Param("major_id") Integer major_id);

    List<User> likeSelectUser(@Param("username") String username, @Param("name") String name, @Param("dept_id") Integer dept_id, @Param("major_id") Integer major_id);

}
