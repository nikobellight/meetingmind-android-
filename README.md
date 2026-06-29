# MeetingMind Android

Android APK wrapper for [MeetingMind](https://meetingmind.nikobellight.workers.dev) built with Capacitor.

## Getting the APK

GitHub Actions builds the APK automatically on every push.

1. Go to **Actions** → latest **Build APK** workflow run
2. Download `meetingmind-debug-apk` from **Artifacts**
3. Transfer the `.apk` to your Android phone
4. Enable **Settings → Security → Install unknown apps** for your file manager
5. Tap the APK to install

## Background Recording

The app uses an Android Foreground Service to keep the microphone active when the screen is off or the app is in the background. A persistent notification ("Recording in progress...") appears while recording — this is required by Android to allow background microphone access.

### Web App Integration

The native Android bridge is injected automatically into every page load as `window.__MeetingMindNative`. To trigger the foreground service from your existing web app (`index.html`), call:

```js
// When the user taps Record — start the background service
if (window.__MeetingMindNative) {
  window.__MeetingMindNative.startRecording();
}

// When the user taps Stop
if (window.__MeetingMindNative) {
  window.__MeetingMindNative.stopRecording();
}
```

Add these calls alongside your existing `mediaRecorder.start()` / `mediaRecorder.stop()` code. The service is a no-op when called from a regular browser (the `if` guard protects it).

## Architecture

```
MainActivity.java              ← registers plugin, sets WebViewClient
BackgroundRecordingPlugin.java ← Capacitor plugin (JS ↔ native bridge)
AudioForegroundService.java    ← Android Foreground Service (keeps mic alive)
MeetingMindWebViewClient.java  ← injects window.__MeetingMindNative on page load
capacitor.config.json          ← points WebView at hosted URL
```

## Permissions declared

| Permission | Reason |
|---|---|
| `RECORD_AUDIO` | Microphone access |
| `FOREGROUND_SERVICE` | Run service in background |
| `FOREGROUND_SERVICE_MICROPHONE` | API 34+ microphone foreground service type |
| `WAKE_LOCK` | Prevent CPU sleep during recording |
| `INTERNET` | Load the hosted web app |

## Local Build (needs Android SDK)

```bash
npm install
npx cap sync android
cd android && ./gradlew assembleDebug
# APK: android/app/build/outputs/apk/debug/app-debug.apk
```
