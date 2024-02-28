package com.xunmaw.book.controller;

import com.xunmaw.book.pojo.Manager;
import com.xunmaw.book.service.impl.ManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class ManagerController {

    @Autowired
    private ManagerServiceImpl managerService;

    private Manager manager;

    @RequestMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {

        //通过用户名找到管理员
        manager = managerService.getManagerByUsername(username);

        //进行比对
        if (manager != null && manager.getPassword().equals(password)) {
            //验证通过,记录当前登录者的用户名,重定向到主页
            session.setAttribute("loginUser", username);
            return "redirect:/main.html";
        } else {
            //登录失败，提示用户
            model.addAttribute("msg", "用户名或密码错误");
            return "index";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }


}
