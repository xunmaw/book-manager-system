package com.xunmaw.book.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xunmaw.book.pojo.Book;
import com.xunmaw.book.service.impl.BookServiceImpl;
import com.xunmaw.book.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class BookController {

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private OrderServiceImpl orderService;

    @RequestMapping("/books")
    public String bookList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "12") Integer pageSize, Model model) {

        PageHelper.startPage(pageNum, pageSize);

        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books);

        PageInfo<Book> pageInfo = new PageInfo<>(books);
        model.addAttribute("pageInfo", pageInfo);

        return "book/list";
    }

    @RequestMapping("/book/detail/{id}")
    public String bookDetail(@PathVariable("id") int id, Model model) {

        Book book = bookService.getBookById(id);

        model.addAttribute("book", book);

        return "book/detail";
    }

    @RequestMapping("/book/query")
    public String bookquery(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "12") Integer pageSize,
                            @RequestParam(value = "bookname", required = false) String bookname,
                            @RequestParam(value = "state", required = false) Integer state,
                            @RequestParam(value = "btnType", defaultValue = "1") Integer btnType,
                            Model model) {
        if (!StringUtils.isEmpty(bookname)) {
            model.addAttribute("bookname", bookname);
        }
        if (state != null) {
            model.addAttribute("selectstatus", state);
        }
        model.addAttribute("btnType", btnType);
        PageHelper.startPage(pageNum, pageSize);
        List<Book> books = null;
        if (btnType == 1) {
            //精准查询
            books = bookService.accuracyFindBook(StringUtils.isEmpty(bookname) ? null : bookname, state == null || state == 0 ? null : state);
        } else {
            //模糊查询
            books = bookService.likeFindBook(StringUtils.isEmpty(bookname) ? null : bookname, state == null || state == 0 ? null : state);
        }
        model.addAttribute("books", books);
        PageInfo<Book> pageInfo = new PageInfo<>(books);
        model.addAttribute("pageInfo", pageInfo);
        return "book/list";
    }


}
