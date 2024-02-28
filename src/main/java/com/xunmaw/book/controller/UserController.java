package com.xunmaw.book.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xunmaw.book.pojo.Department;
import com.xunmaw.book.pojo.Major;
import com.xunmaw.book.pojo.User;
import com.xunmaw.book.pojo.UserExcelData;
import com.xunmaw.book.service.impl.UserServiceImpl;
import com.xunmaw.book.service.inte.DepartmentService;
import com.xunmaw.book.service.inte.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private MajorService majorService;
    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/users")
    public String userList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "12") Integer pageSize, Model model) {
        List<Major> majorList = majorService.getMajorList();
        model.addAttribute("majorList", majorList);
        List<Department> departList = departmentService.getDepartList();
        model.addAttribute("departList", departList);
        PageHelper.startPage(pageNum, pageSize);

        List<User> users = userService.getUsers();
        model.addAttribute("users", users);

        PageInfo<User> pageInfo = new PageInfo<>(users);
        model.addAttribute("pageInfo", pageInfo);

        return "user/list";
    }

    @RequestMapping("/user/detail/{id}")
    public String bookDetail(@PathVariable("id") int id, Model model) {

        User user = userService.getUserById(id);

        model.addAttribute("user", user);

        return "user/detail";
    }

    @RequestMapping("/user/query")
    public String queryUser(@RequestParam(value = "username", required = false) String username,
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "depart_id", required = false) Integer depart_id,
                            @RequestParam(value = "major_id", required = false) Integer major_id,
                            @RequestParam(value = "btnType", required = false, defaultValue = "1") Integer btnType,
                            @RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "12") Integer pageSize,
                            Model model) {

        List<Major> majorList = majorService.getMajorList();
        model.addAttribute("majorList", majorList);
        List<Department> departList = departmentService.getDepartList();
        model.addAttribute("departList", departList);
        PageHelper.startPage(pageNum, pageSize);
        if (depart_id != null) {
            model.addAttribute("select_depid", depart_id);
        }
        if (major_id != null) {
            model.addAttribute("select_majorid", major_id);
        }
        if (username != null && !StringUtils.isEmpty(username)) {
            model.addAttribute("username", username);
        }
        if (name != null && !StringUtils.isEmpty(name)) {
            model.addAttribute("name", name);
        }
        if (btnType != null) {
            model.addAttribute("btnType", btnType);
        }
        List<User> users = null;
        if (btnType == 1) {
            //精准查询
            users = userService.accuracyQueryUser(StringUtils.isEmpty(username) ? null : username, StringUtils.isEmpty(name) ? null : name,
                    depart_id == null || depart_id == 0 ? null : depart_id, major_id == null || major_id == 0 ? null : major_id);

        } else if (btnType == 2) {
            //模糊查询(对用户名和姓名模糊查询）
            users = userService.likeQueryUser(StringUtils.isEmpty(username) ? null : username, StringUtils.isEmpty(name) ? null : name,
                    depart_id == null || depart_id == 0 ? null : depart_id, major_id == null || major_id == 0 ? null : major_id);
        }
        System.out.println("users size : " + users.size());
        for (User user : users) {
            System.out.println("user" + user);
        }
        model.addAttribute("users", users);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        model.addAttribute("pageInfo", pageInfo);
        return "user/list";
    }

    /*
        导出Execel
     */
    @RequestMapping("/user/export")
    @ResponseBody
    public String exportExcel(@RequestParam(value = "username", required = false) String username,
                              @RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "depart_id", required = false) Integer depart_id,
                              @RequestParam(value = "major_id", required = false) Integer major_id,
                              @RequestParam(value = "btnType", defaultValue = "1") Integer btnType,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "12") Integer pageSize,
                              HttpServletResponse response) throws IOException {
        List<User> users = null;
        if (btnType == 1) {
            //精准查询
            users = userService.accuracyQueryUser(StringUtils.isEmpty(username) ? null : username, StringUtils.isEmpty(name) ? null : name,
                    depart_id == null || depart_id == 0 ? null : depart_id, major_id == null || major_id == 0 ? null : major_id);

        } else if (btnType == 2) {
            //模糊查询(对用户名和姓名模糊查询）
            users = userService.likeQueryUser(StringUtils.isEmpty(username) ? null : username, StringUtils.isEmpty(name) ? null : name,
                    depart_id == null || depart_id == 0 ? null : depart_id, major_id == null || major_id == 0 ? null : major_id);
        }
        System.out.println("users size : " + users.size());
        List<UserExcelData> list = new ArrayList<>();
        for (User user : users) {
            list.add(new UserExcelData(user.getId(), user.getNickname(), user.getName(), user.getDepartName(), user.getMajorName(), user.getGrade()));
        }

        //设置响应头信息
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("用户信息", "Utf-8");
        response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + fileName + ".xlsx");
        //获得输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //工作簿对象
        ExcelWriterBuilder writerBuilder = EasyExcel.write(outputStream, UserExcelData.class);
        //工作表对象
        ExcelWriterSheetBuilder sheet = writerBuilder.sheet();
        //导出Excel
        sheet.doWrite(list);
        outputStream.close();
        return "";

    }


}
