package com.xunmaw.book.dao;

import com.xunmaw.book.pojo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : xunmaw
 * @create 2022/7/17 9:48
 */
@Repository
@Mapper
public interface DepartmentMapper {

    List<Department> getDepartList();

    Department getDepartById(int id);

    void addDepart(String name);

    void delDepart(int id);

    void updateDepart(Department department);

}
