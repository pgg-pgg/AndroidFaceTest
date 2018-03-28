package pgg.androidfacetest.关于Bitmap处理的几种方法;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by PDD on 2018/3/6.
 */

public class BitmapTransformUtils {

    //根据图片uri生成Bitmap对象
    public static Bitmap getBitmapByUrl(Context context, Uri uri) {
        if (uri == null)
            return null;
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    //bitmap 转base64
    public static String getBase64ByBitmap(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(out.toByteArray(), Base64.DEFAULT);
    }

    //base64转成bitmap
    public static Bitmap getBitmapByBase64(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //根据本地图片路径String转base64
    public static String getFileBase64String(String path) {
        File file = new File(path);
        try {
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int i = in.read();
            while (i != -1) {
                out.write(i);
                i = in.read();
            }
            return Base64.encodeToString(out.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //加载本地图片
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //将Bitmap保存在本地
    public static void saveBitmap(Context context, Bitmap bitmap) {
        //首先保存bitmap
        File file=new File(Environment.getExternalStorageDirectory(),"bitmap" );
        if (!file.exists()){
            file.mkdir();
        }
        String filename="bitmap"+".png";
        File file1=new File(file,filename);
        try {
            FileOutputStream out=new FileOutputStream(file1);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),file1.getAbsolutePath(),filename,null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));
    }

    //bitmap转换成byte数组
    /**
     * bitmap转换成byte数组
     *
     * @param bitmap
     * @param needRecycle
     * @return
     */
    public static byte[] bitmapToByteArray(Bitmap bitmap, boolean needRecycle) {
        if (null == bitmap) {
            return null;
        }
        if (bitmap.isRecycled()) {
            return null;
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bitmap.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
        }
        return result;
    }
}
