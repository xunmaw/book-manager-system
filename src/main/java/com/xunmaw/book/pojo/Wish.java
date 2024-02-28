package com.xunmaw.book.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : xunmaw
 * @create 2022/7/17 14:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wish {

    private int id;

    private int user_id;
    private String nickName;
    private String tel;

    private String title;
    private String desc;

    private int state;
    private String sName;
}
