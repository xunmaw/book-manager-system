import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.xunmaw.book.pojo.UserExcelData;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        //工作簿对象
        ExcelWriterBuilder writerBuilder = EasyExcel.write("测试.xlsx", UserExcelData.class);

        ExcelWriterSheetBuilder sheet = writerBuilder.sheet();

        List<UserExcelData> list = new ArrayList<>();

        list.add(new UserExcelData(1, "cc", "熙杰", "数科院", "计科", 1));

        list.add(new UserExcelData(2, "cc", "熙杰", "数科院", "计科", 2));

        sheet.doWrite(list);

    }
}
