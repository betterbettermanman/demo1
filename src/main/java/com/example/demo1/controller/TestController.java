package com.example.demo1.controller;

import com.example.demo1.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
        int s=start.compareTo(end);
        for (; start.compareTo(end) <=0; start=method1(start)) {
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
        method("2021-06-01","2021-06-04");
    }
}
