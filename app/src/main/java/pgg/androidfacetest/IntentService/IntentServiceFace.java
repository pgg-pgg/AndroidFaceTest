package pgg.androidfacetest.IntentService;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Message;
import android.support.annotation.Nullable;

/**
 * Created by PDD on 2018/2/28.
 */

public class IntentServiceFace extends IntentService {

    public final static String DOWNLOAD_URL="download_url";
    public static UpdateUI updateUI;
    public static void setUpdateUI(UpdateUI updateUIa){
        updateUI=updateUIa;
    }

    public IntentServiceFace(String name) {
        super("IntentServiceFace");
    }
    /**
     * 模拟下载图片的操作，在下载完成后由Activity的回调来通知主线程更新UI
     * @param intent
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bitmap bitmap=downloadBitmapUrl(intent.getExtras().getString(DOWNLOAD_URL));
        Message message=Message.obtain();
        message.obj=bitmap;
        message.what=0;
        if (updateUI!=null){
            updateUI.updateUI(message);
        }
    }

    /**
     * 模拟下载图片的方法
     * @param string
     * @return
     */
    private Bitmap downloadBitmapUrl(String string) {
        return null;
    }

    public interface UpdateUI{
        void updateUI(Message msg);
    }
}
