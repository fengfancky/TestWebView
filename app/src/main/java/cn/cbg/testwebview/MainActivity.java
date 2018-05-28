package cn.cbg.testwebview;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private FrameLayout fullVideo;
    private View customView = null;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView=findViewById(R.id.webView);
        fullVideo=findViewById(R.id.full_video);
        progressBar=findViewById(R.id.progress);

        webView.loadUrl("https://www.iqiyi.com/");

        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new MyWebViewClient());
        setWebView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView!=null){
            webView.destroy();
        }
    }

    private void setWebView(){
       WebSettings webSettings = webView.getSettings();
       webSettings.setJavaScriptEnabled(true);
    }

    private class MyWebChromeClient extends WebChromeClient{

        @Override
        public void onHideCustomView() {

            if (customView == null){
                return;
            }
            fullVideo.removeView(customView);
            fullVideo.setVisibility(View.GONE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//清除全屏

        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            customView = view;
            fullVideo.setVisibility(View.VISIBLE);
            fullVideo.addView(customView);
            fullVideo.bringToFront();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        }

    }


    private class MyWebViewClient extends WebViewClient{

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

}
