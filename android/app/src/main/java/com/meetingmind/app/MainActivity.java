package com.meetingmind.app;

import android.os.Bundle;
import android.webkit.WebSettings;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        registerPlugin(BackgroundRecordingPlugin.class);
        super.onCreate(savedInstanceState);

        // Keep WebView JS running when app is backgrounded
        WebSettings settings = getBridge().getWebView().getSettings();
        settings.setMediaPlaybackRequiresUserGesture(false);

        // Inject native bridge on every page load
        getBridge().getWebView().setWebViewClient(
            new MeetingMindWebViewClient(getBridge())
        );
    }
}
