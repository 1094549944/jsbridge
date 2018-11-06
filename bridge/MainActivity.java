package com.example.zhangyuke.mywebview;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private WebView wb;
    private String URL_HOST_STARTWITH_FUNCATIONAL = "test://functional";
    private String URL_HOST_STARTWITH_BUSINSS = "test://business";
    private String URL_QUERY_KEY_HANDLER = "handler";
    private String URL_SHOW_LOADING = "showLoading";
    private String URL_CLOSE_LOADING = "closeLoading";
    private String URL_SHOW_ALERT="showAlert";
    private String URL_TOAST="toast";
    private String URL_PENDING_PAYMENT="pending_payment";
    private String URL_QUERY_KEY_CALLBACK="callback";

    private String URL_QUERY_PARAMS="params";
    private String URL_DATA="data";
    private String ORDER_ID="orderId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wb = findViewById(R.id.wb);
        //支持js语言
        wb.getSettings().setJavaScriptEnabled(true);
        //缩放至屏幕的大小
        wb.getSettings().setLoadWithOverviewMode(true);
        //支持缩放
        wb.getSettings().setSupportZoom(true);
        //webview去加载网页
        wb.loadUrl("http://www.jiaxinying.cn./bridge/");
        //设置用自己的浏览器打开
        wb.setWebViewClient(new MyWebViewClient());


    }

    private boolean baseParseShouldOverrideUrlLoading(WebView view, String url, Uri uri) {
        if (url.startsWith(URL_HOST_STARTWITH_FUNCATIONAL)) {
            JSONObject queryObject = JSonUtils.parseObjectWithoutException(uri.getQuery());
            String jsCallback = queryObject.getString(URL_QUERY_KEY_CALLBACK);
            String action = queryObject.getString(URL_QUERY_KEY_HANDLER);
            Log.d(TAG, "taogu://functional: handler: " + action);
            if (URL_SHOW_LOADING.equals(action)) {
                Toast.makeText(this, "加载进度条", Toast.LENGTH_SHORT).show();
                return true;
            } else if (URL_CLOSE_LOADING.equals(action)) { // 设置标题
                Toast.makeText(this, "关闭进度条", Toast.LENGTH_SHORT).show();
                return true;
            }else if (URL_SHOW_ALERT.equals(action)){
                String params = queryObject.getString(URL_QUERY_PARAMS);
                if (!TextUtils.isEmpty(params)) {
                    Toast.makeText(this, params, Toast.LENGTH_SHORT).show();
                }
            }else if (URL_TOAST.equals(action)){
                String params = queryObject.getString(URL_QUERY_PARAMS);
                if (!TextUtils.isEmpty(params)){
                    JSONObject paramsObject=JSonUtils.parseObjectWithoutException(params);
                    if (paramsObject!=null){
                        String data = paramsObject.getString(URL_DATA);
                        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
                    }
                }
            }

        } else if (url.startsWith(URL_HOST_STARTWITH_BUSINSS)){
            JSONObject queryObject = JSonUtils.parseObjectWithoutException(uri.getQuery());
            String jsCallback = queryObject.getString(URL_QUERY_KEY_CALLBACK);
            String action = queryObject.getString(URL_QUERY_KEY_HANDLER);
            if (URL_PENDING_PAYMENT.equals(action)){
                String params = queryObject.getString(URL_QUERY_PARAMS);
                if (!TextUtils.isEmpty(params)){
                    JSONObject paramsObject=JSonUtils.parseObjectWithoutException(params);
                    if (paramsObject!=null){
                        String order = paramsObject.getString(ORDER_ID);
//                        Toast.makeText(this, order, Toast.LENGTH_SHORT).show();
                    }
                }
                callBackToJs(jsCallback,"");
            }
        }
        return false;
    }

    //js回调
    private void callBackToJs(String callbackFunc, String callbackParam) {
        if (TextUtils.isEmpty(callbackFunc)) {
            Log.e(TAG,"callback Func is Null");
        } else {
            wb.loadUrl("javascript:" + callbackFunc + "(" + callbackParam + ")");
        }
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i(TAG,"webView shouldOverrideUrl: " + url);
            Uri uri = Uri.parse(url);
            return baseParseShouldOverrideUrlLoading(view, url, uri);

        }
    }


}
