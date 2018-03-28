package pgg.androidfacetest.AsyncTask;

import android.os.AsyncTask;
import android.os.HandlerThread;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by PDD on 2018/2/28.
 */


/**
 *
 */
public class AsyncTaskFace extends AsyncTask<Integer,Integer,String> {

    TextView textView;
    ProgressBar progressBar;

    public AsyncTaskFace(TextView textView,ProgressBar progressBar) {
        this.textView=textView;
        this.progressBar=progressBar;
    }

    @Override
    protected String doInBackground(Integer... params) {
        int i;
        for (i=10;i<1000000;i+=10){
            publishProgress(i);
        }
        return i+params[0].intValue()+"";
    }


    @Override
    protected void onPreExecute() {
        textView.setText("开始执行异步任务");
    }

    @Override
    protected void onPostExecute(String s) {
        textView.setText("异步任务执行结束     " +s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int value=values[0];
        progressBar.setProgress(value);
    }

}
