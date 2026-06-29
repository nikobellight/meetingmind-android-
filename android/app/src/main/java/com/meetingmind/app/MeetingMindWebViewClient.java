package com.meetingmind.app;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import com.getcapacitor.BridgeWebViewClient;
import com.getcapacitor.Bridge;

/**
 * Custom WebViewClient that injects the background-recording JS bridge
 * into every page so the hosted web app can call native APIs.
 */
public class MeetingMindWebViewClient extends BridgeWebViewClient {

    private static final String BRIDGE_JS =
        "window.__MeetingMindNative = {" +
        "  startRecording: function() {" +
        "    if (window.Capacitor && window.Capacitor.Plugins && window.Capacitor.Plugins.BackgroundRecording) {" +
        "      window.Capacitor.Plugins.BackgroundRecording.startService();" +
        "    }" +
        "  }," +
        "  stopRecording: function() {" +
        "    if (window.Capacitor && window.Capacitor.Plugins && window.Capacitor.Plugins.BackgroundRecording) {" +
        "      window.Capacitor.Plugins.BackgroundRecording.stopService();" +
        "    }" +
        "  }" +
        "};";

    public MeetingMindWebViewClient(Bridge bridge) {
        super(bridge);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        view.evaluateJavascript(BRIDGE_JS, null);
    }
}
