package com.xunmaw.book.dao;

import com.xunmaw.book.pojo.Wish;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : xunmaw
 * @create 2022/7/17 15:04
 */
@Repository
@Mapper
public interface WishMapper {

    List<Wish> getWishList();

    Wish getWishById(int id);

}
