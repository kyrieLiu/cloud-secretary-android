package com.luck.cloud.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;


import com.luck.cloud.config.AppConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuyin on 2019/2/19 9:05
 *
 * @Describe 图片处理工具
 */
public class ImageUtils {

    private volatile static ImageUtils utils;

    public static ImageUtils getInstance() {
        synchronized (ImageUtils.class) {
            if (utils == null) utils = new ImageUtils();
            return utils;
        }
    }

    /**
     * 图片压缩
     *
     * @param imageList 原始图片集合
     * @return 压缩后图片集合
     */
    public ArrayList<String> compressListPicture(List<String> imageList) {
        ArrayList<String> newImageList = new ArrayList<>();
        for (int i = 0; i < imageList.size(); i++) {
            String path = imageList.get(i);
            String targetPath = executeCompressImage(path, i + "compress." + parseSuffix(path), 100, 1024 * 1024 * 2);
            newImageList.add(targetPath);
        }
        return newImageList;
    }

    /**
     * 单张图片压缩
     *
     * @param picturePath 原始图片
     * @return 压缩后图片
     */
    public String compressPicture(String picturePath) {
        String targetPath = executeCompressImage(picturePath, "compress." + parseSuffix(picturePath), 100, 1024 * 1024 * 2);
        return targetPath;
    }

    /**
     * 压缩图片
     *
     * @param filePath   源文件路径
     * @param targetPath 目标存储路径
     * @param quality    压缩质量
     * @param maxSize    压缩内存限制
     * @return
     */
    public String executeCompressImage(String filePath, String targetPath, int quality, int maxSize) {
        File file = new File(filePath);
        long size = getFileSize(file);
        if (size > maxSize) {
            FileOutputStream out = null;
            Bitmap bm = getSmallBitmap(filePath);//获取一定尺寸的图片（压缩分辨率）
            int degree = readPictureDegree(filePath);//获取相片拍摄角度
            if (degree != 0) {//旋转照片角度，防止头像横着显示
                bm = rotateBitmap(bm, degree);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (!filePath.endsWith("png")) {
                bm = compressImage(baos, bm, quality, maxSize);// 进行质量压缩，直到压缩到小于maxSize为止
            }
            File appDir = new File(Environment.getExternalStorageDirectory(), AppConstants.SD_PATH);
            if (!appDir.exists()) appDir.mkdir();
            File outputFile = new File(appDir, targetPath);
            try {
                if (outputFile.exists()) {
                    outputFile.delete();
                }
                out = new FileOutputStream(outputFile);
                if (!filePath.endsWith("png")) {
                    baos.writeTo(out);
                    baos.flush();
                } else {
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
                }

            } catch (Exception e) {

            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (Exception e) {
                    }
                }
                if (baos != null) {
                    try {
                        baos.close();
                    } catch (Exception e) {
                    }
                }
                if (bm != null) {
                    bm.recycle();
                    bm = null;
                }
            }
            return outputFile.getPath();
        } else {
            return filePath;
        }
    }

    /**
     * 获取指定文件大小
     *
     * @return
     */
    private long getFileSize(File file) {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                size = fis.available();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (Exception e) {
                    }
                }
            }
        }
        return size;
    }


    /**
     * 获取url文件后缀名
     *
     * @param url
     * @return
     */
    public String parseSuffix(String url) {
        Pattern pattern = Pattern.compile("\\S*[?]\\S*");
        Matcher matcher = pattern.matcher(url);

        String[] spUrl = url.toString().split("/");
        int len = spUrl.length;
        String endUrl = spUrl[len - 1];

        if (matcher.find()) {
            String[] spEndUrl = endUrl.split("\\?");
            return spEndUrl[0].split("\\.")[1];
        }
        return endUrl.split("\\.")[1];
    }

    /**
     * 根据路径获得图片信息并按比例压缩(压缩分辨率)，返回bitmap
     */
    public Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize2(options, 1224);// 短边大于1224进行压缩
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public int calculateInSampleSize2(BitmapFactory.Options options,
                                      int standardW) {
        final int h = options.outHeight;
        final int w = options.outWidth;
        int zoomRatio = 1;
        if (w > h && h > standardW) {
            zoomRatio = (int) (h / standardW);
        } else if (w < h && w > standardW) {
            zoomRatio = (int) (w / standardW);
        }
        if (zoomRatio <= 0)
            zoomRatio = 1;
        return zoomRatio;
    }

    /**
     * 旋转图片
     *
     * @param degress 被旋转角度
     * @param bitmap  图片对象
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public Bitmap compressImage(ByteArrayOutputStream baos, Bitmap image, int options, int maxSize) {
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        while (baos.toByteArray().length > maxSize) {
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        byte[] bytes = baos.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    /**
     * 读取照片旋转角度
     *
     * @param path 照片路径
     * @return 角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

}
