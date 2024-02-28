package com.xunmaw.book.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : xunmaw
 * @create 2022/7/16 14:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Major {

    private int id;
    private String name;

    private int departId;
    private String departName;

    public Major(String name, int departId) {
        this.name = name;
        this.departId = departId;
    }
}
