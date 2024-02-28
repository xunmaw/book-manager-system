package com.xunmaw.book.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : xunmaw
 * @create 2022/7/16 22:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {


    private int id;

    private int id_buy; //购买人id
    private String nickname;
    private String tel;
    private String address;

    private int book_id;    //购买书id
    private String name;
    private String publisher;
    private String code;
    private String author;
    private double price;
    private int newDeg;

    private int uId;    //本书所有人id
    private String uName;
    private String uTel;
    private String uAddress;

    private Date time;
    private int state;
    private String sname;

}
