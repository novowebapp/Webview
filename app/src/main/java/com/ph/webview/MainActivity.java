package com.ph.webview;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MainActivity extends Activity {

    // Debugging
    private static final String TAG = "MainActivity";
    private static final boolean DEBUG = true;

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_CHOSE_BMP = 3;
    private static final int REQUEST_CAMER = 4;


    // Message types sent from the BluetoothService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final int MESSAGE_CONNECTION_LOST = 6;
    public static final int MESSAGE_UNABLE_CONNECT = 7;

    // Key names received from the BluetoothService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    public static String aparelho_conectado;


    // Name of the connected device
    private String mConnectedDeviceName;
    // Local Bluetooth adapter
    public BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the services
    private static final String CHINESE = "GBK";
    private static final String THAI = "CP874";
    private static final String KOREAN = "EUC-KR";
    private static final String BIG5 = "BIG5";

    public static Bitmap decodedByte = null;

    public int nPaperWidth = 384;



    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView= (WebView) findViewById(R.id.webview);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        settings.setAppCacheEnabled(false);
        webView.getSettings().setDomStorageEnabled(true);

        //webView.setWebChromeClient(new WebChromeClient());

        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webView.setScrollbarFadingEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);



        String htmlDocument = "<html><body><h1>Android Print Test</h1><p>" + "This is some sample content.</p></body></html>";

        webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);

        webView.loadUrl("http://jackdaves.com/deploy/interface/app/index.html");


        webView.setWebViewClient(new WebViewClient() {

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Ops!");
                builder.setMessage("Não foi possivel conectar à pagina.")
                        .setCancelable(false)
                        .setPositiveButton("Tente novamente", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                webView.loadUrl("http://jackdaves.com/deploy/interface/app/index.html");
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }


            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPageFinished(WebView view, String url)
            {
                //Toast.makeText(getApplicationContext(), "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                String verifica=method(url);
                String pagina_impressao="";
                //Toast.makeText(getApplicationContext(),verifica,Toast.LENGTH_SHORT).show();

                if (pagina_impressao.equals(verifica)) {
                    //createWebPrintJob(view);
                    //Toast.makeText(getApplicationContext(),verifica,Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           // Toast.makeText(getApplicationContext(),"teste",Toast.LENGTH_SHORT).show();

                        }
                    }, 2000);



                }
                //Toast.makeText(getApplicationContext(),url,Toast.LENGTH_SHORT).show();
                //createWebPrintJob(view);


            }
        });

    }


    public String method(String str) {
        String mainChapterNumber = str.split("\\?", 2)[0];
        return mainChapterNumber;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }


}

