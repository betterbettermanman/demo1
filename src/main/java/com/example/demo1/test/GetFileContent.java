package com.example.demo1.test;

import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class GetFileContent {
    //需要写入得文件，暂时只能写到txt，你再粘贴到word
    static String targetFile = "F:\\workspace\\JAVA\\demo1\\src\\main\\java\\com\\example\\demo1\\test\\a.txt";
    //需要读取得文件夹
    static String filePath = "F:\\workspace\\JAVA\\demo1\\src\\main\\java\\com\\example\\demo1\\service";

    public static void main(String[] args) throws IOException {
        String targetFile = "F:\\workspace\\JAVA\\demo1\\src\\main\\java\\com\\example\\demo1\\test\\a.txt";
        System.out.println(targetFile.substring(targetFile.lastIndexOf("\\")+1));
        Set set=new HashSet();


        //iterator(filePath, targetFile);
    }

    public static void iterator(String filePath, String targetFile) throws IOException {
        File file = new File(filePath);
        if (file.isDirectory()) {
            String[] list = file.list();
            for (String fileName : list) {
                iterator(filePath + File.separator + fileName, targetFile);
            }
        } else {
            FileInputStream fileInputStream = new FileInputStream(file);
            FileOutputStream fileOutputStream = new FileOutputStream(targetFile,true);
            fileOutputStream.write(file.getName().getBytes());
            fileOutputStream.write(new byte[]{'\n','\r'});
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes,0,len);
            }
            fileOutputStream.write(new byte[]{'\n','\r'});
        }
    }
}
