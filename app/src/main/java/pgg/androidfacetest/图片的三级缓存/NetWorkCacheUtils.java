package pgg.androidfacetest.图片的三级缓存;

/**
 * Created by PDD on 2018/3/6.
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 三级缓存值网络缓存
 */
public class NetWorkCacheUtils {
    private MemoryCacheUtils memoryCacheUtils;
    private LocalCacheUtils localCacheUtils;
    private Context context;

    public NetWorkCacheUtils(Context context,MemoryCacheUtils memoryCacheUtils, LocalCacheUtils localCacheUtils){
        this.memoryCacheUtils=memoryCacheUtils;
        this.localCacheUtils=localCacheUtils;
        this.context=context;
    }

    /**
     * 从网络中下载图片
     * @param ivPic 需要显示图片的ImageView
     * @param url
     */
    public void getBitmapFromNet(ImageView ivPic, String url) {
       new BitmapTask().execute(ivPic,url);
    }

    class BitmapTask extends AsyncTask<Object,Void,InputStream>{
        private ImageView ivPic;
        private String url;

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            if (inputStream!=null){
                BitmapFactory.Options options=new BitmapFactory.Options();
                options.inSampleSize=2;
                options.inPreferredConfig= Bitmap.Config.ARGB_4444;
                Bitmap bitmap=BitmapFactory.decodeStream(inputStream,null,options);
                ivPic.setImageBitmap(bitmap);
                System.out.println("从网络中获取图片成功");
                //保存在本地
                localCacheUtils.setBitmapToLocal(context,url,inputStream);
                //保存在缓存
                memoryCacheUtils.setBitmapToMemory(url,bitmap);
            }
        }

        @Override
        protected InputStream doInBackground(Object... params) {
            ivPic= (ImageView) params[0];
            url= (String) params[1];
            return downLoadBitmap(url);
        }
    }

    private InputStream downLoadBitmap(String url) {
        HttpURLConnection connection=null;
        try {
            connection=(HttpURLConnection)new URL(url).openConnection();
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            connection.setRequestMethod("GET");
            int responseCode=connection.getResponseCode();
            if (responseCode==200){
                return connection.getInputStream();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            connection.disconnect();
        }
        return null;
    }

}
