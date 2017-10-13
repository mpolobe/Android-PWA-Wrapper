package at.xtools.pwawrapper.webview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import at.xtools.pwawrapper.Constants;
import at.xtools.pwawrapper.R;
import at.xtools.pwawrapper.ui.UIManager;

public class WebViewHelper {
    // Instance variables
    private Activity activity;
    private UIManager uiManager;
    private WebView webView;
    private WebSettings webSettings;

    public WebViewHelper(Activity activity, UIManager uiManager) {
        this.activity = activity;
        this.uiManager = uiManager;
        this.webView = (WebView) activity.findViewById(R.id.webView);
        this.webSettings = webView.getSettings();
    }

    /**
     * Simple helper method checking if connected to Network.
     * Doesn't check for actual Internet connection!
     * @return {boolean} True if connected to Network.
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Wifi or Mobile Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }

    // manipulate cache settings to make sure our PWA gets updated
    private void useCache(Boolean use) {
        if (use) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }
    }

    // public method changing cache settings according to network availability
    public void checkUseCache() {
        useCache(!isNetworkAvailable());
    }

    // handles initial setup of webview
    public void setupWebView() {
        // accept cookies
        CookieManager.getInstance().setAcceptCookie(true);
        // enable JS
        webSettings.setJavaScriptEnabled(true);
        // must be set for our js-popup-blocker:
        webSettings.setSupportMultipleWindows(true);

        // PWA settings
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            webSettings.setDatabasePath(activity.getApplicationContext().getFilesDir().getAbsolutePath());
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        }
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCachePath(activity.getApplicationContext().getCacheDir().getAbsolutePath());
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);

        // retrieve content from cache primarily if not connected
        checkUseCache();

        // set User Agent
        if (Constants.OVERRIDE_USER_AGENT || Constants.POSTFIX_USER_AGENT) {
            String userAgent = "";
            if (Constants.OVERRIDE_USER_AGENT) {
                userAgent = Constants.USER_AGENT;
            }
            if (Constants.POSTFIX_USER_AGENT) {
                userAgent = userAgent + " " + Constants.USER_AGENT_POSTFIX;
            }
            webSettings.setUserAgentString(userAgent);
        }

        // enable HTML5-support
        webView.setWebChromeClient(new WebChromeClient() {
            //simple yet effective redirect/popup blocker
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                Message href = view.getHandler().obtainMessage();
                view.requestFocusNodeHref(href);
                final String popupUrl = href.getData().getString("url");
                if (popupUrl != null) {
                    //it's null for most rouge browser hijack ads
                    webView.loadUrl(popupUrl);
                    return true;
                }
                return false;
            }

            // update ProgressBar
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                uiManager.setLoadingProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });

        // Set up Webview client
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // prevent loading content that isn't ours
                if (!url.startsWith(Constants.WEBAPP_URL)) {
                    // stop loading
                    view.stopLoading();

                    // open external URL in Browser/3rd party apps instead
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    activity.startActivity(intent);
                }
                // activate loading animation screen
                uiManager.setLoading(true);
                super.onPageStarted(view, url, favicon);
            }

            // handle loading error by showing the offline screen
            @Deprecated
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    uiManager.setOffline(true);
                }
            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // new API method calls this on every error for each resource.
                    // we only want to interfere if the page itself got problems.
                    if (view.getUrl().equals(request.getUrl().toString())) {
                        uiManager.setOffline(true);
                    }
                }
            }
        });
    }

    // Lifecycle callbacks
    public void onPause() {
        webView.onPause();
    }

    public void onResume() {
        webView.onResume();
    }

    // handle back button press
    public boolean goBack() {
        if (webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }

    // load app startpage
    public void loadHome() {
        webView.loadUrl(Constants.WEBAPP_URL);
    }
}
