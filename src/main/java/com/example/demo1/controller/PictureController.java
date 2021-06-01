package com.example.demo1.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class PictureController {
    @Value("${picturePath}")
    private String picturePath;

    //通过produces 告知浏览器我要返回的媒体类型
    @GetMapping(value = "/getImage", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public BufferedImage getImage() throws IOException {
        File file = new File(picturePath);
        String[] list = file.list();
        int index = (int) (Math.random() * list.length);
        String random = list[index];
        return ImageIO.read(new FileInputStream(new File(picturePath + File.separator + random)));
    }
}
