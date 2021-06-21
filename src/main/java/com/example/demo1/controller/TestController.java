package com.example.demo1.controller;

import com.example.demo1.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
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
import java.net.URL;
import java.net.URLConnection;

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
                ByteArrayResource(bytes),fileHeader);
        postParameters.add("file",bytes);
        // 使用客户端的请求头,发起请求
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("Cookie", "JSESSIONID=358a4b6b-aec1-4b16-bfa2-f95f784d1a79");
        headers.add("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36");
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity(postParameters, headers);

        String s = restTemplate.postForObject("http://192.168.2.12:8088/file/upload/moreFileUpload", request, String.class);
        System.out.println("-----------------------");
        System.out.println(s);
    }
    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024*4];
        int n=0;
        while ( (n=in.read(buffer)) !=-1) {
            out.write(buffer,0,n);
        }
        return out.toByteArray();
    }

 /*   public void test3(){
        try{
            MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
            postParameters.add("type",type);
            postParameters.add("subType",subType);

            //获取文件名称
            URL openUrl = new URL(chenvaFilePath+fileUrl);
            URLConnection urlConnection = openUrl.openConnection();
            int fileLength = urlConnection.getContentLength();
            byte[]bytes = new byte[fileLength];
            // 读取流信息，一次性写入字节数组（与下面正确示例中不同之处）
            InputStream inputStream = urlConnection.getInputStream();
            inputStream.read(bytes);
            inputStream.close();

            HttpHeaders fileHeader = new HttpHeaders();
            fileHeader.setContentType(MediaType.parseMediaType(urlConnection.getContentType()));
            fileHeader.setContentDispositionFormData("upload", fileName);
            HttpEntity<ByteArrayResource> filePart = new HttpEntity<>(new
                    ByteArrayResource(bytes),fileHeader);
            postParameters.add("upload",filePart);
        } catch (Exception e) {
            updateSyncStatus(projectId,dictId,"4");
            throw new RuntimeException("文件上传错误");
        }


        HttpHeaders headers = new HttpHeaders();

        // 使用客户端的请求头,发起请求
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("Cookie", newCookie);
        headers.add("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36");
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity(postParameters, headers);
        restTemplate.setRequestFactory(new NoRedirectClientHttpRequestFactory());

        restTemplate.postForObject(UPLOAD_PATH, request, String.class) ;
    }
*/
}
