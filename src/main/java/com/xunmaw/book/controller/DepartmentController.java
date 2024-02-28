package com.xunmaw.book.controller;


import com.xunmaw.book.pojo.Department;
import com.xunmaw.book.service.impl.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class DepartmentController {

    @Autowired
    private DepartmentServiceImpl departmentService;

    @RequestMapping("/departs")
    public String departList(Model model) {

        List<Department> departments = departmentService.getDepartList();
        model.addAttribute("departs", departments);

        return "department/list";
    }

    @RequestMapping("/depart/toAdd")
    public String toAdd() {
        return "department/add";
    }

    @RequestMapping("/depart/add")
    public String Add(@RequestParam String name) {
        departmentService.addDepart(name);
        return "redirect:/departs";
    }

    @RequestMapping("/depart/delete/{id}")
    public String delDepart(@PathVariable("id") int id) {
        departmentService.delDepart(id);
        return "redirect:/departs";
    }

    @RequestMapping("/depart/toupdate/{id}")
    public String toUpdateDepart(@PathVariable("id") int id, Model model) {
        Department department = departmentService.getDepartById(id);
        model.addAttribute("depart", department);
        return "department/update";
    }

    @RequestMapping("/depart/update")
    public String updateDepart(Department department) {
        departmentService.updateDepart(department);
        return "redirect:/departs";
    }

    @RequestMapping("/depart/query")
    public String queryDepart(@RequestParam(required = false) Integer id, Model model) {
        if (id == null){
            List<Department> departList = departmentService.getDepartList();
            model.addAttribute("departs", departList);
            return "department/list";
        }
        Department department = departmentService.getDepartById(id);
        model.addAttribute("depart", department);

        return "department/queryList";
    }

}
