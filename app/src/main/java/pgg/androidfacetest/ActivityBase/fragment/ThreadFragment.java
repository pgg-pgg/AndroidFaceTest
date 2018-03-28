package pgg.androidfacetest.ActivityBase.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.logging.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import pgg.androidfacetest.ActivityBase.base.BaseFragment;
import pgg.androidfacetest.R;

/**
 * Created by PDD on 2018/3/18.
 */

public class ThreadFragment extends BaseFragment {

    @Bind(R.id.thread_webview)
    WebView thread_webview;
    public static BaseFragment newInstance() {
        Bundle args = new Bundle();
        ThreadFragment threadFragment = new ThreadFragment();
        threadFragment.setArguments(args);
        return threadFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_thread,null);
        ButterKnife.bind(this,view);
        setWebView("http://blog.csdn.net/a992036795/article/details/51362487");
        return view;
    }

    private void setWebView(String s) {
        thread_webview.loadUrl(s);
        WebSettings settings = thread_webview.getSettings();
        settings.setJavaScriptEnabled(true);
        thread_webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                 /*重写此方法点击网页里面的链接还是当前的webvidw里面跳转*/
                view.loadUrl(url);
                return true;
            }
        });
    }
}
