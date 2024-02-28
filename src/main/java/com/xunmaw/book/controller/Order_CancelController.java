package com.xunmaw.book.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xunmaw.book.pojo.Order_Cancel;
import com.xunmaw.book.service.impl.Order_CancelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class Order_CancelController {

    @Autowired
    private Order_CancelServiceImpl order_cancelService;

    @RequestMapping("/order2s")
    private String getOrder2List(Model model, @RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "12") Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Order_Cancel> orderCancels = order_cancelService.getOrder_CancelList();
        model.addAttribute("order2s", orderCancels);
        PageInfo<Order_Cancel> pageInfo = new PageInfo<>(orderCancels);
        model.addAttribute("pageInfo", pageInfo);
        return "order2/list";

    }

    @RequestMapping("/order2/detail/{id}")
    public String bookDetail(@PathVariable("id") int id, Model model) {

        Order_Cancel order = order_cancelService.getOrder_CancelById(id);

        model.addAttribute("order", order);

        return "order2/detail";
    }

    @RequestMapping("/order2/query")
    private String orderQuery2(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "bookname", required = false) String bookname,
            @RequestParam(value = "state", required = false) Integer state,
            @RequestParam(value = "btnType", defaultValue = "1") Integer btnType,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "12") Integer pageSize,
            Model model) {


        List<Order_Cancel> orderCancels = null;

        //给模板引擎添加搜索参数
        if (btnType != null) {
            model.addAttribute("btnType", btnType);
        }
        if (!StringUtils.isEmpty(username)) {
            model.addAttribute("username", username);
        }
        if (!StringUtils.isEmpty(bookname)) {
            model.addAttribute("bookname", bookname);
        }
        model.addAttribute("selectstatus", state);
        PageHelper.startPage(pageNum, pageSize);
        if (btnType == 1) {
            orderCancels = order_cancelService.accuracyGetOrder_Cancel(StringUtils.isEmpty(bookname) ? null : bookname, StringUtils.isEmpty(username) ? null : username, state == null || state == 0 ? null : state);
        } else {
            orderCancels = order_cancelService.likeGetOrder_Cancel(StringUtils.isEmpty(bookname) ? null : bookname, StringUtils.isEmpty(username) ? null : username, state == null || state == 0 ? null : state);
        }

        model.addAttribute("order2s", orderCancels);
        PageInfo<Order_Cancel> pageInfo = new PageInfo<>(orderCancels);
        model.addAttribute("pageInfo", pageInfo);
        return "order2/list";

    }

}
