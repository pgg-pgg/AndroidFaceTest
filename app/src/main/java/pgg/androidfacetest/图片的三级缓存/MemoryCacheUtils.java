package pgg.androidfacetest.图片的三级缓存;

import android.graphics.Bitmap;
import android.util.LruCache;


/**
 * Created by PDD on 2018/3/6.
 */

public class MemoryCacheUtils {

    private LruCache<String,Bitmap> lruCache;

    public MemoryCacheUtils(){
        //获取手机最大内存的1/8
        long memory=Runtime.getRuntime().maxMemory()/8;
        lruCache=new LruCache<String, Bitmap>((int)memory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    /**
     * 从内存中读图片
     * @param url
     * @return
     */
    public  Bitmap getBitmapFromMemory(String url) {
        Bitmap bitmap = lruCache.get(url);
        return bitmap;
    }

    public void setBitmapToMemory(String url, Bitmap bitmap) {
        lruCache.put(url,bitmap);
    }
}
