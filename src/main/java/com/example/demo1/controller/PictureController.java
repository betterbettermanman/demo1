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
    @Value("${pictureSourcePath}")
    private String pictureSourcePath;
    @Value("${pictureTargetPath}")
    private String pictureTargetPath;

    //通过produces 告知浏览器我要返回的媒体类型
    @GetMapping(value = "/getImage", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public BufferedImage getImage() throws IOException {
        File file = new File(pictureSourcePath);
        String[] list = file.list();
        int index = (int) (Math.random() * list.length);
        String random = list[index];
        File file1 = new File(pictureSourcePath + File.separator + random);
        BufferedImage read = ImageIO.read(new FileInputStream(file1));
        //boolean b = file1.renameTo(new File(pictureTargetPath + File.separator + random));
//        System.out.println(b);
        return read;
    }
}
