package com.example.demo1.controller;

import com.example.demo1.service.TaskService;
import com.example.demo1.service.WeatherService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class TestController {
    @Autowired
    private WeatherService weatherService;

    @RequestMapping("/test")
    public void test() {
        weatherService.getWeather(511400);
    }

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/test1")
    public void test1() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("D:/a.txt");
        byte[] bytes = toByteArray(fileInputStream);
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        HttpHeaders fileHeader = new HttpHeaders();
        HttpEntity<ByteArrayResource> filePart = new HttpEntity<>(new
                ByteArrayResource(bytes), fileHeader);
        postParameters.add("file", bytes);
        // 使用客户端的请求头,发起请求
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("Cookie", "JSESSIONID=358a4b6b-aec1-4b16-bfa2-f95f784d1a79");
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36");
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity(postParameters, headers);

        String s = restTemplate.postForObject("http://192.168.2.12:8088/file/upload/moreFileUpload", request, String.class);
        System.out.println("-----------------------");
        System.out.println(s);
    }

    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    public static void method(String startTime, String endTime) throws ParseException {
        StringBuilder sql = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date start = simpleDateFormat.parse(startTime);
        Date end = simpleDateFormat.parse(endTime);
        int s = start.compareTo(end);
        for (; start.compareTo(end) <= 0; start = method1(start)) {
            if (sql.length() == 0) {
                sql.append("SELECT count(amount),sum(amount) FROM index_details WHERE bidding='INVITE_BIDDING' and publish_at >= '").append(simpleDateFormat.format(start)).append(" 00:00:00' and publish_at <= '").append(simpleDateFormat.format(start)).append(" 23:59:59'");
            } else {
                sql.append(" union all SELECT count(amount),sum(amount) FROM index_details WHERE bidding='INVITE_BIDDING' and publish_at >= '").append(simpleDateFormat.format(start)).append(" 00:00:00' and publish_at <= '").append(simpleDateFormat.format(start)).append(" 23:59:59'");
            }
        }
        sql.append(";");
        System.out.println(sql.toString());
    }

    public static Date method1(Date current) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(current);
        calendar.add(calendar.DATE, 1);
        current = calendar.getTime();
        return current;
    }

    public static void main(String[] args) throws ParseException {
        method("2021-06-01", "2021-06-04");
    }

    @GetMapping("test2")
    public void test2() throws Exception {
        String[] strs = new String[]{"订单号", "运单号", "揽件员", "揽件码", "已收运费", "抵扣运费", "客户名称", "寄件人", "寄件人电话", "寄件区域", "寄件人地址", "收件人", "收件人电话", "收件区域", "商品类型", "重量", "运费（报价单）", "物流状态", "备注", "订单创建时间"};
        String testUrl = "http://upload.kuaidihelp.com/excel/outputstatement/2021/06/30/17/OrderStatement-handleCourierCustomer60dc3f3a727a2.xlsx";
        URL url = new URL(testUrl);//把远程文件地址转换成URL格式
        InputStream fin = url.openStream();
        XSSFWorkbook work = new XSSFWorkbook(fin);
        XSSFSheet sheet = work.getSheetAt(0);//获取第一个表
        List<Row> rows = new ArrayList<Row>();
        for (Row row : sheet) {
            if ("代明阳".equals(row.getCell(8).getStringCellValue())) {
                rows.add(row);
            }
        }
        export(strs, rows);
    }


    public void export(String[] titles, List<Row> rows) throws Exception {
        try {
            // 第一步，创建一个workbook，对应一个Excel文件
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet hssfSheet = workbook.createSheet("sheet1");
            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
            HSSFRow row0 = hssfSheet.createRow(0);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
            HSSFCell hssfCell = null;
            for (int i = 0; i < titles.length; i++) {
                hssfCell = row0.createCell(i);//列索引从0开始
                hssfCell.setCellValue(titles[i]);//列名1
                hssfCell.setCellStyle(hssfCellStyle);//列居中显示
            }
            System.out.printf("==========总数：%d========================", rows.size());
            for (int i = 0; i < rows.size(); i++) {
                HSSFRow row = hssfSheet.createRow(i + 1);
                for (int j = 0; j < rows.get(i).getRowNum(); j++) {
                    if (rows.get(i).getCell(j) != null) {
                        switch (rows.get(i).getCell(j).getCellType()) {
                            case NUMERIC:
                            case FORMULA:
                                row.createCell(j).setCellValue(rows.get(i).getCell(j).getNumericCellValue());
                                break;
                            case STRING:
                                row.createCell(j).setCellValue(rows.get(i).getCell(j).getStringCellValue());
                                break;
                            case BLANK:
                                break;
                            case BOOLEAN:
                                row.createCell(j).setCellValue(rows.get(i).getCell(j).getBooleanCellValue());
                                break;
                            case ERROR:
                                row.createCell(j).setCellValue(rows.get(i).getCell(j).getErrorCellValue());
                                break;
                        }
                    }
                }
            }
            //输出到本地文件
            FileOutputStream out1 = new FileOutputStream("D:/a.xls");
            workbook.write(out1);
            out1.close();
            // 第七步，将文件输出到客户端浏览器
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("导出信息失败！");

        }

        ThreadLocal<Object> objectThreadLocal = new ThreadLocal<>();
        objectThreadLocal.set("13213");

        objectThreadLocal.get();
        objectThreadLocal.set("helolo");
        objectThreadLocal.get();
    }

    @Autowired
    private TaskService taskService;

    /**
     * 测试发送任务
     */
    @GetMapping("/test3")
    public void test3() {
        taskService.remindTask();
    }
}
