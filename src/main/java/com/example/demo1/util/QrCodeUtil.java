package com.example.demo1.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;

/**
 * 二维码工具类
 */
public class QrCodeUtil {

    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;


    /**
     * @param content      二维码内容
     * @param logoStream   logo的文件流，不需要就传null
     * @param qrCodeStream 二维码输出文件流
     * @param needCompress logo图片是否需要压缩
     * @throws Exception
     */
    public static void createImage(String content, InputStream logoStream,
                                   OutputStream qrCodeStream, boolean needCompress) throws Exception {
        createImage(content, logoStream, qrCodeStream, FORMAT_NAME, needCompress);
    }

    /**
     * @param content      二维码内容
     * @param logoStream   logo的文件流，不需要就传null
     * @param qrCodeStream 二维码输出文件流
     * @param formatName   二维码图片格式
     * @param needCompress logo图片是否需要压缩
     * @throws Exception
     */
    public static void createImage(String content, InputStream logoStream, OutputStream qrCodeStream,
                                   String formatName, boolean needCompress) throws Exception {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        // 插入图片
//        QrCodeUtil.insertImage(image, logoStream, needCompress);
        //输出到输出流
        ImageIO.write(image, formatName, qrCodeStream);
    }
    public static void createImage(String content,  OutputStream qrCodeStream,
                                   String formatName) throws Exception {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        //输出到输出流
        ImageIO.write(image, formatName, qrCodeStream);
    }

    private static void insertImage(BufferedImage source, InputStream logoStream, boolean needCompress) throws Exception {
        if (logoStream == null) {
            return;
        }
        Image src = ImageIO.read(logoStream);
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    public static String decode(InputStream inputStream) throws Exception {
        BufferedImage image = ImageIO.read(inputStream);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable hints = new Hashtable();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        return result.getText();
    }

    public static String decode(String path) {
        String str = null;
        try {
            InputStream qrCodeInputStream = new FileInputStream(path);
            str = QrCodeUtil.decode(qrCodeInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("图片解析异常");
        }
        return str;
    }

    public static void main(String[] args) throws Exception {
        // 存放在二维码中的内容
        String text = "我是小铭\r\n hello world";
        // 嵌入的logo文件流
        InputStream logoStream = new FileInputStream("D:\\bai.jpg");
        // 输出二维码的文件流
        FileOutputStream qrCodeOutputStream = new FileOutputStream("D:\\bai_two.jpg");
        //生成二维码
        QrCodeUtil.createImage(text, logoStream, qrCodeOutputStream, true);
        qrCodeOutputStream.flush();

        // 解析二维码
        InputStream qrCodeInputStream = new FileInputStream("D:\\bai_two.jpg");
        String str = "";
        try {
            str = QrCodeUtil.decode(qrCodeInputStream);
        } catch (Exception e) {
            System.out.println("图片解析异常");
        } finally {
            System.out.println(str);
        }
    }
}