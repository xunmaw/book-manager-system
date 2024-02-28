package com.xunmaw.book.controller;


import com.xunmaw.book.pojo.Department;
import com.xunmaw.book.pojo.Major;
import com.xunmaw.book.service.impl.DepartmentServiceImpl;
import com.xunmaw.book.service.impl.MajorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : xunmaw
 * @create 2022/7/17 9:57
 */
@Controller
public class MajorController {

    @Autowired
    private MajorServiceImpl majorService;
    @Autowired
    private DepartmentServiceImpl departmentService;
    List<Department> departments = null;


    @RequestMapping("/majors")
    public String majorList(Model model) {

        List<Major> majors = majorService.getMajorList();
        model.addAttribute("majors", majors);

        return "major/list";
    }

    @RequestMapping("/major/toAdd")
    public String toAdd(Model model) {
        departments = departmentService.getDepartList();
        model.addAttribute("departments", departments);
        return "major/add";
    }

    @RequestMapping("/major/add")
    public String Add(Major major) {
        majorService.addMajor(new Major(major.getName(), major.getDepartId()));
        return "redirect:/majors";
    }

    @RequestMapping("/major/delete/{id}")
    public String delMajor(@PathVariable("id") int id) {
        majorService.delMajor(id);
        return "redirect:/majors";
    }

    @RequestMapping("/major/toupdate/{id}")
    public String toUpdateMajor(@PathVariable("id") int id, Model model) {

        Major major = majorService.getMajorById(id);
        model.addAttribute("major", major);

        departments = departmentService.getDepartList();
        model.addAttribute("departments", departments);
        return "major/update";
    }

    @RequestMapping("/major/update")
    public String updateMajor(Major major) {
        majorService.updMajor(major);
        return "redirect:/majors";
    }

    @RequestMapping("/major/query")
    public String queryMajor(@RequestParam(required = false) Integer id, Model model) {
        if (id == null){
            List<Major> majorList = majorService.getMajorList();
            model.addAttribute("majors", majorList);
            return "major/list";
        }
        Major major = majorService.getMajorById(id);
        model.addAttribute("major", major);

        return "major/queryList";
    }

}
