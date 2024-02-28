package com.xunmaw.book.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/*
    导出表格的数据格式
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER) //水平居中
public class OrderExcelData {

    @ExcelProperty("编号")
    private Integer id;
    @ExcelProperty("购买书名")
    @ColumnWidth(20)
    private String name;
    @ExcelProperty("价格")
    private Double price;
    @ExcelProperty("购买人用户名")
    private String nickName;
    @ExcelProperty("购买人电话")
    private String tel;
    @ExcelProperty("发货方用户名")
    private String uName;
    @ExcelProperty("发货方电话")
    private String uTel;
    @ExcelProperty("订单状态")
    private String state;
    @ExcelProperty("付款时间")
    @ColumnWidth(40)
    @DateTimeFormat("yyyy-mm-dd hh:MM:ss")
    private Date time;

}
