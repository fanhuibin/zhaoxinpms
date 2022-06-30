package com.zhaoxinms.common.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

// 二维码工具类
public class QRCodeUtil {
    public static final String FORMAT = "jpg"; // 二维码图片类型
    private static final String FONT_STR = "华文细黑"; // 字体
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    /**
     * 生成二维码(无logo)
     * @param width 二维码宽度
     * @param height 二维码高度
     * @param margin 二维码边距(白边宽度)
     * @param text 二维码内容
     * @param path 二维码保存路径
     * */
    public void generate(Integer width, Integer height, Integer margin, String text, OutputStream stream){
        // 定义二维码的参数
        HashMap hashMap = new HashMap();
        // 设置二维码字符编码
        hashMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 设置二维码纠错等级
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 设置二维码边距
        hashMap.put(EncodeHintType.MARGIN, margin);
        try {
            // 开始生成二维码
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hashMap);
            // 导出到指定目录
            MatrixToImageWriter.writeToStream(bitMatrix, "png", stream);
        } catch (WriterException e) {
            log.error("[二维码]生成二维码失败", e);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("[二维码]生成二维码异常]", e);
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码(无logo)
     * @param width 二维码宽度
     * @param height 二维码高度
     * @param margin 二维码边距(白边宽度)
     * @param text 二维码内容
     * @param path 二维码保存路径
     * */
    public void generate(Integer width, Integer height, Integer margin, String text, String path){
        // 定义二维码的参数
        HashMap hashMap = new HashMap();
        // 设置二维码字符编码
        hashMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 设置二维码纠错等级
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 设置二维码边距
        hashMap.put(EncodeHintType.MARGIN, margin);
        File qrFile = new File(path); // 二维码文件
        File qrParent = new File(qrFile.getParent()); // 文件父目录
        if(!qrParent.exists()) // 判断父目录是否存在
            qrParent.mkdirs(); // 不存在创建目录
        try {
            // 开始生成二维码
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hashMap);
            // 导出到指定目录
            MatrixToImageWriter.writeToPath(bitMatrix, FORMAT,qrFile.toPath());
        } catch (WriterException e) {
            log.error("[二维码]生成二维码失败", e);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("[二维码]生成二维码异常[path: " + path + "]", e);
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码(带logo)
     * @param width 二维码宽度
     * @param height 二维码高度
     * @param margin 二维码边距(白边宽度)
     * @param text 二维码内容
     * @param path 二维码保存路径
     * @param logoPath logo图片路径
     * */
    public void generate(Integer width, Integer height, Integer margin, String text, String path, String logoPath){
        this.generate(width, height, margin, text, path);
        try {
            this.generateWithLogo(path, logoPath);
        } catch (IOException e) {
            log.error("[二维码]生成带logo的二维码异常[QRCode: " + path + ", logo: " + logoPath + "]", e);
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码(带logo和文字描述)
     * @param size 二维码大小
     * @param text 二维码内容
     * @param path 二维码保存路径
     * @param logoPath logo图片路径
     * @param info  需要在二维码图片上添加的文字信息（并非二维码中的内容）
     * */
    public void generate(Integer size, String text, String path, String logoPath, String info){
        Integer width = size; // 二维码高度
        Integer fontSize = size / 25; // 字体
        Integer height = width; //高度
        this.generate(width, height, 3, text, path);
        try {
            this.generateWithLogo(path, logoPath);
            this.drawTextInImg(path, info, path, fontSize, FONT_STR);
        } catch (IOException e) {
            log.error("[二维码]生成带logo的二维码异常[QRCode: " + path + ", logo: " + logoPath + "]", e);
            e.printStackTrace();
        }
    }


    /**
     * 生成二维码(带文字描述无logo)
     * @param size 二维码大小 
     * @param text 二维码内容
     * @param path 二维码保存路径
     * @param info  需要在二维码图片上添加的文字信息（并非二维码中的内容）
     * */
    public void generate(Integer size, String text, String path, String info){
        Integer width = size; // 二维码高度
        Integer fontSize = size / 25; // 字体
        Integer height = width + fontSize * 2; //高度
        this.generate(width, height, 3, text, path);
        try {
            this.drawTextInImg(path, info, path, fontSize, FONT_STR);
        } catch (IOException e) {
            log.error("[二维码]添加文字描述异常[QRCodePath: " + path + "]", e);
            e.printStackTrace();
        }
    }


    /**
     * 生成带logo的二维码（圆角） 优化了绘制代码解决logo图失真的问题
     * @param path 二维码路径
     * @param logoPath logo图片路径
     * */
    private void generateWithLogo(String path, String logoPath) throws IOException{
        File file = new File(path);
        BufferedImage matrixImage = ImageIO.read(file);
        // 读取二维码图片，并构建绘图对象
        Graphics2D g2 = matrixImage.createGraphics();
        int matrixWidth = matrixImage.getWidth();
        int matrixHeigh = matrixImage.getHeight();
        // 读取Logo图片
        BufferedImage logo = ImageIO.read(new File(logoPath));
        // 开始绘制图片
        g2.drawImage(logo.getScaledInstance(matrixWidth/5, matrixWidth/5, Image.SCALE_SMOOTH),matrixWidth/5*2,matrixHeigh/5*2, matrixWidth/5, matrixHeigh/5, null);//绘制
        BasicStroke stroke = new BasicStroke(5,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);// 设置笔画对象
        //指定弧度的圆角矩形
        RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth/5*2, matrixHeigh/5*2, matrixWidth/5, matrixHeigh/5,20,20);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 消除锯齿
        g2.setColor(Color.white);
        g2.draw(round);// 绘制圆弧矩形

        //设置logo 有一道灰色边框
        BasicStroke stroke2 = new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke2);// 设置笔画对象
        RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(matrixWidth/5*2+2, matrixHeigh/5*2+2, matrixWidth/5-4, matrixHeigh/5-4,20,20);
        g2.setColor(new Color(128,128,128));
        g2.draw(round2);// 绘制圆弧矩形
        g2.dispose();
        logo.flush();
        matrixImage.flush() ;
        ImageIO.write(matrixImage, FORMAT, file); // 保存图片
    }

    /**
     * 生成带logo的二维码(方块)
     * @param path 二维码路径
     * @param logoPath logo图片路径
     * */
    private void generateWithCubeLogo(String path, String logoPath) throws IOException {
        BufferedImage i = ImageIO.read(new File(path));
        Integer qrWidth = i.getWidth();
        Integer qrHeight = i.getHeight();
        BufferedImage image = new BufferedImage(qrWidth, qrHeight, BufferedImage.TYPE_INT_RGB);
        // 开始利用二维码数据创建Bitmap图片
        for (int x = 0; x < qrWidth; x++) {
            for (int y = 0; y < qrHeight; y++) {
                image.setRGB(x, y, i.getRGB(x, y));
            }
        }
        int width = image.getWidth();
        int height = image.getHeight();
        // 构建绘图对象
        Graphics2D g = image.createGraphics();
        // 读取Logo图片
        BufferedImage logo = ImageIO.read(new File(logoPath));
        // 开始绘制logo图片
        g.drawImage(logo, width * 2 / 5, height * 2 / 5, width * 2 / 10, height * 2 / 10, null);
        g.dispose();
        logo.flush();
        ImageIO.write(image, "jpg", new File(path));
    }

    /**
     * 图片中添加文字
     * @param filePath 图片路径
     * @param text 需要添加的文字
     * @param outPath 添加完后文件保存的路径
     * @param fontSize 文字字体大小
     * @param fontStr 文字字体
     * */
    private void drawTextInImg(String filePath, String text, String outPath,int fontSize, String fontStr) throws IOException {
        BufferedImage img = ImageIO.read(new File(filePath));
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage bimage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Integer n = text.replaceAll("[\\u4e00-\\u9fa5]", "aa").length();
        Graphics2D g = bimage.createGraphics();
        int rectX = (img.getWidth() - fontSize * n / 2) / 2;
        int rectY = img.getHeight() - fontSize;
        Font font = new Font(fontStr, Font.PLAIN, fontSize);   //定义字体
        g.setBackground(Color.white);
        g.drawImage(img, 0, 0, null);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // 消除锯齿
        g.setPaint(Color.black);
        g.setFont(font);
        g.drawString(text, rectX, rectY);
        g.dispose();
        try {
            ImageIO.write(bimage, "jpg", new File(outPath));
        } catch (FileNotFoundException e) {
            log.error("[二维码]添加文字描述时找不到指定路径【path: " + outPath + "】", e);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("[二维码]添加文字描述时IO异常【path: " + outPath + "】", e);
            e.printStackTrace();
        }
        bimage.flush();
    }
}
