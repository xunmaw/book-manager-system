package com.xunmaw.book.dao;

import com.xunmaw.book.pojo.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : xunmaw
 * @create 2022/7/16 13:50
 */
@Repository
@Mapper
public interface ManagerMapper {

    Manager getManagerByUsername(String username);

}
