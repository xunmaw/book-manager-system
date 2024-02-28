package com.xunmaw.book.controller;

import com.xunmaw.book.dao.WishMapper;
import com.xunmaw.book.pojo.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
public class WishController {

    @Autowired
    private WishMapper wishMapper;

    @RequestMapping("/wishes")
    public String wishList(Model model) {

        List<Wish> wishes = wishMapper.getWishList();
        model.addAttribute("wishes", wishes);
        return "/wish/list";

    }

    @RequestMapping("/wish/detail/{id}")
    public String wishDetail(@PathVariable("id") int id, Model model) {

        Wish wish = wishMapper.getWishById(id);

        model.addAttribute("wish", wish);

        return "wish/detail";
    }
}
