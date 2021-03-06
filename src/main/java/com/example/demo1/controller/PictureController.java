package com.example.demo1.controller;

import com.example.demo1.config.AppProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class PictureController {
    @Autowired
    private AppProperties myAppProperties;

    //通过produces 告知浏览器我要返回的媒体类型
    @GetMapping(value = "/getImage", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public BufferedImage getImage() throws IOException {
        File file = new File(myAppProperties.getPictureSourcePath());
        String[] list = file.list();
        int index = (int) (Math.random() * list.length);
        String random = list[index];
        File file1 = new File(myAppProperties.getPictureSourcePath() + File.separator + random);
        BufferedImage read = ImageIO.read(new FileInputStream(file1));
        return read;
    }

    //获取生成得二维码图片
    @GetMapping(value = "/getImage2", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public BufferedImage getImage(HttpServletRequest request) throws IOException {
        String fileName = request.getParameter("fileName");
        if (!StringUtils.isEmpty(fileName)) {
            File file1 = new File(myAppProperties.getPictureQrPath() + File.separator + fileName);
            BufferedImage read = ImageIO.read(new FileInputStream(file1));
            return read;
        }
        return null;
    }
}
