package pgg.androidfacetest.图片的三级缓存;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import pgg.androidfacetest.R;

/**
 * 图片三级缓存工具类
 * Created by PDD on 2018/3/6.
 */

public class MyBitmapUtils {

    private NetWorkCacheUtils netWorkCacheUtils;
    private MemoryCacheUtils memoryCacheUtils;
    private LocalCacheUtils localCacheUtils;

    public MyBitmapUtils(Context context){
        localCacheUtils=new LocalCacheUtils();
        memoryCacheUtils=new MemoryCacheUtils();
        netWorkCacheUtils=new NetWorkCacheUtils(context,memoryCacheUtils,localCacheUtils);
    }

    public void display(ImageView ivPic,String url) {

        //给ImageView设置默认显示图片
        ivPic.setImageResource(R.drawable.btn_zhifubao);
        //创建一个bitmap变量，用于接收从各缓存拿到的图片bitmap
        Bitmap bitmap;

        //首先从内存获取图片
        bitmap = memoryCacheUtils.getBitmapFromMemory(url);
        //判断
        if (bitmap != null) {
            ivPic.setImageBitmap(bitmap);
            System.out.println("从内存中获取图片成功");
            return;
        }
        //从内存获取图片失败，再从本地获取图片
        bitmap = localCacheUtils.getBitmapFromLocal(url);
        //判断
        if (bitmap!=null){
            ivPic.setImageBitmap(bitmap);
            //将从本地获取的图片存到内存中
            memoryCacheUtils.setBitmapToMemory(url,bitmap);
            System.out.println("从本地获取图片成功");
            return;
        }

        //如果前两种方法都无法获得，那么最后才从网络中获取图片
        netWorkCacheUtils.getBitmapFromNet(ivPic,url);
    }
}
