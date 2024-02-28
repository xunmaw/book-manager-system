package com.xunmaw.book.dao;

import com.xunmaw.book.pojo.Book;
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
public interface BookMapper {

    List<Book> getBooks();

    Book getBookById(int id);

    List<Book> selectAccuracyBook(@Param("bookname") String bookName, @Param("state") Integer state);

    List<Book> likeSelectBook(@Param("bookname") String bookName, @Param("state") Integer state);


}
