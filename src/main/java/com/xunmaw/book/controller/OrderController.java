package com.xunmaw.book.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xunmaw.book.pojo.Order;
import com.xunmaw.book.pojo.OrderExcelData;
import com.xunmaw.book.pojo.User;
import com.xunmaw.book.service.impl.OrderServiceImpl;
import com.xunmaw.book.service.impl.UserServiceImpl;
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
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/orders")
    public String orderList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "12") Integer pageSize, Model model) {

        PageHelper.startPage(pageNum, pageSize);

        List<Order> orders = orderService.getOrders();
        for (int i = 0; i < orders.size(); i++) {
            User user = userService.getUserById(orders.get(i).getUId());
            orders.get(i).setUName(user.getNickname());
            orders.get(i).setUTel(user.getTel());
            orders.get(i).setUAddress(user.getAddress());
        }

        model.addAttribute("orders", orders);

        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        model.addAttribute("pageInfo", pageInfo);

        return "order/list";
    }

    @RequestMapping("/order/detail/{id}")
    public String bookDetail(@PathVariable("id") int id, Model model) {

        Order order = orderService.getOrderById(id);

        User user = userService.getUserById(order.getUId());
        order.setUTel(user.getTel());
        order.setUName(user.getNickname());
        order.setUAddress(user.getAddress());

        model.addAttribute("order", order);

        return "order/detail";
    }

    /*
        查询订单(精准、模糊查询)
     */
    @RequestMapping("/order/query")
    public String accuracyQueryOrder(@RequestParam(value = "oid", required = false) Integer oid,
                                     @RequestParam(value = "username", required = false) String userName,
                                     @RequestParam(value = "status", required = false) Integer status,
                                     @RequestParam(value = "btnType", defaultValue = "1") Integer btnType,
                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "12") Integer pageSize,
                                     Model model) {

        List<Order> orders = null;
        PageHelper.startPage(pageNum, pageSize);
        if (btnType != null) {
            model.addAttribute("btnType", btnType);
        }
        if (oid != null) {
            model.addAttribute("oid", oid);
        }
        if (!StringUtils.isEmpty(userName)) {
            model.addAttribute("username", userName);
        }
        if (status != null) {
            model.addAttribute("selectstatus", status);
        }

        if (btnType == 1) {
            //精准查询
            orders = orderService.accuracyQueryBooks(oid, StringUtils.isEmpty(userName) ? null : userName, status == 0 ? null : status);
            for (int i = 0; i < orders.size(); i++) {
                User user = userService.getUserById(orders.get(i).getUId());
                orders.get(i).setUName(user.getNickname());
                orders.get(i).setUTel(user.getTel());
                orders.get(i).setUAddress(user.getAddress());
            }
        } else if (btnType == 2) {
            //模糊查询
            orders = orderService.likeQueryBooks(oid, StringUtils.isEmpty(userName) ? null : userName, status == 0 ? null : status);
            for (int i = 0; i < orders.size(); i++) {
                User user = userService.getUserById(orders.get(i).getUId());
                orders.get(i).setUName(user.getNickname());
                orders.get(i).setUTel(user.getTel());
                orders.get(i).setUAddress(user.getAddress());
            }
        }
        if (orders != null) {
            for (Order order : orders) {
                System.out.println(order);
            }
        }
        model.addAttribute("orders", orders);
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        model.addAttribute("pageInfo", pageInfo);
        return "order/list";
    }

    /*
       导出查询到的订单信息
    */
    @RequestMapping("/order/export")
    @ResponseBody
    public String excelExport(@RequestParam(value = "oid", required = false) Integer oid,
                              @RequestParam(value = "username", required = false) String userName,
                              @RequestParam(value = "status", required = false) Integer status,
                              @RequestParam(value = "btnType", defaultValue = "1") Integer btnType,
                              HttpServletResponse response) throws IOException {

        List<Order> orders = null;

        if (btnType == 1) {
            //精准查询
            orders = orderService.accuracyQueryBooks(oid, StringUtils.isEmpty(userName) ? null : userName, status == 0 ? null : status);
            for (int i = 0; i < orders.size(); i++) {
                User user = userService.getUserById(orders.get(i).getUId());
                orders.get(i).setUName(user.getNickname());
                orders.get(i).setUTel(user.getTel());
                orders.get(i).setUAddress(user.getAddress());
            }
        } else if (btnType == 2) {
            //模糊查询
            orders = orderService.likeQueryBooks(oid, StringUtils.isEmpty(userName) ? null : userName, status == 0 ? null : status);
            for (int i = 0; i < orders.size(); i++) {
                User user = userService.getUserById(orders.get(i).getUId());
                orders.get(i).setUName(user.getNickname());
                orders.get(i).setUTel(user.getTel());
                orders.get(i).setUAddress(user.getAddress());
            }
        }
        //设置响应头信息
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("订单信息", "Utf-8");
        response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + fileName + ".xlsx");

        //获得输出流
        ServletOutputStream outputStream = response.getOutputStream();

        //工作簿对象
        ExcelWriterBuilder writerBuilder = EasyExcel.write(outputStream, OrderExcelData.class);

        //工作表对象
        ExcelWriterSheetBuilder sheet = writerBuilder.sheet();
        //准备数据
        List<OrderExcelData> list = new ArrayList<>();
        for (Order order : orders) {
            String stateName = orderService.findStateById(order.getState());
            list.add(new OrderExcelData(order.getId(), order.getName(), order.getPrice(), order.getNickname(), order.getTel(), order.getUName(), order.getUTel(), stateName, order.getTime()));
        }

        //导出Excel
        sheet.doWrite(list);
        outputStream.close();

        return "";
    }


}
