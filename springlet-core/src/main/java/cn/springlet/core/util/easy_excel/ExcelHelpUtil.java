package cn.springlet.core.util.easy_excel;


import cn.springlet.core.exception.web_return.ReturnMsgException;
import cn.springlet.core.util.ServletUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * EasyExcel 相关 工具方法
 *
 * @author watermelon
 * @time 2021/3/24
 */
public class ExcelHelpUtil {


    /**
     * 获取 对于  sourceClass 类， relativePath 地址 的绝对地址
     * 用于填充 excel, 将要填充的 excel 模板放在 resources 中的时候
     *
     * @param sourceClass
     * @param relativePath
     * @return
     */
    public static String getResourceFilePath(Class sourceClass, String relativePath) {
        return sourceClass.getResource(relativePath).getFile();
    }

    /**
     * 获取 对于 class path 中文件的 输入流
     *
     * @param classPath 如 import_template/import_fill_template.xls
     * @return
     */
    public static InputStream getResourceAsStream(String classPath) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(classPath);
    }

    /**
     * 获取 excel 导出时 服务器的 OutputStream
     *
     * @return
     */
    public static OutputStream getOutputStream(String fileName) {
        HttpServletResponse response = ServletUtil.getResponse();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            return response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ReturnMsgException("服务器响应异常");
        }
    }

    public static void main(String[] args) {
        //demo
        //EasyExcel.write(ExcelHelpUtil.getOutputStream("导出数据"), ExportVO.class).excelType(ExcelTypeEnum.XLS).sheet("数据").doWrite(exportVOList);
    }
}
