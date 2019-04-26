# Android-CalgaryTrafficCamera
- Java
- Android Studio
- RESTful API
  - Endpoints
  - Query Parameters
  - Query Functions
- Json
- Android AsyncTask

## Overview
Displays real-time traffic camera feed using the Open Calgary Traffic Camera API

## Problems Encountered
- API Query Parameter cannot contain `&`
- Enable internet permission in the android manifest in order to access internet
  - `<uses-permission android:name="android.permission.INTERNET"/>`
- Cleartext HTTP traffic not permitted
  - Add `android:usesCleartextTraffic="true"` in android manifest under `<application>`
- Cannot run network on mainthread
  - Use `AsyncTask` to run URL requests in the background
