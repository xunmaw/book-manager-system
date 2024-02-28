package com.xunmaw.book.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER) //水平居中
public class UserExcelData {
    @ExcelProperty("编号")
    private Integer id;

    @ExcelProperty("用户名")
    private String username;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("院系")
    @ColumnWidth(20)
    private String depart;

    @ExcelProperty("专业")
    @ColumnWidth(20)
    private String major;

    @ExcelProperty("年级")
    @ColumnWidth(10)
    private Integer grade;
}
