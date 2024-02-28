package com.xunmaw.book.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : xunmaw
 * @create 2022/7/16 15:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private int id;
    private String name;
    private String publisher;
    private String code;
    private String author;
    private double price;
    private int newDeg;

    private int uId;
    private String uname;

    private int state;
    private String sname;

    private Date publishTime;

}
