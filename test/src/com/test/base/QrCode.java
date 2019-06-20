package com.test.base;

import java.awt.BasicStroke;
import java.awt.Color;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import org.apache.commons.lang.time.DateUtils;

public class QrCode {
    public static void CreateQrCode(String path, String Content) {
        // 定义参数：
        int width = 300; // 图片宽度
        int height = 300; // 图片高度
        String format = "png"; // 图片格式
        // 定义二维码的参数
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
        // 生成二维码

        // 1.定义HashMap hints
        // 2.hints调用put函数设置字符集、间距以及纠错度为M
        // 3.最后用MultiformatWriter函数类调用echoed函数并返回一个值 然后写入文件

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(Content, BarcodeFormat.QR_CODE, width, height, hints);
            // 这里路径后面的img.png不可省略，前面是自己选取生成的图片地址
            File f = new File(path);
            if (!f.exists()) {
                f.createNewFile();
            }
            Path file = f.toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String GetQrContent(String path) {
        String ret = "";
        DateUtils dateUtils = new DateUtils();
        MultiFormatReader formatReader = new MultiFormatReader();
        try {
            File file = new File(path);
            BufferedImage image = ImageIO.read(file);
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);

            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

            HashMap hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

            com.google.zxing.Result result = formatReader.decode(binaryBitmap, hints);

            // System.out.println(result.toString());
            // System.out.println(result.getText());
            ret = result.getText();
        } catch (Exception e) {
            System.err.println(e);
        }
        return ret;
    }

    public static void CreateLogoQrCode(String path, String Content) {
        BufferedImage img;
        java.awt.Graphics2D g;
        BufferedImage logo;
        try {
            img = ImageIO.read(new File("D:/img.png"));
            g = img.createGraphics(); // 构建绘图图像
            logo = ImageIO.read(new File("C:\\Users\\bwt\\Downloads\\unlogin_img.png"));
            int logoWidth = logo.getWidth(null) > img.getWidth() * 2 / 10 ? (img.getWidth() * 2 / 10)
                    : logo.getWidth(null);
            int logoHeight = logo.getHeight(null) > img.getHeight() * 2 / 10 ? (img.getHeight() * 2 / 10)
                    : logo.getHeight(null); // 设置logo大小

            int x = (img.getWidth() - logoWidth) / 2; // 位置：默认中间
            int y = (img.getHeight() - logoHeight) / 2;

            g.drawImage(logo, x, y, logoWidth, logoHeight, null); // 绘制图片
            g.drawRoundRect(x, y, logoWidth, logoHeight, 15, 15);
            g.setStroke(new BasicStroke(2)); // 边框宽度
            g.setColor(Color.white);
            g.drawRect(x, y, logoWidth, logoHeight);

            g.dispose();
            logo.flush();
            img.flush();

            ImageIO.write(img, "png", new File("D:/img1.png"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}